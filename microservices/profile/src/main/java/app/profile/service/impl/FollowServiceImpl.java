package app.profile.service.impl;

import app.profile.model.Follow;
import app.profile.repository.FollowRepository;
import app.profile.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FollowServiceImpl implements FollowService {

    private FollowRepository followRepository;

    @Autowired
    public FollowServiceImpl(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    @Override
    public String getFollowedBy(long followId) {
        Optional<Follow> follow = followRepository.findById(followId);
        if(follow.isPresent())
            return follow.get().getFollowedBy().getRegularUserUsername();
        else
            return null;
    }
}
