package app.media.service;

import app.media.dtos.*;
import app.media.exception.PostDoesNotExistException;
import app.media.exception.ProfileBlockedException;
import app.media.exception.ProfilePrivateException;
import app.media.model.Favourites;

import java.util.List;

public interface PostService {

    List<PostInfoDTO> getFeed(String username);

    List<PostInfoDTO> getForProfile(String requestedBy, String profile) throws ProfilePrivateException, ProfileBlockedException;

    List<SearchResultDTO> search(String requestedBy, String criterion);

    List<PostInfoDTO> getAllWithLocation(String requestedBy, String location);

    List<PostInfoDTO> getAllWithHashtag(String requestedBy, String hashtag);

    PostInfoDTO get(String requestedBy, long postId) throws PostDoesNotExistException;
    
    Favourites saveToFavourites(long postId ,String username);
    
    List<PostInfoDTO> getFavouritesForProfile(String profile);

	void addFavouritesToCollection(String loggedInUsername, CollectionDTO dto);

	List<CollectionInfoDTO> getCollectionsForProfile(String loggedInUsername);

	List<PostInfoDTO> getLikedContent(String username);

	List<PostInfoDTO> getDislikedContent(String username);

	String getLink(long id);

	void saveLinkClick(long id, String username);

	ReportDto getReport(long id);
}
