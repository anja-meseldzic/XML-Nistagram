package app.profile.model;

import javax.persistence.*;

@Entity
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Profile profile;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Profile followedBy;
    @Column(name = "closeFriend", nullable = false)
    private boolean closeFriend;
    @Column(name = "muted", nullable = false)
    private boolean muted;
    @Column(name = "blocked", nullable = false)
    private boolean blocked;

    public Follow() {

    }

    public Follow(Profile profile, Profile followedBy, boolean closeFriend, boolean muted, boolean blocked) {
        this.profile = profile;
        this.followedBy = followedBy;
        this.closeFriend = closeFriend;
        this.muted = muted;
        this.blocked = blocked;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Profile getFollowedBy() {
        return followedBy;
    }

    public void setFollowedBy(Profile followedBy) {
        this.followedBy = followedBy;
    }

    public boolean isCloseFriend() {
        return closeFriend;
    }

    public void setCloseFriend(boolean closeFriend) {
        this.closeFriend = closeFriend;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
