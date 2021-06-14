package app.profile.service;

import app.profile.model.Follow;

import java.util.Collection;

public interface ProfileInteractionService {
    void blockProfile(String blocked, String blockedBy);
    void muteProfile(String muted, String mutedBy);
    void unblockProfile(String blocked, String blockedBy);
    void unmuteProfile(String muted, String mutedBy);
    Collection<String> getMutedProfiles(String username);
    Collection<String> getBlockedProfiles(String username);
}
