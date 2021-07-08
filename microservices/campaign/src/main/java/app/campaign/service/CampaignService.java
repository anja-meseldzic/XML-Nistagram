package app.campaign.service;

import app.campaign.dto.*;

import org.springframework.web.bind.annotation.PathVariable;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface CampaignService {

    void create(CampaignDTO dto, String agent) throws Exception;

    void delete(long id, String agent) throws Exception;

    void update(long id, DetailsDTO dto, String agent) throws Exception;

    List<CampaignDTO> get(String agent);

    boolean shouldDisplayMedia(long mediaId);

    boolean isPartOfCampaign(long mediaId);

    Collection<CampaignReport> getCampaignsByAgent(String username);

    void createInfluencerCampaign(InfluencerCampaignDTO dto);

	ArrayList<CampaignDTO> getCampaignsForInfluencer(String username);

	void denyCampaign(String username, CampaignDTO dto);

	void acceptCampaign(String username, CampaignDTO dto);

    List<CampaignForUserDTO> getCampaigns(String username);

    String getLink(long id);

    void saveLinkClick(long mediaId, String profile);

    ReportDto getReport(long id);
}
