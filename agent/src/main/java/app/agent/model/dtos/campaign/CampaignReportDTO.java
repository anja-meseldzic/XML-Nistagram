package app.agent.model.dtos.campaign;

import java.time.LocalDateTime;

public class CampaignReportDTO {
    private long id;
    private LocalDateTime start;
    public ReportDTO report;

    public CampaignReportDTO() {
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

    public ReportDTO getReport() {
        return report;
    }

    public void setReport(ReportDTO report) {
        this.report = report;
    }
}
