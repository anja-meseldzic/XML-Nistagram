package app.campaign.dto;

import java.time.LocalDate;

public class DetailsDTO {

    private LocalDate endDate;
    private int timesPerDay;

    public DetailsDTO() { }

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
}
