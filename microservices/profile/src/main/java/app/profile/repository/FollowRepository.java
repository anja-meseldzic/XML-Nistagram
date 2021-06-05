package app.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.profile.model.Follow;
import app.profile.model.Profile;

public interface FollowRepository extends JpaRepository<Follow, Long> {

	Follow findByProfile(Profile profile);
	
}
