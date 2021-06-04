package app.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.profile.model.Followers;

public interface FollowersRepository extends JpaRepository<Followers, Long>{
	
	Followers findByProfileId(long id);

}
