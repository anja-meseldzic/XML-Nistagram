package app.profile.service;

import app.profile.model.Follow;

public interface ProfileInteractionService {
    void blockProfile(String blocked, String blockedBy);
    void muteProfile(String muted, String mutedBy);
    void unblockProfile(String blocked, String blockedBy);
    void unmuteProfile(String muted, String mutedBy);
}
