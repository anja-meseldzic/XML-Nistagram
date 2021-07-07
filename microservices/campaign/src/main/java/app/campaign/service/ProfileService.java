package app.campaign.service;

import app.campaign.model.TargetGroup;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "profile", url = "${app.profile.url}")
public interface ProfileService {
    @GetMapping("profile/ms/followers/{profile}")
    List<String> getFollowers(@PathVariable("profile") String profile);
    @GetMapping("profile/ms/following/{profile}")
    List<String> getFollowing(@PathVariable("profile") String profile);;
}
