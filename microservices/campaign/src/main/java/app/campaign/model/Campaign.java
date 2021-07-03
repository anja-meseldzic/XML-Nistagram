package app.campaign.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Campaign {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "profileId", nullable = false)
	private long profileId;
	
	@Column(name = "mediaId", nullable = false)
	private long mediaId;

	@Column(name = "mediaType", nullable = false)
	private MediaType mediaType;

	@Column(name = "link", nullable = false)
	private String link;

	public Campaign() {
		super();
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

	public long getMediaId() {
		return mediaId;
	}

	public void setMediaId(long mediaId) {
		this.mediaId = mediaId;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public MediaType getMediaType() {
		return mediaType;
	}

	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}
}
