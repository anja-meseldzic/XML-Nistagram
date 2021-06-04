package app.media.service;


import app.media.dtos.PostDTO;
import app.media.model.Media;

public interface MediaService {

	void createPost(String filepath, PostDTO postDTO);

	Media save(Media media);

	void createStory(String filepath, boolean closeFriends);

}
