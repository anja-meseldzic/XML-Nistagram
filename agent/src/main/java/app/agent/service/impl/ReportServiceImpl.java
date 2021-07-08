package app.agent.service.impl;

import app.agent.client.ReportHttpClient;
import app.agent.model.reports.CampaignReport;
import app.agent.model.reports.CampaignReports;
import app.agent.repository.ReportRepository;
import app.agent.service.ReportService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final ReportHttpClient reportHttpClient;

    @Value("${media.storage}")
    private String storageDirectory;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository, ReportHttpClient reportHttpClient) {
        this.reportRepository = reportRepository;
        this.reportHttpClient = reportHttpClient;
    }

    @Override
    public String generatePDFReport() {
//        CampaignReport cr1 = new CampaignReport("campaign1", LocalDateTime.now(), 4, 4, 5, 4, 456123);
//        CampaignReport cr2 = new CampaignReport("campaign2", LocalDateTime.now(), 23, 45, 54, 42, 45643);
//        CampaignReport cr3 = new CampaignReport("campaihn3", LocalDateTime.now(), 44, 34, 54, 24, 45655);

        CampaignReports reports = new CampaignReports();
        reportHttpClient.fetchReports().forEach(reports::addCampaign);
//        reports.addCampaign(cr1);
//        reports.addCampaign(cr2);
//        reports.addCampaign(cr3);
        UUID id = reportRepository.generate(reports);
        generatePDF(id.toString(), reports);
        return "/merch/content/" + id + ".pdf";
    }

    @Override
    public String fetchResourceNames() {
        return reportRepository.fetchResourceNames();
    }

        private void generatePDF(String filename, CampaignReports campaignReports) {
        Document doc = new Document();
        try {
            Path dest = Paths.get(storageDirectory + File.separator + filename + ".pdf");
            PdfWriter.getInstance(doc, new FileOutputStream(dest.toAbsolutePath().toString()));
            doc.open();

            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Chunk chunk = new Chunk("Report", font);
            doc.add(chunk);

            doc.add(Chunk.NEWLINE);

            doc.add(new Paragraph("id: " + filename));

            doc.add(Chunk.NEWLINE);
            doc.add(Chunk.NEWLINE);
            doc.add(Chunk.NEWLINE);

            doc.add(new Paragraph("Here are the results of all campaigns"));

            doc.add(Chunk.NEWLINE);
            doc.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(7);
            addTableHeaders(table);
            addRows(table, campaignReports);
            doc.add(table);
            doc.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private void addTableHeaders(PdfPTable table) {
        Stream.of("Id", "Created On", "Likes", "Dislikes", "Number of Comments", "Number of Clicks", "Total Salary")
                .forEach(column -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setPhrase(new Phrase(column));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table, CampaignReports reports) {
        reports.getCampaigns().forEach(r -> {
            table.addCell("" + r.getId());
            table.addCell(r.getCreated());
            table.addCell("" + r.getLikes());
            table.addCell("" + r.getDislikes());
            table.addCell("" + r.getComments());
            table.addCell("" + r.getClicks());
            table.addCell("" + r.getMoneyIncrease());
        });
    }
}
