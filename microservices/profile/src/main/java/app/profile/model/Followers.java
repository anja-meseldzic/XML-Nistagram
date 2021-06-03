package app.profile.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class Followers {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Profile profile;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Profile> followersOfProfile = new HashSet<Profile>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Profile> following = new HashSet<Profile>();
	
	public Followers() {
		super();
	}

	public Followers(long id, Profile profile, Set<Profile> followersOfProfile, Set<Profile> following) {
		super();
		this.id = id;
		this.profile = profile;
		this.followersOfProfile = followersOfProfile;
		this.following = following;
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

	public Set<Profile> getFollowersOfProfile() {
		return followersOfProfile;
	}

	public void setFollowersOfProfile(Set<Profile> followersOfProfile) {
		this.followersOfProfile = followersOfProfile;
	}

	public Set<Profile> getFollowing() {
		return following;
	}

	public void setFollowing(Set<Profile> following) {
		this.following = following;
	}
	
	
	

}
