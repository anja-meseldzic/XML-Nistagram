package app.agent.model.reports;

import app.agent.model.dtos.campaign.CampaignReportDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "campaign")
public class CampaignReport {
    private long id;
    private String created;
    private int likes;
    private int dislikes;
    private int comments;
    private int clicks;
    private double moneyIncrease;

    public CampaignReport() {
    }

    public CampaignReport(long id, LocalDateTime created, int likes, int dislikes, int comments, int clicks, double moneyIncrease) {
        this.id = id;
        this.created = created.toString();
        this.likes = likes;
        this.dislikes = dislikes;
        this.comments = comments;
        this.clicks = clicks;
        this.moneyIncrease = moneyIncrease;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public double getMoneyIncrease() {
        return moneyIncrease;
    }

    public void setMoneyIncrease(double moneyIncrease) {
        this.moneyIncrease = moneyIncrease;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void from(CampaignReportDTO campaignReportDTO) {
        this.id = campaignReportDTO.getId();
        this.created = campaignReportDTO.getStart().toString();
        if(campaignReportDTO.getReport().getPostReport() != null) {
            this.likes = campaignReportDTO.getReport().getPostReport().getLikeCount();
            this.dislikes = campaignReportDTO.getReport().getPostReport().getDislikeCount();
            this.comments = campaignReportDTO.getReport().getPostReport().getCommentCount();
        }
        else {
            this.likes = 0;
            this.dislikes = 0;
            this.comments = 0;
        }
        this.clicks = (int) campaignReportDTO.getReport().getTotalClicks();
    }
}
