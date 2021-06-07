package app.media.service;

import app.media.dtos.StoryInfoDTO;
import app.media.exception.ProfileBlockedException;
import app.media.exception.ProfilePrivateException;

import java.util.List;

public interface StoryService {

    List<StoryInfoDTO> getFeed(String username);

    List<StoryInfoDTO> getForProfile(String requestedBy, String profile) throws ProfilePrivateException, ProfileBlockedException;
    
    List<StoryInfoDTO> getAllUserStories(String username);
    
    List<StoryInfoDTO> getStoryHighlights(String username);
    
    void addToStoryHighlights(StoryInfoDTO dto);
}
