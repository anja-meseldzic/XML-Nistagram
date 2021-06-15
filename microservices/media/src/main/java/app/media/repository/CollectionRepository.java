package app.media.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.media.model.Collection;
@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long>{

}
