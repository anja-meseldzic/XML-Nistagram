package app.profile.service.impl;

import java.util.ArrayList;
import java.util.List;
import app.profile.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.profile.model.Follow;
import app.profile.model.Profile;
import app.profile.repository.FollowRepository;
import app.profile.repository.ProfileRepository;
import app.profile.service.ProfileService;

@Service
public class ProfileServiceImpl implements ProfileService {

	private ProfileRepository profileRepository;
	private FollowRepository followRepository;

	@Autowired
	public ProfileServiceImpl(ProfileRepository profileRepository, FollowRepository followRepository) {
		this.profileRepository = profileRepository;
		this.followRepository = followRepository;
	}

	
	@Override
	public int followProfile(String username, String loggedInUsername) {
		Profile profile = profileRepository.findByRegularUserUsername(username);
		Profile followedBy = profileRepository.findByRegularUserUsername(loggedInUsername);

		Follow follow = new Follow();
		follow.setFollowedBy(followedBy);
		follow.setProfile(profile);
		follow.setBlocked(false);
		follow.setCloseFriend(false);
		follow.setMuted(false);

		followRepository.save(follow);
		
		return getFollowers(username).size();
	}

	@Override
	public int unfollowProfile(String username, String loggedInUsername) {
		Follow delete = new Follow();
		for (Follow f : followRepository.findAll()) {
			if (f.getProfile().getRegularUserUsername().equals(username)
					&& f.getFollowedBy().getRegularUserUsername().equals(loggedInUsername)) {
				delete = f;
				break;
			}
		}
		followRepository.delete(delete);
		
		return getFollowers(username).size();
	}
	
	public List<String> getFollowers(String username){
		List<String> followers = new ArrayList<String>();
		for (Follow f : followRepository.findAll()) {
			if (f.getProfile().getRegularUserUsername().equals(username)) {
				followers.add(f.getFollowedBy().getRegularUserUsername());
			}
		}
		return followers;
	}
	
	public List<String> getFollowing(String username){
		List<String> following = new ArrayList<String>();
		for (Follow f : followRepository.findAll()) {
			if (f.getFollowedBy().getRegularUserUsername().equals(username)) {
				following.add(f.getFollowedBy().getRegularUserUsername());
			}
		}
		return following;
	}

	@Override
	public void createFromUser(String username) {
		Profile profile = new Profile(username);
		profileRepository.save(profile);
	}
}
