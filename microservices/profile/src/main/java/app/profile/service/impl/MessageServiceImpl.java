package app.profile.service.impl;

import app.profile.model.Profile;
import app.profile.repository.ProfileRepository;
import app.profile.service.FollowService;
import app.profile.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    private final ProfileRepository profileRepository;
    private final FollowService followService;

    @Autowired
    public MessageServiceImpl(ProfileRepository profileRepository, FollowService followService) {
        this.profileRepository = profileRepository;
        this.followService = followService;
    }

    @Override
    public boolean verifyMessage(String sender, String receiver) {
        Profile profile = profileRepository.findByRegularUserUsername(receiver);
        if (profile == null)
            return false;
        if (!followService.doesFollow(receiver, sender))
            return profile.isAllowMessages();
        return true;
    }
}
