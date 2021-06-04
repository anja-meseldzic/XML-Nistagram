package app.media.service.impl;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.media.dtos.PostDTO;
import app.media.model.Media;
import app.media.model.Post;
import app.media.repository.MediaRepository;
import app.media.repository.PostRepository;
import app.media.service.MediaService;

@Service
public class MediaServiceImpl implements MediaService{
	
	private MediaRepository mediaRepository;
	private PostRepository postRepository;
	
	@Autowired
    public MediaServiceImpl(MediaRepository mediaRepository, PostRepository postRepository) {
        this.mediaRepository = mediaRepository;
        this.postRepository = postRepository;
    }

	@Override
	public void createPost(String filepath, PostDTO postDTO) {
		Media media = new Media();
		media.setProfileId(1); //PROFILE MICROSERVICE
		Set<String> paths = new HashSet<String>();
		paths.add(filepath);
		media.setPath(paths);
		mediaRepository.save(media);
		
		Post post = new Post();
		post.setDescription(postDTO.getDescription());
		post.setLocation(postDTO.getLocation());
		Set<String> tags = new HashSet<>(postDTO.getTags());
		post.setTags(tags);
		post.setMedia(media);
		postRepository.save(post);

	}
	
	@Override
	public Media save(Media media) {
		Media newMedia = mediaRepository.save(media);
		return newMedia;
	}


}
