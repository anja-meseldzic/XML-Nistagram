package app.notification.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "profile", url = "${app.profile.url}")
public interface FollowService {
    @GetMapping(value = "follow/followedBy/{followId}")
    String getProfileByFollow(@PathVariable("followId") long followId);
}
