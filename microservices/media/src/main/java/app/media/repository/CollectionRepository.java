package app.media.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.media.model.Collection;
import app.media.model.Favourites;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long>{
	Collection findByFavourite(Favourites favourite);
}
