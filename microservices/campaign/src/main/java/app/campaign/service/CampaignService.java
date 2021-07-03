package app.campaign.service;

import app.campaign.dto.CampaignDTO;
import app.campaign.dto.DetailsDTO;

import java.util.List;

public interface CampaignService {

    void create(CampaignDTO dto, String agent) throws Exception;

    void delete(long id, String agent) throws Exception;

    void update(long id, DetailsDTO dto, String agent) throws Exception;

    List<CampaignDTO> get(String agent);
}
