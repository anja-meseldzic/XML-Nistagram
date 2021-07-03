package app.campaign.repository;

import app.campaign.model.CampaignDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailsRepository extends JpaRepository<CampaignDetails, Long> {
}
