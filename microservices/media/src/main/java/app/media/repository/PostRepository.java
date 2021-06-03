package app.media.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.media.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
