package app.media.dtos;

public class InappropriateDTO {
	private Long idOfContent;
	private String reason;
	
	public InappropriateDTO() {
		super();
	}

	public InappropriateDTO(Long idOfContent, String reason) {
		super();
		this.idOfContent = idOfContent;
		this.reason = reason;
	}

	public Long getIdOfContent() {
		return idOfContent;
	}

	public void setIdOfContent(Long idOfContent) {
		this.idOfContent = idOfContent;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	

}
