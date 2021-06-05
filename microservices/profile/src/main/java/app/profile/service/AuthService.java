package app.profile.service;

import app.profile.dtos.UserInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth", url = "${app.auth.url}")
public interface AuthService {
    @GetMapping("/regulars/ms/{username}")
    UserInfoDTO get(@RequestParam("username") String username);
}
