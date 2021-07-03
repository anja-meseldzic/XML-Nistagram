package app.campaign.controller;

import app.campaign.dto.CampaignDTO;
import app.campaign.service.AuthService;
import app.campaign.service.CampaignService;
import app.campaign.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "campaign")
public class CampaignController {

    private AuthService authService;
    private CampaignService campaignService;

    @Autowired
    public CampaignController(AuthService authService, CampaignService campaignService) {
        this.authService = authService;
        this.campaignService = campaignService;
    }

    @PostMapping
    public void create(@RequestBody CampaignDTO dto, @RequestHeader("Authorization") String auth) {
        if(!authService.verify(auth, "AGENT"))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        String token = TokenUtils.getToken(auth);
        String username = authService.getUsernameFromToken(token);
        try {
            campaignService.create(dto, username);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable("id") long id, @RequestHeader("Authorization") String auth) {
        if(!authService.verify(auth, "AGENT"))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        String token = TokenUtils.getToken(auth);
        String username = authService.getUsernameFromToken(token);
        try {
            campaignService.delete(id, username);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
