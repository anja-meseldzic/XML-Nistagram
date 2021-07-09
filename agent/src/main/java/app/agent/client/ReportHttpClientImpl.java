package app.agent.client;

import app.agent.model.dtos.campaign.CampaignReportDTO;
import app.agent.model.dtos.campaign.ReportDTO;
import app.agent.model.reports.CampaignReport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ReportHttpClientImpl implements ReportHttpClient{
    @Value("${api.key}")
    private String apiKey;

    @Value("${app.campaign.url}")
    private String url;

    @Override
    public Collection<CampaignReport> fetchReports() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("ApiKey", this.apiKey);
        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<CampaignReportDTO[]> res = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                CampaignReportDTO[].class);

        CampaignReportDTO[] reports = res.getBody();
        if(reports != null) {
            return Arrays.stream(reports).map(r -> {
                CampaignReport campaignReport = new CampaignReport();
                campaignReport.from(r);
                return campaignReport;
            }).collect(Collectors.toList());
        }
        else
            return new ArrayList<>();
    }
}
