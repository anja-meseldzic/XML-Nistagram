package app.campaign.controller;

import app.campaign.dto.CampaignDTO;
import app.campaign.dto.DetailsDTO;
import app.campaign.dto.InfluencerCampaignDTO;
import app.campaign.service.AuthService;
import app.campaign.service.CampaignService;
import app.campaign.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping
    public List<CampaignDTO> get(@RequestHeader("Authorization") String auth) {
        if(!authService.verify(auth, "AGENT"))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        String token = TokenUtils.getToken(auth);
        String username = authService.getUsernameFromToken(token);
        return campaignService.get(username);
    }

    @PutMapping(value = "{id}")
    public void update(@PathVariable("id") long id, @RequestBody DetailsDTO dto, @RequestHeader("Authorization") String auth) {
        if(!authService.verify(auth, "AGENT"))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        String token = TokenUtils.getToken(auth);
        String username = authService.getUsernameFromToken(token);
        try {
            campaignService.update(id, dto, username);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    
    @PostMapping(value = "createInflCamp")
    public void createInfluencerCampaigns(@RequestBody InfluencerCampaignDTO dto,  @RequestHeader("Authorization") String auth) {
    	if(!authService.verify(auth, "AGENT"))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        campaignService.createInfluencerCampaign(dto);    
    }
    
    @GetMapping(value = "getForInfl")
    public ResponseEntity<ArrayList<CampaignDTO>> getCampaignsForInfluencer(@RequestHeader("Authorization") String auth){
    	if(!authService.verify(auth, "USER"))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    	String token = TokenUtils.getToken(auth);
        String username = authService.getUsernameFromToken(token);
        ArrayList< CampaignDTO> dtos = campaignService.getCampaignsForInfluencer(username);  
        return new ResponseEntity<ArrayList<CampaignDTO>>(dtos, HttpStatus.OK);
    }
    
    @PostMapping(value = "denyCampaign")
    public void denyCampaign(@RequestHeader("Authorization") String auth, @RequestBody CampaignDTO dto){
    	if(!authService.verify(auth, "USER"))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    	String token = TokenUtils.getToken(auth);
        String username = authService.getUsernameFromToken(token);
        campaignService.denyCampaign(username, dto);  
        
    }
    
    @PostMapping(value = "acceptCampaign")
    public void acceptCampaign(@RequestHeader("Authorization") String auth, @RequestBody CampaignDTO dto){
    	if(!authService.verify(auth, "USER"))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    	String token = TokenUtils.getToken(auth);
        String username = authService.getUsernameFromToken(token);
        campaignService.acceptCampaign(username, dto);  
        
    }
}
