package app.notification.repository;

import app.notification.model.NotificationSettingsPerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SettingsPerProfileRepository extends JpaRepository<NotificationSettingsPerProfile, Long> {

    Optional<NotificationSettingsPerProfile> findByProfile(String profile);
}
