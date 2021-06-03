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
public class OneTimeMessage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Message message;
	
	@Column(name = "linkToSource", nullable = false)
	private String linkToSource;
	
	@Column(name = "seen", nullable = false)
	private boolean seen;

	public OneTimeMessage() {
		super();
	}

	public OneTimeMessage(long id, Message message, String linkToSource, boolean seen) {
		super();
		this.id = id;
		this.message = message;
		this.linkToSource = linkToSource;
		this.seen = seen;
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

	public boolean isSeen() {
		return seen;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
	}
	
	
}
