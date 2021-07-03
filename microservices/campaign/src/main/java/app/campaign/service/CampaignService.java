package app.campaign.service;

import app.campaign.dto.CampaignDTO;

public interface CampaignService {

    void Create(CampaignDTO dto, String agent) throws Exception;
}
