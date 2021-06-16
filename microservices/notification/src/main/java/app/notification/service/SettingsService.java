package app.notification.service;

import app.notification.model.NotificationSettingsPerFollow;
import app.notification.model.NotificationSettingsPerProfile;

import java.util.List;

public interface SettingsService {

    void createSettingsPerProfile(String profile);

    void createSettingsPerFollow(long followId);

    void deleteSettingsPerFollow(long followId);

    void update(NotificationSettingsPerProfile settings, String requestedBy);

    void update(NotificationSettingsPerFollow settings, String requestedBy);

    List<NotificationSettingsPerFollow> getByProfile(String profile, String requestedBy);

    NotificationSettingsPerProfile get(String profile, String requestedBy);
}
