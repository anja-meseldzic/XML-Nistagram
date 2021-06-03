package app.campaign.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CampaignUpdate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private MultipleTimeCampaign campaign;
	
	@Column(name = "dateCreated", nullable = false)
	private LocalDateTime dateCreated;
	
	@Column(name = "startDate", nullable = false)
	private LocalDateTime startDate;
	
	@Column(name = "endDate", nullable = false)
	private LocalDateTime endDate;
	
	@Column(name = "timesPerDay", nullable = false)
	private int timesPerDay;
	
	@Column(name = "hoursUntilApply", nullable = false)
	private int hoursUntilApply;

	public CampaignUpdate() {
		super();
	}

	public CampaignUpdate(long id, MultipleTimeCampaign campaign, LocalDateTime dateCreated, LocalDateTime startDate,
			LocalDateTime endDate, int timesPerDay, int hoursUntilApply) {
		super();
		this.id = id;
		this.campaign = campaign;
		this.dateCreated = dateCreated;
		this.startDate = startDate;
		this.endDate = endDate;
		this.timesPerDay = timesPerDay;
		this.hoursUntilApply = hoursUntilApply;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public MultipleTimeCampaign getCampaign() {
		return campaign;
	}

	public void setCampaign(MultipleTimeCampaign campaign) {
		this.campaign = campaign;
	}

	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public int getTimesPerDay() {
		return timesPerDay;
	}

	public void setTimesPerDay(int timesPerDay) {
		this.timesPerDay = timesPerDay;
	}

	public int getHoursUntilApply() {
		return hoursUntilApply;
	}

	public void setHoursUntilApply(int hoursUntilApply) {
		this.hoursUntilApply = hoursUntilApply;
	}
	
	 

}
