package app.campaign.service;

import app.campaign.dto.PostReportDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "media", url = "${app.media.url}")
public interface MediaService {
    @PostMapping("media/{id}")
    boolean exists(@PathVariable("id") long id);
    @DeleteMapping("media/{id}")
    void delete(@PathVariable("id") long id);
    @GetMapping("post/report/{id}")
    PostReportDto getReport(@PathVariable("id") long id);
}
