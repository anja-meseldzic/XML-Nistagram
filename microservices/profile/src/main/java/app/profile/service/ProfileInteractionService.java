package app.profile.service;

import app.profile.model.Follow;

public interface ProfileInteractionService {
    void blockProfile(String blocked, String blockedBy);
    void muteProfile(String muted, String mutedBy);
}
