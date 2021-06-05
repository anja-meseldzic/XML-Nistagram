package app.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.profile.model.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long>{

	Profile findByRegularUserUsername(String username);
}
