package app.media.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.media.model.InapropriateContent;
import app.media.model.Media;

@Repository
public interface InapropriateContentRepository extends JpaRepository<InapropriateContent, Long> {

	InapropriateContent findOneById(long id);
	InapropriateContent findByMedia(Media media);
}
