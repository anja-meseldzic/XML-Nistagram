package app.media.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.media.model.Favourites;
import app.media.model.Post;

public interface FavouritesRepository extends JpaRepository<Favourites, Long> {
	Favourites findByPost(Post post);
}
