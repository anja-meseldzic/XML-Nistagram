package app.profile.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import app.profile.model.FollowRequest;
import app.profile.model.Profile;

public interface FollowRequestRepository extends JpaRepository<FollowRequest, Long>{
	Set<FollowRequest> findByProfile(Profile profile);
}
