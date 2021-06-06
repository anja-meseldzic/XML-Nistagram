package app.profile.service;

import java.util.List;
import java.util.Set;

import app.profile.dtos.ProfileInfoDTO;
import app.profile.exception.ProfileNotFoundException;
import app.profile.model.dto.FollowRequestDto;
import app.profile.model.dto.FollowerDto;


public interface ProfileService {
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

	void addCloseFriend(String myUsername, String usernameOfFriend);
}
