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
public class VerificationRequest {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Profile profile;
	
	@Column(name = "category", nullable = false)
	private VerificationCategory category;
	
	@Column(name = "filePath", nullable = false)
	private String filePath;
	
	@Column(name = "approved", nullable = false)
	private boolean approved;
	
	public VerificationRequest() {
		super();
	}

	public VerificationRequest(long id, Profile profile, VerificationCategory category, String filePath,
			boolean approved) {
		super();
		this.id = id;
		this.profile = profile;
		this.category = category;
		this.filePath = filePath;
		this.approved = approved;
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

	public VerificationCategory getCategory() {
		return category;
	}

	public void setCategory(VerificationCategory category) {
		this.category = category;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	
	
	
	
}
