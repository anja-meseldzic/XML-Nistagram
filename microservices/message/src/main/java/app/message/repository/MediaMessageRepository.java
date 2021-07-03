package app.message.repository;

import app.message.model.MediaMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaMessageRepository extends JpaRepository<MediaMessage, Long> {
}
