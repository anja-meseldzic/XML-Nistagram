package app.media.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Rating {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "profileId", nullable = false)
	private long profileId;
	
	@Column(name = "ratingType", nullable = false)
	private RatingType ratingType;

	
	public Rating() {
		super();
	}

	public Rating(long id, long profileId, RatingType ratingType) {
		super();
		this.id = id;
		this.profileId = profileId;
		this.ratingType = ratingType;
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

	public RatingType getRatingType() {
		return ratingType;
	}

	public void setRatingType(RatingType ratingType) {
		this.ratingType = ratingType;
	}
	
	
}
