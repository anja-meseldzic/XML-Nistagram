package app.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.profile.model.Follow;
import app.profile.model.Profile;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

	List<Follow> findByProfile(Profile profile);

	List<Follow> findByFollowedBy(Profile followedBy);

	Follow findFirstByProfileAndAndFollowedBy(Profile profile, Profile followedBy);
}
