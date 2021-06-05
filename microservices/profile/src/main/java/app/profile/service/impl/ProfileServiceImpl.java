package app.profile.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import java.util.Set;

import app.profile.model.Profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.profile.model.Follow;
import app.profile.model.FollowRequest;
import app.profile.model.Profile;
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

	@Autowired
	public ProfileServiceImpl(ProfileRepository profileRepository, FollowRepository followRepository,
			FollowRequestRepository followRequestRepo) {
		this.profileRepository = profileRepository;
		this.followRepository = followRepository;
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
}
