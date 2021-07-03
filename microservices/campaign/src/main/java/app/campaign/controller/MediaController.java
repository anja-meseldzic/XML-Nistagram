package app.campaign.controller;

import app.campaign.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
