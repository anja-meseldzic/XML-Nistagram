package app.media.service;

import app.media.dtos.PostInfoDTO;
import app.media.dtos.SearchResultDTO;
import app.media.exception.PostDoesNotExistException;
import app.media.exception.ProfileBlockedException;
import app.media.exception.ProfilePrivateException;

import java.util.List;

public interface PostService {

    List<PostInfoDTO> getFeed(String username);

    List<PostInfoDTO> getForProfile(String requestedBy, String profile) throws ProfilePrivateException, ProfileBlockedException;

    List<SearchResultDTO> search(String requestedBy, String criterion);

    List<PostInfoDTO> getAllWithLocation(String requestedBy, String location);

    List<PostInfoDTO> getAllWithHashtag(String requestedBy, String hashtag);

    PostInfoDTO get(String requestedBy, long postId) throws PostDoesNotExistException;
}
