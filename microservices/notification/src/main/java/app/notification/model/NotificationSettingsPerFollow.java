package app.notification.model;

public class NotificationSettingsPerFollow {

    private long id;
    private long followId;
    private boolean notifyOnMessage;
    private boolean notifyOnPost;
    private boolean notifyOnStory;
    private boolean notifyOnComment;

    public NotificationSettingsPerFollow(long followId, boolean notifyOnMessage, boolean notifyOnPost, boolean notifyOnStory, boolean notifyOnComment) {
        this.followId = followId;
        this.notifyOnMessage = notifyOnMessage;
        this.notifyOnPost = notifyOnPost;
        this.notifyOnStory = notifyOnStory;
        this.notifyOnComment = notifyOnComment;
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
