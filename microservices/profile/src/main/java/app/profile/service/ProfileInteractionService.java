package app.profile.service;

import app.profile.model.Follow;

import java.util.Collection;

public interface ProfileInteractionService {
    void updateMute(String muted, String mutedBy);
    void updateBlock(String blocked, String blockedBy);
    Collection<String> getMutedProfiles(String username);
    Collection<String> getBlockedProfiles(String username);
}
