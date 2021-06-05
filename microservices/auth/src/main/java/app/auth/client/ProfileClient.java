package app.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "profile", url = "${app.profile.url}")
public interface ProfileClient {
    @PostMapping("/profile/{username}")
    void createFromUser(@PathVariable String username);
}
