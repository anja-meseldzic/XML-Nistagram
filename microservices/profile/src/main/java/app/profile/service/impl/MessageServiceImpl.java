package app.profile.service.impl;

import app.profile.model.ProfileMessagePermission;
import app.profile.model.Profile;
import app.profile.repository.MessagePermissionRepository;
import app.profile.repository.ProfileRepository;
import app.profile.service.FollowService;
import app.profile.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    private final ProfileRepository profileRepository;
    private final MessagePermissionRepository messagePermissionRepository;
    private final FollowService followService;
    private final ProfileInteractionServiceImpl profileInteractionService;

    @Autowired
    public MessageServiceImpl(ProfileRepository profileRepository, MessagePermissionRepository messagePermissionRepository, FollowService followService, ProfileInteractionServiceImpl profileInteractionService) {
        this.profileRepository = profileRepository;
        this.messagePermissionRepository = messagePermissionRepository;
        this.followService = followService;
        this.profileInteractionService = profileInteractionService;
    }

    @Override
    public boolean verifyMessage(String sender, String receiver) {
        Profile profile = profileRepository.findByRegularUserUsername(receiver);
        boolean blocked = profileInteractionService.getBlockedProfiles(receiver).stream().anyMatch(p -> p.equals(sender));
        if (profile == null || blocked)
            return false;
        if (!followService.doesFollow(receiver, sender))
            return profile.isAllowMessages();
        return true;
    }

    @Override
    public void createMessagePermission(String sender, String receiver) {
        ProfileMessagePermission profileMessagePermission = new ProfileMessagePermission(sender, receiver);
        messagePermissionRepository.save(profileMessagePermission);
    }

    @Override
    public boolean verifyProfileRestrictions(String sender, String receiver) {
        Profile profile = profileRepository.findByRegularUserUsername(sender);
        boolean allowed = messagePermissionRepository.findAll().stream()
                .anyMatch(p -> (p.getFirstPeer().equals(sender) && p.getSecondPeer().equals(receiver)) ||
                        (p.getFirstPeer().equals(receiver) && p.getSecondPeer().equals(sender)));
        if(profile.isPrivateProfile() && !followService.doesFollow(sender, receiver))
            return allowed;
        else
            return true;
    }
}
