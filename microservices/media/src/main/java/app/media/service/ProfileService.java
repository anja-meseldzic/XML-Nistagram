package app.media.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "profile", url = "${app.profile.url}")
public interface ProfileService {
    @GetMapping("profile/ms")
    List<String> getAll();
    @GetMapping("profile/ms/followers/{profile}")
    List<String> getFollowers(@PathVariable("profile") String profile);
    @GetMapping("profile/ms/following/{profile}")
    List<String> getFollowing(@PathVariable("profile") String profile);
    @GetMapping("profile/ms/muted/{profile}")
    List<String> getMuted(@PathVariable("profile") String profile);
    @GetMapping("profile/ms/blocked/{profile}")
    List<String> getBlocked(@PathVariable("profile") String profile);
    @GetMapping("profile/ms/close/{profile}")
    List<String> getCloseFriends(@PathVariable("profile") String profile);
    @GetMapping("profile/ms/public/{profile}")
    boolean isPublic(@PathVariable("profile") String profile);
}
