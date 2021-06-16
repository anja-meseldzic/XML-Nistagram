package app.notification.service.impl;

import app.notification.model.NotificationSettingsPerFollow;
import app.notification.model.NotificationSettingsPerProfile;
import app.notification.repository.SettingsPerFollowRepository;
import app.notification.repository.SettingsPerProfileRepository;
import app.notification.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SettingsServiceImpl implements SettingsService {

    private SettingsPerProfileRepository settingsPerProfileRepository;
    private SettingsPerFollowRepository settingsPerFollowRepository;

    @Autowired
    public SettingsServiceImpl(SettingsPerProfileRepository settingsPerProfileRepository, SettingsPerFollowRepository settingsPerFollowRepository) {
        this.settingsPerProfileRepository = settingsPerProfileRepository;
        this.settingsPerFollowRepository = settingsPerFollowRepository;
    }

    @Override
    public void createSettingsPerProfile(String profile) {
        if(!settingsPerProfileRepository.findByProfile(profile).isPresent())
            settingsPerProfileRepository.save(new NotificationSettingsPerProfile((profile)));
    }

    @Override
    public void createSettingsPerFollow(long followId) {
        if(!settingsPerFollowRepository.findByFollowId(followId).isPresent())
            settingsPerFollowRepository.save(new NotificationSettingsPerFollow(followId));
    }

    @Override
    public void deleteSettingsPerFollow(long followId) {
        Optional<NotificationSettingsPerFollow> settings = settingsPerFollowRepository.findByFollowId(followId);
        if(settings.isPresent())
            settingsPerFollowRepository.delete(settings.get());
    }
}
