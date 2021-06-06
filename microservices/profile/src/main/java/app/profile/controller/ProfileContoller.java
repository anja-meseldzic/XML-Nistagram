package app.profile.controller;

import app.profile.dtos.ProfileInfoDTO;
import app.profile.exception.ProfileNotFoundException;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.profile.model.dto.FollowRequestDto;
import app.profile.model.dto.FollowerDto;

import app.profile.service.ProfileService;
import app.profile.util.TokenUtils;
import org.springframework.web.server.ResponseStatusException;

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
		if(!TokenUtils.verify(auth, "USER", "AGENT")) {
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
		if(!TokenUtils.verify(auth, "USER", "AGENT"))  {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		String loggedInUsername = TokenUtils.getUsernameFromToken(TokenUtils.getToken(auth));
		int followerCount = profileService.unfollowProfile(username, loggedInUsername); 
		return new ResponseEntity<>(followerCount, HttpStatus.OK);
	}

	
	@GetMapping(value = "followRequest/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<FollowRequestDto>> getFollowRequests(@RequestHeader("Authorization") String auth,@PathVariable String username){
		if(!TokenUtils.verify(auth, "USER", "AGENT")) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		Set<FollowRequestDto> requests = profileService.getFollowRequests(username);
		return new ResponseEntity<>(requests, HttpStatus.OK);
	}
	
	@GetMapping(value = "acceptRequest/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<FollowRequestDto>> acceptRequest(@RequestHeader("Authorization") String auth,@PathVariable String username){
		if(!TokenUtils.verify(auth, "USER", "AGENT")) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		String loggedInUsername = TokenUtils.getUsernameFromToken(TokenUtils.getToken(auth));
		Set<FollowRequestDto> requests = profileService.acceptRequest(username, loggedInUsername);
		return new ResponseEntity<>(requests, HttpStatus.OK);
	}
	
	@GetMapping(value = "deleteRequest/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<FollowRequestDto>> deleteRequest(@RequestHeader("Authorization") String auth,@PathVariable String username){
		if(!TokenUtils.verify(auth, "USER", "AGENT")) {
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

	@PostMapping("/{username}")
	public ResponseEntity<Void> createFromUser(@PathVariable String username) {
		profileService.createFromUser(username);
		return new ResponseEntity<>(HttpStatus.CREATED);

	}

	@GetMapping(value = "{username}")
	public ResponseEntity<ProfileInfoDTO> getInfo(@PathVariable String username, @RequestHeader("Authorization") String auth) {
		try {
			String requestedBy = null;
			if (!auth.equals("Bearer null"))
				requestedBy = TokenUtils.getUsernameFromToken(auth.substring(7));
			return new ResponseEntity<>(profileService.getProfile(requestedBy, username), HttpStatus.OK);
		} catch (ProfileNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	//don't put in api gateway
	@GetMapping(value = "ms")
	public List<String> getAll() { return profileService.getAll(); }

	//don't put in api gateway
	@GetMapping(value = "ms/followers/{profile}")
	public List<String> getFollowerss(@PathVariable String profile) {
		return profileService.getFollowerss(profile);
	}

	//don't put in api gateway
	@GetMapping(value = "ms/following/{profile}")
	public List<String> getFollowingg(@PathVariable String profile) {
		return profileService.getFollowingg(profile);
	}

	//don't put in api gateway
	@GetMapping(value = "ms/muted/{profile}")
	public List<String> getMuted(@PathVariable String profile) {
		return profileService.getMuted(profile);
	}

	//don't put in api gateway
	@GetMapping(value = "ms/blocked/{profile}")
	public List<String> getBlocked(@PathVariable String profile) {
		return profileService.getBlocked(profile);
	}

	//don't put in api gateway
	@GetMapping(value = "ms/close/{profile}")
	public List<String> getCloseFriends(@PathVariable String profile) {
		return profileService.getCloseFriends(profile);
	}

	//don't put in api gateway
	@GetMapping(value = "ms/public/{profile}")
	public boolean isPublic(@PathVariable String profile) {
		try {
			return profileService.isPublic(profile);
		} catch (ProfileNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "addCloseFriend")
	public ResponseEntity<String> addCloseFriend(@RequestBody String usernameOfFriend,  @RequestHeader("Authorization") String auth)
	{
		if(!TokenUtils.verify(auth, "USER","AGENT"))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		String token = TokenUtils.getToken(auth);
		String myUsername = TokenUtils.getUsernameFromToken(token);
		
		profileService.addCloseFriend(myUsername, usernameOfFriend);
		
		return new ResponseEntity<>("", HttpStatus.OK);
	}
}
