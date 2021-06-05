package app.profile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import app.profile.model.Profile;
import app.profile.service.ProfileService;
import app.profile.service.impl.ProfileServiceImpl;

@RestController
@RequestMapping(value = "profile")
public class ProfileContoller {

	private ProfileService profileService;
	
	@Autowired
	public ProfileContoller(ProfileService profileService) {
		this.profileService = profileService;
	}
	
	@GetMapping(value = "follow/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> followProfile(@PathVariable long id){
		
		int followerCount = profileService.followProfile(id, 1); // treba dodati id ulogovanog korisnika
		return new ResponseEntity<>(followerCount, HttpStatus.OK);
	}
	
	@GetMapping(value = "unfollow/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> unfollowProfile(@PathVariable long id){
		
		int followerCount = profileService.unfollowProfile(id, 1); // treba dodati id ulogovanog korisnika
		return new ResponseEntity<>(followerCount, HttpStatus.OK);
	}

	@PostMapping("/{username}")
	public ResponseEntity<Void> createFromUser(@PathVariable String username) {
		profileService.createFromUser(username);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
