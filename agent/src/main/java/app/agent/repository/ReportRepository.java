package app.agent.repository;

import app.agent.model.reports.CampaignReports;

import java.util.UUID;

public interface ReportRepository {
    UUID generate(CampaignReports reports);

    String fetchReport(String id);

    String fetchResourceNames();
}
