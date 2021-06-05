package app.media.dtos;

public class RatingDTO {
	private long id;
	private boolean isLike;
	
	public RatingDTO() {
		super();
	}

	public RatingDTO(long id, boolean isLike) {
		super();
		this.id = id;
		this.isLike = isLike;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isLike() {
		return isLike;
	}

	public void setIsLike(boolean isLike) {
		this.isLike = isLike;
	}
	
	
	
}
