package app.profile.service;



public interface ProfileService {
	int followProfile(String username, String loggedInUsername);
	int unfollowProfile(String username, String loggedInUsername);
}
