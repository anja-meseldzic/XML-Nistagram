package app.notification.model;

import javax.persistence.*;

@Entity
public class NotificationSettingsPerFollow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "followId")
    private long followId;
    @Column(name = "notifyOnMessage")
    private boolean notifyOnMessage;
    @Column(name = "notifyOnPost")
    private boolean notifyOnPost;
    @Column(name = "notifyOnStory")
    private boolean notifyOnStory;
    @Column(name = "notifyOnComment")
    private boolean notifyOnComment;

    public NotificationSettingsPerFollow(long followId, boolean notifyOnMessage, boolean notifyOnPost, boolean notifyOnStory, boolean notifyOnComment) {
        this.followId = followId;
        this.notifyOnMessage = notifyOnMessage;
        this.notifyOnPost = notifyOnPost;
        this.notifyOnStory = notifyOnStory;
        this.notifyOnComment = notifyOnComment;
    }

    public NotificationSettingsPerFollow() {

    }

    public NotificationSettingsPerFollow(long followId) {
        this.followId = followId;
        this.notifyOnMessage = true;
        this.notifyOnPost = false;
        this.notifyOnStory = false;
        this.notifyOnComment = true;
    }

    public void setNotifyOnMessage(boolean notifyOnMessage) {
        this.notifyOnMessage = notifyOnMessage;
    }

    public void setNotifyOnPost(boolean notifyOnPost) {
        this.notifyOnPost = notifyOnPost;
    }

    public void setNotifyOnStory(boolean notifyOnStory) {
        this.notifyOnStory = notifyOnStory;
    }

    public void setNotifyOnComment(boolean notifyOnComment) {
        this.notifyOnComment = notifyOnComment;
    }

    public long getId() {
        return id;
    }

    public long getFollowId() {
        return followId;
    }

    public boolean isNotifyOnMessage() {
        return notifyOnMessage;
    }

    public boolean isNotifyOnPost() {
        return notifyOnPost;
    }

    public boolean isNotifyOnStory() {
        return notifyOnStory;
    }

    public boolean isNotifyOnComment() {
        return notifyOnComment;
    }
}
