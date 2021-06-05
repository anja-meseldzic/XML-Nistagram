package app.media.service.impl;


import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.*;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
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
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class MediaServiceImpl implements MediaService{
	
	private MediaRepository mediaRepository;
	private PostRepository postRepository;
	private StoryRepository storyRepository;
	private CommentRepository commentRepository;

	public final String storageDirectoryPath = "..\\storage\\media-content";

	@Autowired
    public MediaServiceImpl(MediaRepository mediaRepository, PostRepository postRepository, StoryRepository storyRepository, CommentRepository commentRepository) {
        this.mediaRepository = mediaRepository;
        this.postRepository = postRepository;
        this.storyRepository = storyRepository;
        this.commentRepository = commentRepository;
    }

	@Override
	public void createPost(MultipartFile file, PostDTO postDTO) throws IOException {
		String fileName = saveFile(file, storageDirectoryPath);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("media/content/")
				.path(fileName)
				.toUriString();
		System.out.println(fileDownloadUri);

		Media media = new Media();
		media.setUsername("username"); //PROFILE MICROSERVICE
		Set<String> paths = new HashSet<String>();
		paths.add(fileDownloadUri);
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
	public void createStory(MultipartFile file, boolean closeFriends) throws IOException {
		String fileName = saveFile(file, storageDirectoryPath);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("media/content/")
				.path(fileName)
				.toUriString();
		System.out.println(fileDownloadUri);

		Media media = new Media();
		media.setUsername("username"); //PROFILE MICROSERVICE
		Set<String> paths = new HashSet<String>();
		paths.add(fileDownloadUri);
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
	public void createAlbumAsPost(List<MultipartFile> files, AlbumDTO albumDTO) throws IOException {
		Set<String> fileNames = new HashSet<String>();
		for(MultipartFile file : files) {
			String fileName = saveFile(file, storageDirectoryPath);
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("media/content/")
					.path(fileName)
					.toUriString();
			System.out.println(fileDownloadUri);
			fileNames.add(fileDownloadUri);
		}

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
	public void createAlbumAsStory(List<MultipartFile> files, AlbumDTO albumDTO) throws  IOException {
		Set<String> fileNames = new HashSet<String>();
		for(MultipartFile file : files) {
			String fileName = saveFile(file, storageDirectoryPath);
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("media/content/")
					.path(fileName)
					.toUriString();
			System.out.println(fileDownloadUri);
			fileNames.add(fileDownloadUri);
		}

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
		if(!post.isPresent())
			throw new PostDoesNotExistException("You are trying to get post that does not exist!");
		Post oldPost= post.get();
		oldPost.getComments().add(comment);
		postRepository.save(oldPost);
		
	}

	@Override
	public UrlResource getContent(String contentName) throws MalformedURLException {
		return new UrlResource("file:" + storageDirectoryPath + "\\" + contentName);
	}

	private String saveFile(MultipartFile file, String storageDirectoryPath) throws IOException {
		String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
		String extension = getFileExtension(originalFileName);
		String fileName = UUID.randomUUID().toString() + "." + extension;

		System.out.println(fileName);

		Path storageDirectory = Paths.get(storageDirectoryPath);
		if(!Files.exists(storageDirectory)){
			Files.createDirectories(storageDirectory);
		}
		Path destination = Paths.get(storageDirectory.toString() + "\\" + fileName);
		Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
		return fileName;
	}

	private String getFileExtension(String fileName) throws IOException {
		String[] parts = fileName.split("\\.");
		if(parts.length > 0)
			return parts[parts.length - 1];
		else
			throw new IOException();
	}
}
