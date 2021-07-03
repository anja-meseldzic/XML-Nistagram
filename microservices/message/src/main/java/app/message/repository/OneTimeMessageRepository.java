package app.message.repository;

import app.message.model.OneTimeMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OneTimeMessageRepository extends JpaRepository<OneTimeMessage, Long> {
}
