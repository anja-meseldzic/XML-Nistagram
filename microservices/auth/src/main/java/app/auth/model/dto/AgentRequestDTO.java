package app.auth.model.dto;

public class AgentRequestDTO {

	private Long idOfRequest;
	private String name;
    private String surname;
    private String website;
    private String email;
    
	public AgentRequestDTO() {
		super();
	}

	public AgentRequestDTO(Long idOfRequest, String name, String surname, String website, String email) {
		super();
		this.idOfRequest = idOfRequest;
		this.name = name;
		this.surname = surname;
		this.website = website;
		this.email = email;
	}
	
	public Long getIdOfRequest() {
		return idOfRequest;
	}
	public void setIdOfRequest(Long idOfRequest) {
		this.idOfRequest = idOfRequest;
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
    
    
}
