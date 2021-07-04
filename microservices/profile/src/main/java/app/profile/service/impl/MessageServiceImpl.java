package app.profile.service.impl;

import app.profile.model.Profile;
import app.profile.repository.ProfileRepository;
import app.profile.service.MessageService;
import app.profile.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    private final ProfileRepository profileRepository;

    @Autowired
    public MessageServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public boolean verifyMessage(String receiver) {
        Profile profile = profileRepository.findByRegularUserUsername(receiver);
        return profile != null;
    }
}
