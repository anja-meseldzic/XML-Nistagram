package app.profile.service;

import app.profile.dtos.UserInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "auth", url = "${app.auth.url}")
public interface AuthService {
    @GetMapping("/regulars/ms/{username}")
    UserInfoDTO get(@PathVariable("username") String username);
    @PostMapping("/auth/{token}/{role}")
    boolean verify(@PathVariable("token") String token, @PathVariable("role") String role);
    @GetMapping("/auth/{token}")
    String getUsernameFromToken(@PathVariable("token") String token);
}
