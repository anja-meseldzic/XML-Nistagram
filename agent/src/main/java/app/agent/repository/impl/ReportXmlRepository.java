package app.agent.repository.impl;

import app.agent.model.reports.CampaignReports;
import app.agent.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.UUID;

@Repository
public class ReportXmlRepository implements ReportRepository {

    @Value("${db.xml.username}")
    private String username;

    @Value("${db.xml.password}")
    private String password;

    @Value("${db.xml.url}")
    private String baseUrl;

    @Override
    public UUID generate(CampaignReports reports) {
        UUID id = UUID.randomUUID();
        String url = baseUrl + "/" + id + ".xml";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.setBasicAuth(username, password);

        StringWriter sw = new StringWriter();
        try {
            JAXBContext context = JAXBContext.newInstance("app.agent.model.reports");
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(reports, sw);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        HttpEntity<String> request = new HttpEntity<>(sw.toString(), headers);
        restTemplate.put(url, request);
        return id;
    }

    @Override
    public String fetchReport(String id) {
        String url = baseUrl + "/" + id + ".xml";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        System.out.println(response.getBody());
        return response.getBody();
    }

    @Override
    public String fetchResourceNames() {
        String url = baseUrl + "?_query=xmldb:get-child-resources('reports')&_howmany=100";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }
}
