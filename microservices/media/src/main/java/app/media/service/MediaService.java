package app.media.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;
import app.media.dtos.AlbumDTO;
import app.media.dtos.AllCommentDTO;
import app.media.dtos.AllReactionsDTO;
import app.media.dtos.CommentDTO;
import app.media.dtos.PostDTO;
import app.media.dtos.RatingDTO;
import app.media.dtos.ReactionsNumberDTO;
import app.media.exception.PostDoesNotExistException;
import app.media.model.Media;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {

	void createPost(MultipartFile file, PostDTO postDTO) throws IOException;

	Media save(Media media);

	void createStory(MultipartFile file, boolean closeFriends) throws  IOException;

	void createAlbumAsPost(List<MultipartFile> files, AlbumDTO albumDTO) throws  IOException;

	void createAlbumAsStory(List<MultipartFile> files, AlbumDTO albumDTO) throws  IOException;

	void postComment(CommentDTO dto) throws PostDoesNotExistException;

	UrlResource getContent(String contentName) throws MalformedURLException;

	void reactOnPost(RatingDTO dto) throws PostDoesNotExistException;

	ReactionsNumberDTO getReactionsNumber(long id) throws PostDoesNotExistException;
	
	Set<AllCommentDTO> getAllComments(long postId) throws PostDoesNotExistException;

	AllReactionsDTO getAllReactions(long postId) throws PostDoesNotExistException;
}
