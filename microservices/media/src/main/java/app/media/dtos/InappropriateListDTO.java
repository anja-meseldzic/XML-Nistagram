package app.media.dtos;

public class InappropriateListDTO {
	private Long id;
	private String reason;
	private String usernameOfReporter;
	private Long postId;
	private Long mediaId;
	
	public InappropriateListDTO() {
		super();
	}
	public InappropriateListDTO(Long id, String reason, String usernameOfReporter, Long postId, Long mediaId) {
		super();
		this.id = id;
		this.reason = reason;
		this.usernameOfReporter = usernameOfReporter;
		this.postId = postId;
		this.mediaId = mediaId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getUsernameOfReporter() {
		return usernameOfReporter;
	}
	public void setUsernameOfReporter(String usernameOfReporter) {
		this.usernameOfReporter = usernameOfReporter;
	}
	public Long getPostId() {
		return postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
	}
	public Long getMediaId() {
		return mediaId;
	}
	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
	}
	
	
	
}
