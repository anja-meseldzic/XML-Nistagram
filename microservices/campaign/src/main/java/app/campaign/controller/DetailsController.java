package app.campaign.controller;

import app.campaign.service.AuthService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "details")
public class DetailsController {

    private AuthService authService;
}
