package app.profile.service.impl;

import app.profile.model.Follow;
import app.profile.model.Profile;
import app.profile.repository.FollowRepository;
import app.profile.repository.ProfileRepository;
import app.profile.service.ProfileInteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

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
    public void updateMute(String muted, String mutedBy) {
        Profile pMuted = profileRepository.findByRegularUserUsername(muted);
        Profile pMutedBy = profileRepository.findByRegularUserUsername(mutedBy);
        Follow interaction = followRepository.findFirstByProfileAndFollowedBy(pMuted, pMutedBy);
        if(interaction == null)
            throw new IllegalArgumentException("Profiles must be following one another before muting");
        else
            interaction.setMuted(!interaction.isMuted());
        followRepository.save(interaction);
    }

    @Override
    public void updateBlock(String blocked, String blockedBy) {
        Profile pBlocked = profileRepository.findByRegularUserUsername(blocked);
        Profile pBlockedBy = profileRepository.findByRegularUserUsername(blockedBy);
        Follow interaction = followRepository.findFirstByProfileAndFollowedBy(pBlocked, pBlockedBy);
        if(interaction == null)
            interaction = new Follow(pBlocked, pBlockedBy, false, false, true);
        else
            interaction.setBlocked(!interaction.isBlocked());
        followRepository.save(interaction);
    }

    @Override
    public Collection<String> getMutedProfiles(String username) {
        return followRepository.findByFollowedBy_RegularUserUsernameAndMutedIsTrue(username).stream()
                .map(f -> f.getProfile().getRegularUserUsername())
                .collect(Collectors.toList());
    }

    @Override
    public Collection<String> getBlockedProfiles(String username) {
        return followRepository.findByFollowedBy_RegularUserUsernameAndBlockedIsTrue(username).stream()
                .map(f -> f.getProfile().getRegularUserUsername())
                .collect(Collectors.toList());
    }
}
