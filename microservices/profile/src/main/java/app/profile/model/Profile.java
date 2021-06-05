package app.profile.model;

import javax.persistence.*;

@Entity
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_generator")
	@SequenceGenerator(name="profile_generator", sequenceName = "profile_seq", allocationSize=50, initialValue = 100)
	private long id;
	
	@Column(name = "verified" , nullable = false)
	private boolean verified;
	
	@Column(name = "active" , nullable = false)
	private boolean active;
	
	@Column(name = "allowTagging" , nullable = false)
	private boolean allowTagging;
	
	@Column(name = "regularUserUsername" , nullable = false)
	private String regularUserUsername;
	
	@Column(name = "allowMessages" , nullable = false)
	private boolean allowMessages;
	
	@Column(name = "privateProfile" , nullable = false)
	private boolean privateProfile;
	
	public Profile() {
		super();
	}

	public Profile(long id, boolean verified, boolean active, boolean allowTagging, String regularUserId,
			boolean allowMessages, boolean privateProfile) {
		super();
		this.id = id;
		this.verified = verified;
		this.active = active;
		this.allowTagging = allowTagging;
		this.regularUserUsername = regularUserId;
		this.allowMessages = allowMessages;
		this.privateProfile = privateProfile;
	}

	public Profile(String username) {
		super();
		this.active = true;
		this.allowTagging = true;
		this.regularUserUsername = username;
		this.allowMessages = true;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isAllowTagging() {
		return allowTagging;
	}

	public void setAllowTagging(boolean allowTagging) {
		this.allowTagging = allowTagging;
	}

	public String getRegularUserUsername() {
		return regularUserUsername;
	}

	public void setRegularUserUsername(String regularUserId) {
		this.regularUserUsername = regularUserId;
	}

	public boolean isAllowMessages() {
		return allowMessages;
	}

	public void setAllowMessages(boolean allowMessages) {
		this.allowMessages = allowMessages;
	}

	public boolean isPrivateProfile() {
		return privateProfile;
	}

	public void setPrivateProfile(boolean privateProfile) {
		this.privateProfile = privateProfile;
	}

	@Override
	public boolean equals(Object obj) {
		return id == ((Profile)obj).id;
	}
}
