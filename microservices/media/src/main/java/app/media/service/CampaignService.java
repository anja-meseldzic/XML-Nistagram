package app.media.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "campaign", url = "${app.campaign.url}")
public interface CampaignService {
    @PostMapping("media/display/{id}")
    boolean shouldDispaly(@PathVariable("id") long id);
    @PostMapping("media/{id}")
    boolean isPartOfCampaign(@PathVariable("id") long id);
}
