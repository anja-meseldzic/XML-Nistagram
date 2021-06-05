package app.profile.service;


import app.profile.dtos.ProfileInfoDTO;
import app.profile.exception.ProfileNotFoundException;

import java.util.List;

public interface ProfileService {
	int followProfile(String username, String loggedInUsername);

	int unfollowProfile(String username, String loggedInUsername);

	void createFromUser(String username);

	ProfileInfoDTO getProfile(String requestedBy, String profile) throws ProfileNotFoundException;

	List<String> getAll();

	List<String> getFollowers(String profile);

	List<String> getFollowing(String profile);

	List<String> getBlocked(String profile);

	List<String> getMuted(String profile);

	List<String> getCloseFriends(String profile);

	boolean isPublic(String profile) throws ProfileNotFoundException;
}
