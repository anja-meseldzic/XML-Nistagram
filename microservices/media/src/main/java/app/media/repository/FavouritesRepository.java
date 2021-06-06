package app.media.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.media.model.Favourites;

public interface FavouritesRepository extends JpaRepository<Favourites, Long> {

}
