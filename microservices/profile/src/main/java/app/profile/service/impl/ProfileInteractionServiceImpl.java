package app.profile.service.impl;

import app.profile.model.Follow;
import app.profile.model.Profile;
import app.profile.repository.FollowRepository;
import app.profile.repository.ProfileRepository;
import app.profile.service.ProfileInteractionService;
import org.springframework.beans.factory.annotation.Autowired;

public class ProfileInteractionServiceImpl implements ProfileInteractionService {
    private final FollowRepository followRepository;
    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileInteractionServiceImpl(FollowRepository followRepository, ProfileRepository profileRepository) {
        this.followRepository = followRepository;
        this.profileRepository = profileRepository;
    }

    @Override
    public void blockProfile(String blocked, String blockedBy) {
        Profile pBlocked = profileRepository.findByRegularUserUsername(blocked);
        Profile pBlockedBy = profileRepository.findByRegularUserUsername(blockedBy);
        Follow interaction = followRepository.findFirstByProfileAndAndFollowedBy(pBlocked, pBlockedBy);
    }

    @Override
    public void muteProfile(String muted, String mutedBy) {

    }
}
