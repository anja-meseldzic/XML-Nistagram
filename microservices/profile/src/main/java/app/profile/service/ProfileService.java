package app.profile.service;

import java.util.List;
import java.util.Set;

import app.profile.model.dto.FollowRequestDto;
import app.profile.model.dto.FollowerDto;

public interface ProfileService {
	int followProfile(String username, String loggedInUsername);
	int unfollowProfile(String username, String loggedInUsername);

	Set<FollowRequestDto> getFollowRequests(String username);
	Set<FollowRequestDto> acceptRequest(String username,String loggedInUsername);
	Set<FollowRequestDto> deleteRequest(String username,String loggedInUsername);
	List<FollowerDto> getFollowers(String username);
	List<FollowerDto> getFollowing(String username);

	void createFromUser(String username);
	
	void addCloseFriend(String myUsername, String usernameOfFriend);

}
