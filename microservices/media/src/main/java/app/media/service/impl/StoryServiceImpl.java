package app.media.service.impl;

import app.media.dtos.StoryInfoDTO;
import app.media.exception.ProfileBlockedException;
import app.media.exception.ProfilePrivateException;
import app.media.model.Story;
import app.media.repository.StoryRepository;
import app.media.service.ProfileService;
import app.media.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoryServiceImpl implements StoryService {

    private StoryRepository storyRepository;
    private ProfileService profileService;

    @Autowired
    public StoryServiceImpl(StoryRepository storyRepository, ProfileService profileService) {
        this.storyRepository = storyRepository;
        this.profileService = profileService;
    }


    @Override
    public List<StoryInfoDTO> getFeed(String username) {
        List<StoryInfoDTO> result = new ArrayList<>();

        List<String> targetedProfiles = profileService.getFollowing(username);
        List<String> mutedProfiles = profileService.getMuted(username);
        List<String> blockedProfiles = profileService.getBlocked(username);

        targetedProfiles.removeAll(mutedProfiles);
        targetedProfiles.removeAll(blockedProfiles);

        List<Story> targetedStories = storyRepository.findAll().stream()
                .filter(s -> targetedProfiles.contains(s.getMedia().getUsername()))
                .collect(Collectors.toList());

        for(Story story : targetedStories) {
            if(story.isActive()
                    && (!story.isCloseFriends()
                        || profileService.getCloseFriends(story.getMedia().getUsername())
                            .contains(username)))
                story.getMedia().getPath().forEach(url ->
                        result.add(toStoryInfoDTO(story, url))
                );
        }
        storyRepository.findAll().stream()
                .filter(s -> s.getMedia().getUsername().equals(username) && s.isActive())
                .forEach(s -> s.getMedia().getPath().forEach(url -> result.add(toStoryInfoDTO(s, url))));

        result.sort((r1, r2) -> r1.getCreated().isBefore(r2.getCreated()) ? 1 : -1);
        return result;
    }

    @Override
    public List<StoryInfoDTO> getForProfile(String requestedBy, String profile) throws ProfilePrivateException, ProfileBlockedException {
        List<StoryInfoDTO> result = new ArrayList<>();

        List<String> followers = profileService.getFollowing(profile);
        boolean follower = followers.contains(requestedBy);
        if(!profileService.isPublic(profile) && !follower && !profile.equals(requestedBy))
            throw new ProfilePrivateException();

        List<String> blockedProfiles = profileService.getBlocked(requestedBy);
        boolean blocked = blockedProfiles.contains(profile);
        if(blocked)
            throw new ProfileBlockedException();

        List<Story> stories = storyRepository.findAll().stream()
                .filter(p -> p.getMedia().getUsername().equals(profile)
                        && p.isActive()
                        && (!p.isCloseFriends() || profile.equals(requestedBy) || profileService.getCloseFriends(profile).contains(requestedBy))
                )
                .collect(Collectors.toList());
        stories.forEach(s -> s.getMedia().getPath().forEach(url -> result.add(toStoryInfoDTO(s, url))));

        result.sort((r1, r2) -> r1.getCreated().isBefore(r2.getCreated()) ? 1 : -1);
        return result;
    }

    private StoryInfoDTO toStoryInfoDTO(Story story, String url) {
        StoryInfoDTO result = new StoryInfoDTO();
        result.setCreated(story.getMedia().getCreated());
        result.setUsername(story.getMedia().getUsername());
        result.setUrl(url);
        return result;
    }
}
