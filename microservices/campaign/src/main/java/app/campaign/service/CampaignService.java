package app.campaign.service;

import app.campaign.dto.CampaignDTO;
import app.campaign.dto.CampaignForUserDTO;
import app.campaign.dto.DetailsDTO;
import app.campaign.dto.ReportDto;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CampaignService {

    void create(CampaignDTO dto, String agent) throws Exception;

    void delete(long id, String agent) throws Exception;

    void update(long id, DetailsDTO dto, String agent) throws Exception;

    List<CampaignDTO> get(String agent);

    boolean shouldDisplayMedia(long mediaId);

    boolean isPartOfCampaign(long mediaId);

    List<CampaignForUserDTO> getCampaigns(String username);

    String getLink(long id);

    void saveLinkClick(long mediaId, String profile);

    ReportDto getReport(long id);
}
