package app.media.service;

import app.media.dtos.CampaignForUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "campaign", url = "${app.campaign.url}")
public interface CampaignService {
    @PostMapping("media/display/{id}/{isPost}")
    boolean shouldDispaly(@PathVariable("id") long id, @PathVariable("isPost") boolean isPost);
    @PostMapping("media/{id}")
    boolean isPartOfCampaign(@PathVariable("id") long id);
    @GetMapping("media/{username}")
    List<CampaignForUserDTO> getCampaignsForUser(@PathVariable("username") String username);
    @GetMapping("campaign/link/{id}")
    String getLink(@PathVariable("id") long id);
    @PostMapping("campaign/{id}/{username}")
    void saveLinkClick(@PathVariable("id") long id, @PathVariable("username") String username);
}
