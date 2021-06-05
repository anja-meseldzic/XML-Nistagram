package app.profile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import app.profile.service.ProfileService;
import app.profile.util.TokenUtils;

@RestController
@RequestMapping(value = "profile")
public class ProfileContoller {

	private ProfileService profileService;
	
	@Autowired
	public ProfileContoller(ProfileService profileService) {
		this.profileService = profileService;
	}
	
	@GetMapping(value = "follow/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> followProfile(@RequestHeader("Authorization") String auth ,@PathVariable String username){
		if(!TokenUtils.verify(auth, "USER")) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		String token = TokenUtils.getToken(auth);
		System.out.println(TokenUtils.getRoleFromToken(token));
		System.out.println(TokenUtils.getUsernameFromToken(token));
		String loggedInUsername = TokenUtils.getUsernameFromToken(token);
		int followerCount = profileService.followProfile(username, loggedInUsername); 
		return new ResponseEntity<>(followerCount, HttpStatus.OK);
	}
	
	@GetMapping(value = "unfollow/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> unfollowProfile(@RequestHeader("Authorization") String auth,@PathVariable String username){
		if(!TokenUtils.verify(auth, "USER")) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		String loggedInUsername = TokenUtils.getUsernameFromToken(TokenUtils.getToken(auth));
		int followerCount = profileService.unfollowProfile(username, loggedInUsername); 
		return new ResponseEntity<>(followerCount, HttpStatus.OK);
	}

	@PostMapping("/{username}")
	public ResponseEntity<Void> createFromUser(@PathVariable String username) {
		profileService.createFromUser(username);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
