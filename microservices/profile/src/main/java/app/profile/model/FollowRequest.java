package app.profile.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class FollowRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Profile profile;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Profile followedBy;

	public FollowRequest() {
		super();
	}

	public FollowRequest(long id, Profile profile, Profile followedBy) {
		super();
		this.id = id;
		this.profile = profile;
		this.followedBy = followedBy;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Profile getFollowedBy() {
		return followedBy;
	}

	public void setFollowedBy(Profile followedBy) {
		this.followedBy = followedBy;
	}
	
}
