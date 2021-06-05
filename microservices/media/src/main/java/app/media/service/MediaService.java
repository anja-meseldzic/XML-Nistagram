package app.media.service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import app.media.dtos.AlbumDTO;
import app.media.dtos.CommentDTO;
import app.media.dtos.PostDTO;
import app.media.exception.PostDoesNotExistException;
import app.media.model.Media;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {

	void createPost(MultipartFile file, PostDTO postDTO) throws IOException;

	Media save(Media media);

	void createStory(MultipartFile file, boolean closeFriends) throws  IOException;

	void createAlbumAsPost(List<MultipartFile> files, AlbumDTO albumDTO) throws  IOException;

	void createAlbumAsStory(List<MultipartFile> files, AlbumDTO albumDTO) throws  IOException;

	void postComment(CommentDTO dto) throws PostDoesNotExistException;

	byte[] getContent(String contentName) throws IOException;
}
