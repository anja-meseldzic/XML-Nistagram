package app.profile.service;

import app.profile.model.Profile;

public interface ProfileService {
	int followProfile(long id, long loggedId);
	int unfollowProfile(long id, long loggedId);
}
