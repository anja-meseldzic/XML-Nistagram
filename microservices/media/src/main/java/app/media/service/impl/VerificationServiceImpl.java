package app.media.service.impl;

import app.media.model.Post;
import app.media.model.Story;
import app.media.repository.PostRepository;
import app.media.repository.StoryRepository;
import app.media.service.ProfileService;
import app.media.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationServiceImpl implements VerificationService {
    private final PostRepository postRepository;
    private final StoryRepository storyRepository;
    private final ProfileService profileService;

    @Autowired
    public VerificationServiceImpl(PostRepository postRepository, StoryRepository storyRepository, ProfileService profileService) {
        this.postRepository = postRepository;
        this.storyRepository = storyRepository;
        this.profileService = profileService;
    }

    @Override
    public boolean verifyPostLink(Long postId, String username) {
        Post post = postRepository.getOne(postId);
        return profileService.getFollowing(username).stream().anyMatch(u -> u.equals(post.getMedia().getUsername())) ||
                username.equals(post.getMedia().getUsername());
    }

    @Override
    public boolean verifyStoryLink(String author, String username) {
        return profileService.getFollowing(username).stream().anyMatch(u -> u.equals(author)) ||
                username.equals(author);
    }
}
