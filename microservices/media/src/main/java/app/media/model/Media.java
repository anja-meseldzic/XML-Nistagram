package app.media.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Media {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "username", nullable = false)
	private String username;
	
	@ElementCollection(targetClass=String.class, fetch = FetchType.LAZY)
	private Set<String> path = new HashSet<String>();

	public Media() {
		super();
	}
	
	public Media(long id, String username, Set<String> path) {
		super();
		this.id = id;
		this.username = username;
		this.path = path;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<String> getPath() {
		return path;
	}

	public void setPath(Set<String> path) {
		this.path = path;
	}


}
