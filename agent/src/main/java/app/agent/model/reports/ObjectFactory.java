package app.agent.model.reports;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

    public ObjectFactory() {
    }

    public CampaignReport createCampaignReport() {
        return new CampaignReport();
    }

    public CampaignReports createCampaignReports() {
        return new CampaignReports();
    }
}
