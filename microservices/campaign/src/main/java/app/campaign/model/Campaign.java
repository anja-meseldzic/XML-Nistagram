package app.campaign.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;


@Entity
public class Campaign {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "agentUsername", nullable = false)
	private String agentUsername;
	
	@ElementCollection
	private Set<Long> mediaIds;

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

	public Set<Long> getMediaIds() {
		return mediaIds;
	}

	public boolean containsMedia(long id) {
		return mediaIds != null && mediaIds.contains(id);
	}

	public void setMediaIds(Set<Long> mediaIds) {
		this.mediaIds = mediaIds;
	}

	public void addMedia(long id) {
		if(mediaIds == null) {
			mediaIds = new HashSet<>();
		}
		mediaIds.add(id);
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
		return details != null && details.size() > 0;
	}

	public RepeatedCampaignDetails getActiveDetails() {
		if(details == null) return null;
		return details.stream()
				.filter(d -> d.applicable())
				.max(Comparator.comparing(c -> c.getCreated()))
				.orElse(details.stream()
						.min(Comparator.comparing(cc -> cc.getCreated()))
						.orElse(null));
	}

	public RepeatedCampaignDetails getActiveDetails(LocalDate now) {
		if(details == null) return null;
		return details.stream()
				.filter(d -> d.applicable() && d.getCreated().toLocalDate().isBefore(now))
				.max(Comparator.comparing(c -> c.getCreated()))
				.orElse(details.stream()
						.min(Comparator.comparing(cc -> cc.getCreated()))
						.orElse(null));
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
		if(!isRepeated() && started() && start.plusHours(24).isAfter(LocalDateTime.now()))
			return true;
		return false;
	}

	public boolean active() {
		return started() && !ended();
	}

	public List<LocalDateTime> getExposureDates() {
		List<LocalDateTime> expDates = new ArrayList<>();
		LocalDate startDate = start.toLocalDate();
		if(!started()) {
			return expDates;
		}
		if(!isRepeated()) {
			expDates.add(start);
			return expDates;
		}
		while(startDate.isBefore(LocalDate.now().plusDays(1))) {
			RepeatedCampaignDetails details = getActiveDetails(startDate);
			for(LocalTime time : details.getExposureTimes()) {
				LocalDateTime dateTime = LocalDateTime.of(startDate, time);
				if(dateTime.isBefore(LocalDateTime.now()))
					expDates.add(dateTime);
			}
			startDate = startDate.plusDays(1);
		}
		return expDates;
	}
}
