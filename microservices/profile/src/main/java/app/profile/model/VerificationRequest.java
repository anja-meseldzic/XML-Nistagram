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
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "surname", nullable = false)
	private String surname;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Profile profile;
	
	@Column(name = "category", nullable = false)
	private VerificationCategory category;
	
	@Column(name = "filePath", nullable = false)
	private String filePath;
	
	@Column(name = "approved", nullable = false)
	private boolean approved;
	
	@Column(name = "deleted")
	private boolean deleted;
	
	public VerificationRequest() {
		super();
	}

	public VerificationRequest(long id,String name, String surname, Profile profile, VerificationCategory category, String filePath,
			boolean approved, boolean deleted) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.profile = profile;
		this.category = category;
		this.filePath = filePath;
		this.approved = approved;
		this.deleted = deleted;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
	
	
	
}
