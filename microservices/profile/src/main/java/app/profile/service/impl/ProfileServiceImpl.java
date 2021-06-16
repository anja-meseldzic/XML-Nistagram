package app.profile.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import app.profile.dtos.ProfileInfoDTO;
import app.profile.dtos.UserInfoDTO;
import app.profile.exception.ProfileNotFoundException;
import app.profile.model.Profile;
import app.profile.model.VerificationCategory;
import app.profile.model.VerificationRequest;
import app.profile.service.AuthService;
import java.util.Set;
import java.util.UUID;

import app.profile.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import app.profile.model.Follow;
import app.profile.model.FollowRequest;
import app.profile.model.dto.FollowRequestDto;
import app.profile.model.dto.FollowerDto;
import app.profile.model.dto.ProfileVerificationRequestDTO;
import app.profile.repository.FollowRepository;
import app.profile.repository.FollowRequestRepository;
import app.profile.repository.ProfileRepository;
import app.profile.repository.VerificationRequestRepository;
import app.profile.service.ProfileService;

@Service
public class ProfileServiceImpl implements ProfileService {

	private ProfileRepository profileRepository;
	private FollowRepository followRepository;
	private FollowRequestRepository followRequestRepo;
	private AuthService authService;
	private VerificationRequestRepository verificationRequestRepo;
	private NotificationService notificationService;
	
	@Value("${profile.storage}")
	private String storageDirectoryPath;

	@Autowired
	public ProfileServiceImpl(ProfileRepository profileRepository, FollowRepository followRepository,
			AuthService authService, FollowRequestRepository followRequestRepo,
			VerificationRequestRepository verificationRequestRepo, NotificationService notificationService) {
		this.profileRepository = profileRepository;
		this.followRepository = followRepository;
		this.authService = authService;
		this.followRequestRepo = followRequestRepo;
		this.verificationRequestRepo = verificationRequestRepo;
		this.notificationService = notificationService;
	}

	@Override
	public void update(Profile profile) {
		profileRepository.save(profile);
	}

	@Override
	public Profile get(String username) {
		Profile p = profileRepository.findByRegularUserUsername(username);
		if (p == null)
			throw new IllegalArgumentException("Profile with username " + username + " not found");
		return p;
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

			follow = followRepository.save(follow);
			notificationService.createSettings(follow.getId());
		} else {
			FollowRequest request = new FollowRequest();
			request.setProfile(profile);
			request.setFollowedBy(followedBy);
			boolean exists = false;

			for (FollowRequest f : followRequestRepo.findAll()) {
				if (f.getFollowedBy().equals(followedBy) && f.getProfile().equals(profile)) {
					exists = true;
				}
			}
			if (!exists) {
				followRequestRepo.save(request);
			}
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
		notificationService.deleteSettings(delete.getId());

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
		Profile profile = profileRepository.findByRegularUserUsername(loggedInUsername);
		Profile followedBy = profileRepository.findByRegularUserUsername(username);

		Follow follow = new Follow();
		follow.setFollowedBy(followedBy);
		follow.setProfile(profile);
		follow.setBlocked(false);
		follow.setCloseFriend(false);
		follow.setMuted(false);

		follow = followRepository.save(follow);
		notificationService.createSettings(follow.getId());

		return deleteRequest(username, loggedInUsername);
	}

