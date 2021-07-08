package app.campaign.dto;

import app.campaign.model.Campaign;
import app.campaign.model.Gender;

import java.time.LocalDateTime;
import java.util.Set;

public class CampaignReport {
    private long id;
    private LocalDateTime start;
    public ReportDto report;

    public CampaignReport() {
    }

    public CampaignReport(long id, LocalDateTime start, ReportDto report) {
        this.id = id;
        this.start = start;
        this.report = report;
    }

    public CampaignReport(Campaign campaign) {
        this.id = campaign.getId();
        this.start = campaign.getStart();
        this.report = null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public ReportDto getReport() {
        return report;
    }

    public void setReport(ReportDto report) {
        this.report = report;
    }
}
