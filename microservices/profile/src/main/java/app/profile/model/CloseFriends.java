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
public class CloseFriends {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Profile profile;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Profile> closeFriendsOfProfile = new HashSet<Profile>();

	public CloseFriends() {
		super();
	}

	public CloseFriends(long id, Profile profile, Set<Profile> closeFriendsOfProfile) {
		super();
		this.id = id;
		this.profile = profile;
		this.closeFriendsOfProfile = closeFriendsOfProfile;
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

	public Set<Profile> getCloseFriendsOfProfile() {
		return closeFriendsOfProfile;
	}

	public void setCloseFriendsOfProfile(Set<Profile> closeFriendsOfProfile) {
		this.closeFriendsOfProfile = closeFriendsOfProfile;
	}
	
	

}
