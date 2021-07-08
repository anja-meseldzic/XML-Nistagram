package app.campaign.repository;

import app.campaign.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    Collection<Campaign> getAllByAgentUsername(String username);
}
