package app.media.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import app.media.dtos.AlbumDTO;
import app.media.dtos.AllCommentDTO;
import app.media.dtos.AllReactionsDTO;
import app.media.dtos.CommentDTO;
import app.media.dtos.InappropriateDTO;
import app.media.dtos.InappropriateListDTO;
import app.media.dtos.PostDTO;
import app.media.dtos.RatingDTO;
import app.media.dtos.ReactionsNumberDTO;
import app.media.exception.PostDoesNotExistException;
import app.media.exception.ProfileBlockedException;
import app.media.exception.ProfilePrivateException;
import app.media.model.Media;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {

	void createPost(MultipartFile file, PostDTO postDTO, String username) throws IOException;

	Media save(Media media);

	void createStory(MultipartFile file, boolean closeFriends, String username) throws  IOException;

	long createAlbumAsPost(List<MultipartFile> files, AlbumDTO albumDTO, String username) throws  IOException;

	long createAlbumAsStory(List<MultipartFile> files, AlbumDTO albumDTO, String username) throws  IOException;

	void postComment(CommentDTO dto, String username) throws PostDoesNotExistException;

	UrlResource getContent(String contentName) throws MalformedURLException;

	void reactOnPost(RatingDTO dto, String username) throws PostDoesNotExistException;

	ReactionsNumberDTO getReactionsNumber(long id) throws PostDoesNotExistException;
	
	List<AllCommentDTO> getAllComments(long postId) throws PostDoesNotExistException;

	AllReactionsDTO getAllReactions(long postId) throws PostDoesNotExistException;

	void checkProfile(long postId, String myUsername) throws PostDoesNotExistException, ProfilePrivateException, ProfileBlockedException;

	String reportContent(String myUsername, InappropriateDTO content);

	List<InappropriateListDTO> getInappropriateList();

	void shutProfileDown(InappropriateListDTO content);

	void approveInappropriateContent(InappropriateListDTO content);

	void deleteInappropriateContent(InappropriateListDTO content);

	void delete(long id);

	boolean exists(long id);

	String getPath(long id);
}
