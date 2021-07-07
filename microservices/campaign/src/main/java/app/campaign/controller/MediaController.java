package app.campaign.controller;

import app.campaign.dto.CampaignForUserDTO;
import app.campaign.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "media")
public class MediaController {

    private CampaignService campaignService;

    @Autowired
    public MediaController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @PostMapping(value = "display/{id}")
    public boolean shouldDisplay(@PathVariable("id") long id) {
        return campaignService.shouldDisplayMedia(id);
    }

    @PostMapping(value = "{id}")
    public boolean partOfCampaign(@PathVariable("id") long id) {
        return campaignService.isPartOfCampaign(id);
    }

    @GetMapping(value = "{username}")
    public List<CampaignForUserDTO> getCampaignsForUser(@PathVariable("username") String username) {
        return campaignService.getCampaigns(username);
    }
}
