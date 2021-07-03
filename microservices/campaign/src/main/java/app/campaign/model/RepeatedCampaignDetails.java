package app.campaign.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;

@Entity
public class RepeatedCampaignDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "endDate", nullable = false)
    private LocalDate endDate;

    @Column(name = "timesPerDay", nullable = false)
    private int timesPerDay;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    public RepeatedCampaignDetails() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public boolean applicable() {
        return created.isBefore(LocalDateTime.now().minusHours(24));
    }
}
