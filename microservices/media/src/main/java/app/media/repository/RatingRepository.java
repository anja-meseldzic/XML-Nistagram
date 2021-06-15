package app.media.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.media.model.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long>{

}
