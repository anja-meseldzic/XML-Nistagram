package app.media.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Media {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "profileId", nullable = false)
	private long profileId;
	
	@ElementCollection(targetClass=String.class, fetch = FetchType.LAZY)
	private Set<String> path = new HashSet<String>();

	public Media() {
		super();
	}
	
	public Media(long id, long profileId, Set<String> path) {
		super();
		this.id = id;
		this.profileId = profileId;
		this.path = path;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getProfileId() {
		return profileId;
	}

	public void setProfileId(long profileId) {
		this.profileId = profileId;
	}

	public Set<String> getPath() {
		return path;
	}

	public void setPath(Set<String> path) {
		this.path = path;
	}


}
