package app.media.service;

import java.util.Set;

import app.media.dtos.AlbumDTO;
import app.media.dtos.PostDTO;
import app.media.model.Media;

public interface MediaService {

	void createPost(String filepath, PostDTO postDTO);

	Media save(Media media);

	void createStory(String filepath, boolean closeFriends);

	void createAlbumAsPost(Set<String> fileNames, AlbumDTO albumDTO);

	void createAlbumAsStory(Set<String> fileNames, AlbumDTO albumDTO);

}
