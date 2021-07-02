package app.profile.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;

import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import app.profile.dtos.ProfileInfoDTO;
import app.profile.exception.ProfileNotFoundException;
import app.profile.model.Profile;
import app.profile.model.dto.FollowRequestDto;
import app.profile.model.dto.FollowerDto;
import app.profile.model.dto.ProfileVerificationRequestDTO;


public interface ProfileService {
	void update(Profile profile);
	Profile get(String username);

	int followProfile(String username, String loggedInUsername);

	int unfollowProfile(String username, String loggedInUsername);

	ProfileInfoDTO getProfile(String requestedBy, String profile) throws ProfileNotFoundException;

	List<String> getAll();

	List<String> getFollowerss(String profile);

	List<String> getFollowingg(String profile);

	List<String> getBlocked(String profile);

	List<String> getMuted(String profile);

	List<String> getCloseFriends(String profile);

	boolean isPublic(String profile) throws ProfileNotFoundException;

	Set<FollowRequestDto> getFollowRequests(String username);

	Set<FollowRequestDto> acceptRequest(String username,String loggedInUsername);

	Set<FollowRequestDto> deleteRequest(String username,String loggedInUsername);

	List<FollowerDto> getFollowers(String username);

	void createFromUser(String username);

	List<FollowerDto> getFollowing(String username);

	String addCloseFriend(String myUsername, String usernameOfFriend);

	String removeCloseFriend(String myUsername, String usernameOfFriend);

	String saveRequest(MultipartFile data, ProfileVerificationRequestDTO requestDTO, String username) throws IOException;

	List<ProfileVerificationRequestDTO> getVerificationRequests();

	List<ProfileVerificationRequestDTO> verify(long id);

	List<ProfileVerificationRequestDTO> deleteVerification(long id);
	
	UrlResource getContent(String fileName) throws MalformedURLException;
	
	boolean isProfileActive(String username);
	
	void deactivateProfile(String username);
	
	List<String> getAllInactiveProfiles();
}
