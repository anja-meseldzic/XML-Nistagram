package app.profile.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import app.profile.dtos.ProfileInfoDTO;
import app.profile.dtos.UserInfoDTO;
import app.profile.exception.ProfileNotFoundException;
import app.profile.model.Profile;
import app.profile.service.AuthService;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.profile.model.Follow;
import app.profile.model.FollowRequest;
import app.profile.model.dto.FollowRequestDto;
import app.profile.model.dto.FollowerDto;
import app.profile.repository.FollowRepository;
import app.profile.repository.FollowRequestRepository;
import app.profile.repository.ProfileRepository;
import app.profile.service.ProfileService;

@Service
public class ProfileServiceImpl implements ProfileService {

	private ProfileRepository profileRepository;
	private FollowRepository followRepository;
	private FollowRequestRepository followRequestRepo;
	private AuthService authService;

	@Autowired
	public ProfileServiceImpl(ProfileRepository profileRepository, FollowRepository followRepository, AuthService authService, FollowRequestRepository followRequestRepo) {
		this.profileRepository = profileRepository;
		this.followRepository = followRepository;
		this.authService = authService;
		this.followRequestRepo = followRequestRepo;
	}

	@Override
	public int followProfile(String username, String loggedInUsername) {
		Profile profile = profileRepository.findByRegularUserUsername(username);
		Profile followedBy = profileRepository.findByRegularUserUsername(loggedInUsername);

		if (!profile.isPrivateProfile()) {

			Follow follow = new Follow();
			follow.setFollowedBy(followedBy);
			follow.setProfile(profile);
			follow.setBlocked(false);
			follow.setCloseFriend(false);
			follow.setMuted(false);

			followRepository.save(follow);
		} else {
			FollowRequest request = new FollowRequest();
			request.setProfile(profile);
			request.setFollowedBy(followedBy);

			followRequestRepo.save(request);
		}
		return getFollowers(username).size();
	}

	@Override
	public int unfollowProfile(String username, String loggedInUsername) {
		Follow delete = new Follow();
		for (Follow f : followRepository.findAll()) {
			if (f.getProfile().getRegularUserUsername().equals(username)
					&& f.getFollowedBy().getRegularUserUsername().equals(loggedInUsername)) {
				delete = f;
				break;
			}
		}
		followRepository.delete(delete);

		return getFollowers(username).size();
	}

	@Override
	public List<FollowerDto> getFollowers(String username) {
		List<FollowerDto> followers = new ArrayList<FollowerDto>();
		for (Follow f : followRepository.findAll()) {
			if (f.getProfile().getRegularUserUsername().equals(username)) {
				followers.add(new FollowerDto(f.getFollowedBy().getRegularUserUsername()));
			}
		}
		return followers;
	}

	@Override
	public List<FollowerDto> getFollowing(String username) {
		List<FollowerDto> following = new ArrayList<FollowerDto>();
		for (Follow f : followRepository.findAll()) {
			if (f.getFollowedBy().getRegularUserUsername().equals(username)) {
				following.add(new FollowerDto(f.getProfile().getRegularUserUsername()));
			}
		}
		return following;
	}

	@Override
	public Set<FollowRequestDto> getFollowRequests(String username) {
		Set<FollowRequest> requests = followRequestRepo
				.findByProfile(profileRepository.findByRegularUserUsername(username));
		Set<FollowRequestDto> followRequests = new HashSet<FollowRequestDto>();

		for (FollowRequest f : requests) {
			FollowRequestDto dto = new FollowRequestDto();
			dto.setUsername1(f.getProfile().getRegularUserUsername());
			dto.setUsername2(f.getFollowedBy().getRegularUserUsername());
			dto.setId(f.getId());
			followRequests.add(dto);
		}
		return followRequests;
	}

	@Override
	public Set<FollowRequestDto> acceptRequest(String username, String loggedInUsername) {
		Profile profile = profileRepository.findByRegularUserUsername(username);
		Profile followedBy = profileRepository.findByRegularUserUsername(loggedInUsername);

		Follow follow = new Follow();
		follow.setFollowedBy(followedBy);
		follow.setProfile(profile);
		follow.setBlocked(false);
		follow.setCloseFriend(false);
		follow.setMuted(false);

		followRepository.save(follow);
		
		return deleteRequest(username, loggedInUsername);
	}

