package app.campaign.service;

import app.campaign.dto.CampaignDTO;

public interface CampaignService {

    void create(CampaignDTO dto, String agent) throws Exception;

    void delete(long id, String agent) throws Exception;
}
