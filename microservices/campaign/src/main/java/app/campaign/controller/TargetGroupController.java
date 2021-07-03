package app.campaign.controller;

import app.campaign.service.AuthService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "target-group")
public class TargetGroupController {

    private AuthService authService;
}
