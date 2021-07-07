package app.profile.repository;

import app.profile.model.ProfileMessagePermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessagePermissionRepository extends JpaRepository<ProfileMessagePermission, Long> {
}
