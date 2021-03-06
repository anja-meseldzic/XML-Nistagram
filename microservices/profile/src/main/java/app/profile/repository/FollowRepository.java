package app.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.profile.model.Follow;
import app.profile.model.Profile;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

	List<Follow> findByProfile(Profile profile);

	List<Follow> findByFollowedBy(Profile followedBy);

	Follow findFirstByProfileAndFollowedBy(Profile profile, Profile followedBy);
	List<Follow> findByFollowedBy_RegularUserUsernameAndMutedIsTrue(String username);
	List<Follow> findByFollowedBy_RegularUserUsernameAndBlockedIsTrue(String username);
}
