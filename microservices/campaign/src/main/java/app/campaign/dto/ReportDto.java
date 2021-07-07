package app.campaign.dto;

import java.util.List;

public class ReportDto {
    public PostReportDto postReport;
    public List<ClicksByProfileDto> clicks;
    public long totalClicks;
    public int timesPublished;
}
