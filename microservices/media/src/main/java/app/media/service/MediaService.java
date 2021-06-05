package app.media.service;

import java.util.Set;

import app.media.dtos.AlbumDTO;
import app.media.dtos.AllCommentDTO;
import app.media.dtos.CommentDTO;
import app.media.dtos.PostDTO;
import app.media.exception.PostDoesNotExistException;
import app.media.model.Media;

public interface MediaService {

	void createPost(String filepath, PostDTO postDTO);

	Media save(Media media);

	void createStory(String filepath, boolean closeFriends);

	void createAlbumAsPost(Set<String> fileNames, AlbumDTO albumDTO);

	void createAlbumAsStory(Set<String> fileNames, AlbumDTO albumDTO);

	void postComment(CommentDTO dto) throws PostDoesNotExistException;

	Set<AllCommentDTO> getAllComments(long postId) throws PostDoesNotExistException;

}
