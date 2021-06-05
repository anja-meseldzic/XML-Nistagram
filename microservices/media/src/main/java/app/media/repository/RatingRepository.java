package app.media.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.media.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long>{

}
