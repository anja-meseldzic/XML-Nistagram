package app.profile.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class AgentRegistrationRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Profile profile;
	
	@Column(name = "website", nullable = false)
	private String website;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "reviewed", nullable = false)
	private boolean reviewed;

	public AgentRegistrationRequest() {
		super();
	}

	public AgentRegistrationRequest(long id, Profile profile, String website, String email, boolean reviewed) {
		super();
		this.id = id;
		this.profile = profile;
		this.website = website;
		this.email = email;
		this.reviewed = reviewed;
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

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isReviewed() {
		return reviewed;
	}

	public void setReviewed(boolean reviewed) {
		this.reviewed = reviewed;
	}
	
	
}
