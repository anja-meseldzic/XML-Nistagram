package app.profile.model;

import java.util.HashSet;
import java.util.Set;

public class FollowRequest {

	private long id;
	private Profile profile;
	private Set<Profile> followRequests = new HashSet<Profile>();
}