	@Override
	public Set<FollowRequestDto> deleteRequest(String username, String loggedInUsername) {
		Set<FollowRequest> requests = followRequestRepo
				.findByProfile(profileRepository.findByRegularUserUsername(loggedInUsername));

		Set<FollowRequestDto> followRequests = new HashSet<FollowRequestDto>();

		FollowRequest delete = new FollowRequest();
		for (FollowRequest f : requests) {
			if (f.getFollowedBy().getRegularUserUsername().equals(username)) {
				delete = f;
				break;
			}
		}
		if (delete != null) {
			followRequestRepo.delete(delete);
		}
		Set<FollowRequest> newRequests = followRequestRepo
				.findByProfile(profileRepository.findByRegularUserUsername(loggedInUsername));

		for (FollowRequest f : newRequests) {
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
		profile = profileRepository.save(profile);
		notificationService.createSettings(profile.getRegularUserUsername());
	}

	@Override
	public ProfileInfoDTO getProfile(String requestedBy, String profile) throws ProfileNotFoundException {
		Profile p = profileRepository.findByRegularUserUsername(profile);
		if (p == null)
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
		result.setVerified(p.isVerified());

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
				.filter(f -> f.getFollowedBy().getRegularUserUsername().equals(profile) && f.isBlocked())
				.map(f -> f.getProfile().getRegularUserUsername()).collect(Collectors.toList());
	}

	@Override
	public List<String> getMuted(String profile) {
		return followRepository.findAll().stream()
				.filter(f -> f.getFollowedBy().getRegularUserUsername().equals(profile) && f.isMuted())
				.map(f -> f.getProfile().getRegularUserUsername()).collect(Collectors.toList());
	}

	@Override
	public List<String> getCloseFriends(String profile) {
		return followRepository.findAll().stream()
				.filter(f -> f.getProfile().getRegularUserUsername().equals(profile) && f.isCloseFriend())
				.map(f -> f.getFollowedBy().getRegularUserUsername()).collect(Collectors.toList());
	}

	@Override
	public boolean isPublic(String profile) throws ProfileNotFoundException {
		Optional<Profile> p = profileRepository.findAll().stream()
				.filter(prof -> prof.getRegularUserUsername().equals(profile)).findFirst();
		if (!p.isPresent())
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
		return followRepository.findAll().stream()
				.filter(f -> f.getProfile().getRegularUserUsername().equals(profile.getRegularUserUsername())
						&& f.getFollowedBy().getRegularUserUsername().equals(follower))
				.count() > 0;
	}

	@Override
	public String addCloseFriend(String myUsername, String usernameOfFriend) {
		for (Follow f : followRepository.findAll()) {
			if (f.getProfile().getRegularUserUsername().equals(myUsername)
					&& f.getFollowedBy().getRegularUserUsername().equals(usernameOfFriend)
					&& f.isCloseFriend() == true) {
				return "You have already choose this profile as your close friend.";
			} else if (f.getProfile().getRegularUserUsername().equals(myUsername)
					&& f.getFollowedBy().getRegularUserUsername().equals(usernameOfFriend)
					&& f.isCloseFriend() == false) {
				f.setCloseFriend(true);
				followRepository.save(f);
				return "You have successfully added your close friend";
			}
		}
		return "This profile can't be set as your close friend";

	}

	@Override
	public String removeCloseFriend(String myUsername, String usernameOfFriend) {
		for (Follow f : followRepository.findAll()) {
			if (f.getProfile().getRegularUserUsername().equals(myUsername)
					&& f.getFollowedBy().getRegularUserUsername().equals(usernameOfFriend)
					&& f.isCloseFriend() == false) {
				return "You have already removed this profile from your close friends.";
			} else if (f.getProfile().getRegularUserUsername().equals(myUsername)
					&& f.getFollowedBy().getRegularUserUsername().equals(usernameOfFriend)
					&& f.isCloseFriend() == true) {
				f.setCloseFriend(false);
				followRepository.save(f);
				return "You have successfully removed your close friend";
			}
		}
		return "This profile can't be removed from your close friends list";
	}

	@Override
	public String saveRequest(MultipartFile data, ProfileVerificationRequestDTO requestDTO, String username)
			throws IOException {

		String fileName = saveFile(data, storageDirectoryPath);
		String fileDownloadUri = "media/content/" + fileName;
		System.out.println(fileDownloadUri);

		Profile profile = profileRepository.findByRegularUserUsername(username);
		if(!profile.isVerified()) {
		VerificationRequest request = new VerificationRequest();

		request.setApproved(false);
		request.setFilePath(fileDownloadUri);
		request.setProfile(profile);
		request.setName(requestDTO.getName());
		request.setSurname(requestDTO.getSurname());

		String category = requestDTO.getCategory();
		if (category.equalsIgnoreCase("Influencer")) {
			request.setCategory(VerificationCategory.INFLUENCECR);
		} else if (category.equalsIgnoreCase("Sports")) {
			request.setCategory(VerificationCategory.SPORTS);
		} else if (category.equalsIgnoreCase("Business")) {
			request.setCategory(VerificationCategory.BUSINESS);
		} else if (category.equalsIgnoreCase("News/Media")) {
			request.setCategory(VerificationCategory.NEWS_MEDIA);
		} else if (category.equalsIgnoreCase("Brand")) {
			request.setCategory(VerificationCategory.BRAND);
		} else if (category.equalsIgnoreCase("Organisation")) {
			request.setCategory(VerificationCategory.ORGANISATION);
		}

		verificationRequestRepo.save(request);
		return "Request have been sent";
		}
		else {
			return "You are already verified.";
		}
	}

	private String saveFile(MultipartFile file, String storageDirectoryPath) throws IOException {
		String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
		String extension = getFileExtension(originalFileName);
		String fileName = UUID.randomUUID().toString() + "." + extension;

		System.out.println(fileName);

		Path storageDirectory = Paths.get(storageDirectoryPath);
		if (!Files.exists(storageDirectory)) {
			Files.createDirectories(storageDirectory);
		}
		Path destination = Paths.get(storageDirectory.toString() + File.separator + fileName);
		Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
		return fileName;
	}

	private String getFileExtension(String fileName) throws IOException {
		String[] parts = fileName.split("\\.");
		if (parts.length > 0)
			return parts[parts.length - 1];
		else
			throw new IOException();
	}

	@Override
	public List<ProfileVerificationRequestDTO> getVerificationRequests() {
		List<VerificationRequest> allRequests = verificationRequestRepo.findAll().stream().filter(r -> !r.isApproved())
				.collect(Collectors.toList());
		List<ProfileVerificationRequestDTO> result = new ArrayList<ProfileVerificationRequestDTO>();
		for (VerificationRequest vr : allRequests) {
			
			ProfileVerificationRequestDTO pvr = new ProfileVerificationRequestDTO();
			pvr.setId(vr.getId());
			pvr.setCategory(vr.getCategory().toString());
			pvr.setName(vr.getName());
			pvr.setSurname(vr.getSurname());
			pvr.setUrl(vr.getFilePath());
			
			result.add(pvr);
		}
		return result;
	}

	@Override
	public List<ProfileVerificationRequestDTO> verify(long id) {
		VerificationRequest request = verificationRequestRepo.findById(id).get();
		request.setApproved(true);
		request.getProfile().setVerified(true);
		verificationRequestRepo.save(request);
		
		return getVerificationRequests();
	}
	
	@Override
	public List<ProfileVerificationRequestDTO> deleteVerification(long id) {
		VerificationRequest request = verificationRequestRepo.findById(id).get();
		verificationRequestRepo.delete(request);
		
		return getVerificationRequests();
	}
}
