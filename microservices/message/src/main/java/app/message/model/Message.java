package app.message.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Message {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "sender", nullable = false)
	private String sender;
	
	@Column(name = "receiver", nullable = false)
	private String receiver;
	
	@Column(name = "date", nullable = false)
	private LocalDateTime date;

	@Column(nullable = false)
	private MessageType type;

	@Column
	private String content;

	@Column
	private String linkToPost;

	@Column
	private String linkToSource;

	@Column
	private boolean seen;

	@Column
	private boolean active;

	public Message() {
		super();
	}

	public Message(Long id, String sender, String receiver, LocalDateTime date, MessageType type, String content, String linkToPost, String linkToSource) {
		this.id = id;
		this.sender = sender;
		this.receiver = receiver;
		this.date = date;
		this.type = type;
		this.content = content;
		this.linkToPost = linkToPost;
		this.linkToSource = linkToSource;
		this.seen = false;
		this.active = true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLinkToPost() {
		return linkToPost;
	}

	public void setLinkToPost(String linkToPost) {
		this.linkToPost = linkToPost;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
