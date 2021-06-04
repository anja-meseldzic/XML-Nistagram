package app.media.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.media.model.Story;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long>{

}
