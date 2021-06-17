package app.notification.repository;

import app.notification.model.NotificationSettingsPerFollow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SettingsPerFollowRepository extends JpaRepository<NotificationSettingsPerFollow, Long> {

    Optional<NotificationSettingsPerFollow> findByFollowId(long followId);
}
