package app.agent.model.dtos.campaign;

import app.agent.model.dtos.campaign.ClicksByProfileDto;
import app.agent.model.dtos.campaign.PostReportDto;

import java.util.List;

public class ReportDTO {
    public PostReportDto postReport;
    public List<ClicksByProfileDto> clicks;
    public long totalClicks;
    public int timesPublished;

    public ReportDTO() {
    }

    public PostReportDto getPostReport() {
        return postReport;
    }

    public void setPostReport(PostReportDto postReport) {
        this.postReport = postReport;
    }

    public List<ClicksByProfileDto> getClicks() {
        return clicks;
    }

    public void setClicks(List<ClicksByProfileDto> clicks) {
        this.clicks = clicks;
    }

    public long getTotalClicks() {
        return totalClicks;
    }

    public void setTotalClicks(long totalClicks) {
        this.totalClicks = totalClicks;
    }

    public int getTimesPublished() {
        return timesPublished;
    }

    public void setTimesPublished(int timesPublished) {
        this.timesPublished = timesPublished;
    }
}
