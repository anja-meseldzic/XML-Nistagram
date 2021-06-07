package app.media.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.media.model.Collection;

public interface CollectionRepository extends JpaRepository<Collection, Long>{

}
