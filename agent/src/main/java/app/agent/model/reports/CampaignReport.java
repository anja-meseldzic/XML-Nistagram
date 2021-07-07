package app.agent.model.reports;

import org.apache.tomcat.jni.Local;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.time.LocalDateTime;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "campaign")
public class CampaignReport {
    private String name;
    private String created;
    private int likes;
    private int dislikes;
    private int comments;
    private int clicks;
    private double moneyIncrease;

    public CampaignReport() {
    }

    public CampaignReport(String name, LocalDateTime created, int likes, int dislikes, int comments, int clicks, double moneyIncrease) {
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
