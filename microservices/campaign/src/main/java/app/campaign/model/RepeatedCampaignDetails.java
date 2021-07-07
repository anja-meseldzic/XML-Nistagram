package app.campaign.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

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

    @ElementCollection
    private Set<LocalTime> exposureTimes = new HashSet<>();

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

    public void setTimesPerDay(int timesPerDay) throws Exception {
        if(timesPerDay > 5)
            throw new Exception("Campaign can occur up to 5 times per day");
        this.timesPerDay = timesPerDay;
        setExposureTimes();
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

    public Set<LocalTime> getExposureTimes() {
        return exposureTimes;
    }

    public void setExposureTimes() {
        exposureTimes = new HashSet<>();
        LocalTime minTime = LocalTime.of(8, 0);
        LocalTime maxTime = LocalTime.of(22, 0);
        int totalMinutes = (maxTime.getHour()*60+maxTime.getMinute()) - (minTime.getHour()*60+minTime.getMinute());
        for(int i = 1; i <= timesPerDay; i++) {
            int minutes = minTime.getHour()*60+minTime.getMinute() + ((totalMinutes/timesPerDay)*i);
            LocalTime time = LocalTime.of((int)Math.floor(minutes / 60), minutes % 60);
            exposureTimes.add(time);
        }
    }
}
