package app.message.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class MediaMessage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Message message;
	
	@Column(name = "linkToSource", nullable = false)
	private String linkToSource;

	public MediaMessage() {
		super();
	}
	public MediaMessage(long id, Message message, String linkToSource) {
		super();
		this.id = id;
		this.message = message;
		this.linkToSource = linkToSource;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public String getLinkToSource() {
		return linkToSource;
	}

	public void setLinkToSource(String linkToSource) {
		this.linkToSource = linkToSource;
	}
	
	

}
