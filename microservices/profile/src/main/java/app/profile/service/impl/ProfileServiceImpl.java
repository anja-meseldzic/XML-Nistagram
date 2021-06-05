package app.profile.service.impl;

import app.profile.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.profile.repository.ProfileRepository;
import app.profile.service.ProfileService;

@Service
public class ProfileServiceImpl implements ProfileService {

	private ProfileRepository profileRepository;

	@Autowired
	public ProfileServiceImpl(ProfileRepository profileRepository) {
		this.profileRepository = profileRepository;
	}

	@Override
	public int followProfile(long id, long loggedId) {
		// TODO Auto-generated method stub
		/*Profile profileToFollow = profileRepository.findByRegularUserId(id); // profil korisnika kog hocu da zapratim
		Followers followerToFollow = followersRepo.findByProfileId(id);

		Profile loggedInProfile = profileRepository.findByRegularUserId(loggedId);// profil ulogovanog korisnika
		Followers loggedInFollower = followersRepo.findByProfileId(loggedId); // dobavi mi ljude koje prati ulogovani profil
		
		Set<Profile> following = loggedInFollower.getFollowing(); // ljudi koji prate profil ulogovanog korisnika
		Set<Profile> followers = followerToFollow.getFollowersOfProfile();// ljudi koji prate drugog korisnika
		
		//ukoliko je profil javan dodaj u listu pratilaca i listu pratecih
		if (!profileToFollow.isPrivateProfile()) {
			
			following.add(profileToFollow); // dodajem da ulogovani prati korisnika 2			
			followers.add(loggedInProfile); // dodajem da korisnika 2 prati ulogovani
		}
		
		followersRepo.save(followerToFollow);
		followersRepo.save(loggedInFollower);
		return followers.size();*/
		return 1;
	}

	@Override
	public int unfollowProfile(long id, long loggedId) {
		/*Profile profileToFollow = profileRepository.findByRegularUserId(id); // profil korisnika kog hocu da zapratim
		Followers followerToFollow = followersRepo.findByProfileId(id);

		Profile loggedInProfile = profileRepository.findByRegularUserId(loggedId);// profil ulogovanog korisnika
		Followers loggedInFollower = followersRepo.findByProfileId(loggedId); // dobavi mi ljude koje prati ulogovani profil
		
		Set<Profile> following = loggedInFollower.getFollowing(); // ljudi koji prate profil ulogovanog korisnika
		Set<Profile> followers = followerToFollow.getFollowersOfProfile();// ljudi koji prate drugog korisnika
			
		following.remove(profileToFollow); // dodajem da ulogovani prati korisnika 2			
		followers.remove(loggedInProfile); // dodajem da korisnika 2 prati ulogovani
		
		
		followersRepo.save(followerToFollow);
		followersRepo.save(loggedInFollower);
		return followers.size();*/
		return 1;
	}

	@Override
	public void createFromUser(String username) {
		Profile profile = new Profile(username);
		profileRepository.save(profile);
	}
}
