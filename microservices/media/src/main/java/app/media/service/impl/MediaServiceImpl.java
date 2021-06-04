package app.media.service.impl;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.media.dtos.AlbumDTO;
import app.media.dtos.CommentDTO;
import app.media.dtos.PostDTO;
import app.media.exception.PostDoesNotExistException;
import app.media.model.Comment;
import app.media.model.Media;
import app.media.model.Post;
import app.media.model.Story;
import app.media.repository.CommentRepository;
import app.media.repository.MediaRepository;
import app.media.repository.PostRepository;
import app.media.repository.StoryRepository;
import app.media.service.MediaService;

@Service
public class MediaServiceImpl implements MediaService{
	
	private MediaRepository mediaRepository;
	private PostRepository postRepository;
	private StoryRepository storyRepository;
	private CommentRepository commentRepository;
	
	@Autowired
    public MediaServiceImpl(MediaRepository mediaRepository, PostRepository postRepository, StoryRepository storyRepository, CommentRepository commentRepository) {
        this.mediaRepository = mediaRepository;
        this.postRepository = postRepository;
        this.storyRepository = storyRepository;
        this.commentRepository = commentRepository;
    }

	@Override
	public void createPost(String filepath, PostDTO postDTO) {
		Media media = new Media();
		media.setUsername("username"); //PROFILE MICROSERVICE
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

	@Override
	public void createStory(String filepath, boolean closeFriends) {
		Media media = new Media();
		media.setUsername("username"); //PROFILE MICROSERVICE
		Set<String> paths = new HashSet<String>();
		paths.add(filepath);
		media.setPath(paths);
		mediaRepository.save(media);
		
		Story story = new Story();
		story.setDateCreated(LocalDateTime.now());
		story.setCloseFriends(closeFriends);
		story.setExpiresInHours(24);
		story.setHighlighted(false);
		story.setMedia(media);
		storyRepository.save(story);
	}

	@Override
	public void createAlbumAsPost(Set<String> fileNames, AlbumDTO albumDTO) {
		Media media = new Media();
		media.setUsername("username"); //PROFILE MICROSERVICE
		media.setPath(fileNames);
		mediaRepository.save(media);
		
		Post post = new Post();
		post.setDescription(albumDTO.getDescription());
		post.setLocation(albumDTO.getLocation());
		Set<String> tags = new HashSet<>(albumDTO.getTags());
		post.setTags(tags);
		post.setMedia(media);
		postRepository.save(post);

		
	}

	@Override
	public void createAlbumAsStory(Set<String> fileNames, AlbumDTO albumDTO) {
		Media media = new Media();
		media.setUsername("username"); //PROFILE MICROSERVICE
		media.setPath(fileNames);
		mediaRepository.save(media);
		
		Story story = new Story();
		story.setDateCreated(LocalDateTime.now());
		story.setCloseFriends(albumDTO.isCloseFriends());
		story.setExpiresInHours(24);
		story.setHighlighted(false);
		story.setMedia(media);
		storyRepository.save(story);
		
	}

	@Override
	public void postComment(CommentDTO dto) throws PostDoesNotExistException {
		Comment comment = new Comment();
		comment.setUsername("username"); //
		comment.setDateCreated(LocalDateTime.now());
		comment.setContent(dto.getContent());
		commentRepository.save(comment);
		
		Optional<Post> post = postRepository.findById(dto.getId());
		if(post.isEmpty())
			throw new PostDoesNotExistException("You are trying to get post that does not exist!");
		Post oldPost= post.get();
		oldPost.getComments().add(comment);
		postRepository.save(oldPost);
		
	}


}
