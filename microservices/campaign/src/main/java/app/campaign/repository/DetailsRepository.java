package app.campaign.repository;

import app.campaign.model.RepeatedCampaignDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailsRepository extends JpaRepository<RepeatedCampaignDetails, Long> {
}
