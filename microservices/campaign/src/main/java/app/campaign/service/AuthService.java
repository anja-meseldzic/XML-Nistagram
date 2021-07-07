package app.campaign.service;

import app.campaign.model.TargetGroup;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "auth", url = "${app.auth.url}")
public interface AuthService {
    @PostMapping("/auth/{token}/{role}")
    boolean verify(@PathVariable("token") String token, @PathVariable("role") String role);
    @GetMapping("/auth/{token}")
    String getUsernameFromToken(@PathVariable("token") String token);
    @GetMapping("/auth/target-group")
    List<String> getTargetGroup(@RequestBody TargetGroup targetGroup);
}
