package app.profile.service.impl;

import app.profile.model.Follow;
import app.profile.model.Profile;
import app.profile.repository.FollowRepository;
import app.profile.repository.ProfileRepository;
import app.profile.service.ProfileInteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
        Follow interaction = followRepository.findFirstByProfileAndFollowedBy(pBlocked, pBlockedBy);
        if(interaction == null)
            interaction = new Follow(pBlocked, pBlockedBy, false, false, true, false);
        else
            interaction.setBlocked(true);
        followRepository.save(interaction);
    }

    @Override
    public void muteProfile(String muted, String mutedBy) {
        Profile pMuted = profileRepository.findByRegularUserUsername(muted);
        Profile pMutedBy = profileRepository.findByRegularUserUsername(mutedBy);
        Follow interaction = followRepository.findFirstByProfileAndFollowedBy(pMuted, pMutedBy);
        if(interaction == null)
            interaction = new Follow(pMuted, pMutedBy, false, true, false, false);
        else
            interaction.setMuted(true);
        followRepository.save(interaction);
    }

    @Override
    public void unblockProfile(String blocked, String blockedBy) {
        Profile pBlocked = profileRepository.findByRegularUserUsername(blocked);
        Profile pBlockedBy = profileRepository.findByRegularUserUsername(blockedBy);
        Follow interaction = followRepository.findFirstByProfileAndFollowedBy(pBlocked, pBlockedBy);
        if(interaction == null)
            throw new IllegalArgumentException("No blocked profiles with given usernames");
        else
            interaction.setBlocked(false);
        followRepository.save(interaction);
    }

    @Override
    public void unmuteProfile(String muted, String mutedBy) {
        Profile pMuted = profileRepository.findByRegularUserUsername(muted);
        Profile pMutedBy = profileRepository.findByRegularUserUsername(mutedBy);
        Follow interaction = followRepository.findFirstByProfileAndFollowedBy(pMuted, pMutedBy);
        if(interaction == null)
            throw new IllegalArgumentException("No muted profiles with given usernames");
        else
            interaction.setMuted(false);
        followRepository.save(interaction);
    }
}
