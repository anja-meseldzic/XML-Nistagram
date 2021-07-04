package app.profile.service.impl;

import app.profile.model.Follow;
import app.profile.model.dto.FollowerDto;
import app.profile.repository.FollowRepository;
import app.profile.service.FollowService;
import app.profile.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FollowServiceImpl implements FollowService {

    private FollowRepository followRepository;
    private final ProfileService profileService;

    @Autowired
    public FollowServiceImpl(FollowRepository followRepository, ProfileService profileService) {
        this.followRepository = followRepository;
        this.profileService = profileService;
    }

    @Override
    public String getFollowedBy(long followId) {
        Optional<Follow> follow = followRepository.findById(followId);
        if(follow.isPresent())
            return follow.get().getFollowedBy().getRegularUserUsername();
        else
            return null;
    }

    @Override
    public boolean doesFollow(String follower, String followee) {
        return profileService.getFollowers(followee).stream().map(FollowerDto::getUsername).anyMatch(u -> u.equals(follower));
    }
}
