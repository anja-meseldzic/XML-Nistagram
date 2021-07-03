package app.campaign.controller;

import app.campaign.service.AuthService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "campaign")
public class CampaignController {

    private AuthService authService;
}
