package app.campaign.repository;

import app.campaign.model.LinkClick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkClickRepository extends JpaRepository<LinkClick, Long> {
}
