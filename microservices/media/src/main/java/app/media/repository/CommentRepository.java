package app.media.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.media.model.Comment;


public interface CommentRepository extends JpaRepository<Comment, Long> {

}
