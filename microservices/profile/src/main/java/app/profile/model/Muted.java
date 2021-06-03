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
public class Muted {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Profile profile;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Profile> mutedByProfile = new HashSet<Profile>();

	public Muted() {
		super();
	}

	public Muted(long id, Profile profile, Set<Profile> mutedByProfile) {
		super();
		this.id = id;
		this.profile = profile;
		this.mutedByProfile = mutedByProfile;
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

	public Set<Profile> getMutedByProfile() {
		return mutedByProfile;
	}

	public void setMutedByProfile(Set<Profile> mutedByProfile) {
		this.mutedByProfile = mutedByProfile;
	}
	
	

}
