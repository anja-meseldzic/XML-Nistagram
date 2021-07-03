package app.campaign.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class CampaignDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Campaign campaign;

    @Column(name = "type", nullable = false)
    private CampaignType type;

    @Column(name = "startDate", nullable = false)
    private LocalDate startDate;

    @Column(name = "endDate", nullable = false)
    private LocalDate endDate;

    @Column(name = "timesPerDay", nullable = false)
    private int timesPerDay;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private TargetGroup targetGroup;

    public CampaignDetails() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public CampaignType getType() {
        return type;
    }

    public void setType(CampaignType type) {
        this.type = type;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getTimesPerDay() {
        return timesPerDay;
    }

    public void setTimesPerDay(int timesPerDay) {
        this.timesPerDay = timesPerDay;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
