package app.campaign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "auth", url = "${app.auth.url}")
public interface MediaService {
    @PostMapping("media/{id}")
    boolean exists(@PathVariable("id") long id);
    @DeleteMapping("media/{id}")
    void delete(@PathVariable("id") long id);
}
