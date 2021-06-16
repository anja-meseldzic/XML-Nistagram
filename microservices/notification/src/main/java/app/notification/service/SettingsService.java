package app.notification.service;

public interface SettingsService {

    void createSettingsPerProfile(String profile);

    void createSettingsPerFollow(long followId);

    void deleteSettingsPerFollow(long followId);
}
