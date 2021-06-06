package app.profile.model.dto;

public class FollowerDto {
	private String username;
	
	public FollowerDto() {}

	public FollowerDto(String username) {
		super();
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
