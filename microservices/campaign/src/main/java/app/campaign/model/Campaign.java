package app.campaign.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	
	@Column(name = "pathToContent", nullable = false)
	private String pathToContent;
	
	@Column(name = "typeOfContent", nullable = false)
	private TypeOfCampaignContent typeOfContent;
	

	@Column(name = "targetGroup")
	@ElementCollection(targetClass=Long.class, fetch = FetchType.LAZY)
	private Set<Long> targetGroup = new HashSet<Long>();

	public Campaign() {
		super();
	}
	
	public Campaign(long id, long profileId, String pathToContent, TypeOfCampaignContent typeOfContent,
			Set<Long> targetGroup) {
		super();
		this.id = id;
		this.profileId = profileId;
		this.pathToContent = pathToContent;
		this.typeOfContent = typeOfContent;
		this.targetGroup = targetGroup;
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

	public String getPathToContent() {
		return pathToContent;
	}

	public void setPathToContent(String pathToContent) {
		this.pathToContent = pathToContent;
	}

	public TypeOfCampaignContent getTypeOfContent() {
		return typeOfContent;
	}

	public void setTypeOfContent(TypeOfCampaignContent typeOfContent) {
		this.typeOfContent = typeOfContent;
	}

	public Set<Long> getTargetGroup() {
		return targetGroup;
	}

	public void setTargetGroup(Set<Long> targetGroup) {
		this.targetGroup = targetGroup;
	}
	
	
}
