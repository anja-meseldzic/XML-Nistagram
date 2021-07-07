package app.campaign.dto;

public class ClicksByProfileDto {

    public String profile;
    public int clicks;

    public ClicksByProfileDto(String profile, int clicks) {
        this.profile = profile;
        this.clicks = clicks;
    }
}
