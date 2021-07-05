package app.agent.service.impl;

import app.agent.model.reports.CampaignReport;
import app.agent.model.reports.CampaignReports;
import app.agent.repository.ReportRepository;
import app.agent.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public String generatePDFReport() {
        CampaignReport cr1 = new CampaignReport("campaign1", LocalDateTime.now(), 4, 4, 5, 4, 456123);
        CampaignReport cr2 = new CampaignReport("campaign2", LocalDateTime.now(), 23, 45, 54, 42, 45643);
        CampaignReport cr3 = new CampaignReport("campaihn3", LocalDateTime.now(), 44, 34, 54, 24, 45655);

        CampaignReports reports = new CampaignReports();
        reports.addCampaign(cr1);
        reports.addCampaign(cr2);
        reports.addCampaign(cr3);
        UUID id = reportRepository.generate(reports);
        return reportRepository.fetchReport(id.toString());
    }
}
