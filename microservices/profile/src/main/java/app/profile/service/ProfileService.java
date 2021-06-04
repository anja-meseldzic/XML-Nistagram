package app.profile.service;

import app.profile.model.Profile;

public interface ProfileService {
	void followProfile(long id, long loggedId);
	void unfollowProfile(long id, long loggedId);
}
