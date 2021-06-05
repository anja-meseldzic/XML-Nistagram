package app.profile.controller;

import java.util.List;
import java.util.Set;

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

import app.profile.model.FollowRequest;
import app.profile.model.Profile;
import app.profile.model.dto.FollowRequestDto;
import app.profile.model.dto.FollowerDto;
import app.profile.service.ProfileService;
import app.profile.service.impl.ProfileServiceImpl;
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
	
	@GetMapping(value = "followRequest/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<FollowRequestDto>> getFollowRequests(@RequestHeader("Authorization") String auth,@PathVariable String username){
		if(!TokenUtils.verify(auth, "USER")) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		Set<FollowRequestDto> requests = profileService.getFollowRequests(username);
		return new ResponseEntity<>(requests, HttpStatus.OK);
	}
	
	@GetMapping(value = "acceptRequest/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<FollowRequestDto>> acceptRequest(@RequestHeader("Authorization") String auth,@PathVariable String username){
		if(!TokenUtils.verify(auth, "USER")) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		String loggedInUsername = TokenUtils.getUsernameFromToken(TokenUtils.getToken(auth));
		Set<FollowRequestDto> requests = profileService.acceptRequest(username, loggedInUsername);
		return new ResponseEntity<>(requests, HttpStatus.OK);
	}
	
	@GetMapping(value = "deleteRequest/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<FollowRequestDto>> deleteRequest(@RequestHeader("Authorization") String auth,@PathVariable String username){
		if(!TokenUtils.verify(auth, "USER")) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		String loggedInUsername = TokenUtils.getUsernameFromToken(TokenUtils.getToken(auth));
		Set<FollowRequestDto> requests = profileService.deleteRequest(username, loggedInUsername);
		return new ResponseEntity<>(requests, HttpStatus.OK);
	}
	
	@GetMapping(value = "following/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FollowerDto>> getFollowing(@PathVariable String username){
		/*if(!TokenUtils.verify(auth, "USER")) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}*/
		
		List<FollowerDto> following = profileService.getFollowing(username);
		return new ResponseEntity<>(following, HttpStatus.OK);
	}
	
	@GetMapping(value = "followers/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FollowerDto>> getFollowers(@PathVariable String username){
		/*if(!TokenUtils.verify(auth, "USER")) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}*/

		List<FollowerDto> followers = profileService.getFollowers(username);
		return new ResponseEntity<>(followers, HttpStatus.OK);
	}
}
