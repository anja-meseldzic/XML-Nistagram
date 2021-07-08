package app.campaign.dto;

import app.campaign.model.Campaign;
import app.campaign.model.Gender;

import java.time.LocalDateTime;
import java.util.Set;

public class CampaignDTO {

    private long id;
    private long mediaId;
    private String link;
    private LocalDateTime start;
    private Set<Gender> targetedGenders;
    private Set<Long> targetedAges;
    private DetailsDTO details;
    public ReportDto report;

    public CampaignDTO() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMediaId() {
        return mediaId;
    }

    public void setMediaId(long mediaId) {
        this.mediaId = mediaId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public Set<Gender> getTargetedGenders() {
        return targetedGenders;
    }

    public void setTargetedGenders(Set<Gender> targetedGenders) {
        this.targetedGenders = targetedGenders;
    }

    public Set<Long> getTargetedAges() {
        return targetedAges;
    }

    public void setTargetedAges(Set<Long> targetedAges) {
        this.targetedAges = targetedAges;
    }

    public DetailsDTO getDetails() {
        return details;
    }

    public void setDetails(DetailsDTO details) {
        this.details = details;
    }
}
