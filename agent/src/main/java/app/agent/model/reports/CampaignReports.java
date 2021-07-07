package app.agent.model.reports;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "report")
public class CampaignReports {
    @XmlElement(name = "campaign")
    private List<CampaignReport> campaigns;

    public CampaignReports() {
        campaigns = new ArrayList<>();
    }

    public void addCampaign(CampaignReport campaignReport) {
        campaigns.add(campaignReport);
    }

    public List<CampaignReport> getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(List<CampaignReport> campaigns) {
        this.campaigns = campaigns;
    }
}
