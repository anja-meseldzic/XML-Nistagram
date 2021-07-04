package app.profile.service;

public interface FollowService {
    String getFollowedBy(long followId);

    boolean doesFollow(String follower, String followee);
}
