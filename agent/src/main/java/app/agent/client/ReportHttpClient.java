package app.agent.client;

import app.agent.model.reports.CampaignReport;

import java.util.Collection;

public interface ReportHttpClient {
    Collection<CampaignReport> fetchReports();
}
