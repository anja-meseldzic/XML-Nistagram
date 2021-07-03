package app.campaign.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Campaign {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "agentUsername", nullable = false)
	private String agentUsername;
	
	@Column(name = "mediaId", nullable = false)
	private long mediaId;

	@Column(name = "link", nullable = false)
	private String link;

	@Column(name = "start", nullable = false)
	private LocalDateTime start;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private TargetGroup targetGroup;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<RepeatedCampaignDetails> details;

	public Campaign() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAgentUsername() {
		return agentUsername;
	}

	public void setAgentUsername(String agentUsername) {
		this.agentUsername = agentUsername;
	}

	public long getMediaId() {
		return mediaId;
	}

	public void setMediaId(long mediaId) {
		this.mediaId = mediaId;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) throws Exception {
		if(start.isBefore(LocalDateTime.now()))
			throw new Exception("Campaign must start in the future");
		this.start = start;
	}

	public TargetGroup getTargetGroup() {
		return targetGroup;
	}

	public void setTargetGroup(TargetGroup targetGroup) {
		this.targetGroup = targetGroup;
	}

	public Set<RepeatedCampaignDetails> getDetails() {
		return details;
	}

	public void setDetails(Set<RepeatedCampaignDetails> details) {
		this.details = details;
	}

	public boolean isRepeated() {
		return details != null;
	}

	public RepeatedCampaignDetails getActiveDetails() {
		if(details == null) return null;
		return details.stream()
				.filter(d -> d.applicable())
				.max(Comparator.comparing(c -> c.getCreated()))
				.orElse(null);
	}

	public void addDetails(RepeatedCampaignDetails newDetails) throws Exception {
		if(newDetails == null)
			return;
		if(newDetails.getEndDate().isBefore(start.toLocalDate()))
			throw new Exception("End date must be after start date");
		if(details == null)
			details = new HashSet<>();
		details.add(newDetails);
	}

	public boolean started() {
		return start.isBefore(LocalDateTime.now());
	}

	public boolean ended() {
		if(isRepeated() && getActiveDetails().getEndDate().isBefore(LocalDate.now()))
			return true;
		if(!isRepeated() && started())
			return true;
		return false;
	}

	public boolean active() {
		return started() && !ended();
	}
}