	@Override
	public Set<FollowRequestDto> deleteRequest(String username, String loggedInUsername){
		Set<FollowRequest> requests = followRequestRepo
				.findByProfile(profileRepository.findByRegularUserUsername(username));
		
		Set<FollowRequestDto> followRequests = new HashSet<FollowRequestDto>();
		
		FollowRequest delete = new FollowRequest();
		for (FollowRequest f : requests) {
			if(f.getFollowedBy().getRegularUserUsername().equals(loggedInUsername)){
				delete = f;
				break;
			}
		}
		
		followRequestRepo.delete(delete);
		
		for (FollowRequest f : requests) {
			FollowRequestDto dto = new FollowRequestDto();
			dto.setUsername1(f.getProfile().getRegularUserUsername());
			dto.setUsername2(f.getFollowedBy().getRegularUserUsername());
			dto.setId(f.getId());
			followRequests.add(dto);
		}
		
		return followRequests;
	}
	
	@Override
	public void createFromUser(String username) {
		Profile profile = new Profile(username);
		profileRepository.save(profile);
	}

	@Override
	public ProfileInfoDTO getProfile(String requestedBy, String profile) throws ProfileNotFoundException {
		Profile p = profileRepository.findByRegularUserUsername(profile);
		if(p == null)
			throw new ProfileNotFoundException();

		ProfileInfoDTO result = new ProfileInfoDTO();
		UserInfoDTO userInfo = authService.get(profile);

		result.setId(p.getId());
		result.setUsername(p.getRegularUserUsername());
		result.setBio(userInfo.getBio());
		result.setBirthDate(userInfo.getBirthDate());
		result.setEmail(userInfo.getEmail());
		result.setFullName(userInfo.getFullName());
		result.setGender(userInfo.getGender());
		result.setWebsite(userInfo.getWebsite());
		result.setFollowerCount(getFollowerCount(p));
		result.setFollowingCount(getFollowingCount(p));
		result.setOwned(p.getRegularUserUsername().equals(requestedBy) ? true : false);
		result.setPrivateProfile(p.isPrivateProfile());
		result.setFollowing(isFollowing(p, requestedBy));

		return result;
	}

	@Override
	public List<String> getAll() {
		return profileRepository.findAll().stream().map(p -> p.getRegularUserUsername()).collect(Collectors.toList());
	}

	@Override
	public List<String> getFollowerss(String profile) {
		List<String> following = new ArrayList<String>();
		for (Follow f : followRepository.findAll()) {
			if (f.getProfile().getRegularUserUsername().equals(profile)) {
				following.add(f.getFollowedBy().getRegularUserUsername());
			}
		}
		return following;
	}

	@Override
	public List<String> getFollowingg(String username) {
		List<String> following = new ArrayList<String>();
		for (Follow f : followRepository.findAll()) {
			if (f.getFollowedBy().getRegularUserUsername().equals(username)) {
				following.add(f.getProfile().getRegularUserUsername());
			}
		}
		return following;
	}

	@Override
	public List<String> getBlocked(String profile) {
		return followRepository.findAll().stream()
				.filter(f -> f.getFollowedBy().getRegularUserUsername().equals(profile)
						&& f.isBlocked())
				.map(f -> f.getProfile().getRegularUserUsername())
				.collect(Collectors.toList());
	}

	@Override
	public List<String> getMuted(String profile) {
		return followRepository.findAll().stream()
				.filter(f -> f.getFollowedBy().getRegularUserUsername().equals(profile)
						&& f.isMuted())
				.map(f -> f.getProfile().getRegularUserUsername())
				.collect(Collectors.toList());
	}

	@Override
	public List<String> getCloseFriends(String profile) {
		return followRepository.findAll().stream()
				.filter(f -> f.getProfile().getRegularUserUsername().equals(profile)
						&& f.isCloseFriend())
				.map(f -> f.getFollowedBy().getRegularUserUsername())
				.collect(Collectors.toList());
	}

	@Override
	public boolean isPublic(String profile) throws ProfileNotFoundException {
		Optional<Profile> p = profileRepository.findAll().stream().
				filter(prof -> prof.getRegularUserUsername().equals(profile)).findFirst();
		if(!p.isPresent())
			throw new ProfileNotFoundException();
		return !p.get().isPrivateProfile();
	}

	private int getFollowerCount(Profile profile) {
		return followRepository.findByProfile(profile).size();
	}

	private int getFollowingCount(Profile profile) {
		return followRepository.findByFollowedBy(profile).size();
	}

	private boolean isFollowing(Profile profile, String follower) {
		return followRepository.findAll().stream().
				filter(f -> f.getProfile().getRegularUserUsername().equals(profile.getRegularUserUsername())
						&& f.getFollowedBy().getRegularUserUsername().equals(follower)).count() > 0;
	}

	@Override
	public void addCloseFriend(String myUsername, String usernameOfFriend) {
		for (Follow f : followRepository.findAll()) {
			if (f.getProfile().getRegularUserUsername().equals(myUsername) && f.getFollowedBy().getRegularUserUsername().equals(usernameOfFriend)){
				f.setCloseFriend(true);
				followRepository.save(f);
				return;
			}
		}
		
	}
}
