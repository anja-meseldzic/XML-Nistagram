package app.media.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.media.model.InapropriateContent;
@Repository
public interface InapropriateContentRepository extends JpaRepository<InapropriateContent, Long> {

}
