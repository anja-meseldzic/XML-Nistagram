package app.agent.model.dtos.campaign;

public class ClicksByProfileDto {
    public String profile;
    public int clicks;

    public ClicksByProfileDto(String profile, int clicks) {
        this.profile = profile;
        this.clicks = clicks;
    }

    public ClicksByProfileDto() {
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }
}
