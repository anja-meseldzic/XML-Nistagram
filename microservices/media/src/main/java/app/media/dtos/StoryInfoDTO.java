package app.media.dtos;

import java.time.LocalDateTime;

public class StoryInfoDTO {

    private String username;
    private String url;
    private LocalDateTime created;

    public StoryInfoDTO() {

    }

    public StoryInfoDTO(String username, String url, LocalDateTime created) {
        this.username = username;
        this.url = url;
        this.created = created;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
