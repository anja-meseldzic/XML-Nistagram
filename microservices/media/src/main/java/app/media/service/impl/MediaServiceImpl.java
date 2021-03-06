package app.media.service.impl;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import app.media.dtos.*;
import app.media.service.NotificationService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import app.media.exception.PostDoesNotExistException;
import app.media.exception.ProfileBlockedException;
import app.media.exception.ProfilePrivateException;
import app.media.model.Comment;
import app.media.model.Favourites;
import app.media.model.InapropriateContent;
import app.media.model.Media;
import app.media.model.Post;
import app.media.model.Rating;
import app.media.model.RatingType;
import app.media.model.Story;
import app.media.repository.CollectionRepository;
import app.media.repository.CommentRepository;
import app.media.repository.FavouritesRepository;
import app.media.repository.InapropriateContentRepository;
import app.media.repository.MediaRepository;
import app.media.repository.PostRepository;
import app.media.repository.RatingRepository;
import app.media.repository.StoryRepository;
import app.media.service.MediaService;
import app.media.service.ProfileService;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MediaServiceImpl implements MediaService{
	
	private MediaRepository mediaRepository;
	private PostRepository postRepository;
	private StoryRepository storyRepository;
	private CommentRepository commentRepository;
	private RatingRepository ratingRepository;
	private ProfileService profileService;
	private InapropriateContentRepository inapropriateContentRepository;
	private NotificationService notificationService;
	private CollectionRepository collectionRepository;
	private FavouritesRepository favouritesRepository;

	@Value("${media.storage}")
	private String storageDirectoryPath;

	@Autowired
    public MediaServiceImpl(MediaRepository mediaRepository, PostRepository postRepository, StoryRepository storyRepository,
    		CommentRepository commentRepository, RatingRepository ratingRepository, ProfileService profileService, 
    		InapropriateContentRepository inapropriateContentRepository, NotificationService notificationService,
    		CollectionRepository collectionRepository, FavouritesRepository favouritesRepository) {
        this.mediaRepository = mediaRepository;
        this.postRepository = postRepository;
        this.storyRepository = storyRepository;
        this.commentRepository = commentRepository;
        this.ratingRepository = ratingRepository;
        this.profileService = profileService;
        this.inapropriateContentRepository = inapropriateContentRepository;
        this.notificationService = notificationService;
        this.collectionRepository = collectionRepository;
        this.favouritesRepository = favouritesRepository;
        
    }

	@Override
	public void createPost(MultipartFile file, PostDTO postDTO, String username) throws IOException {
		String fileName = saveFile(file, storageDirectoryPath);
		String fileDownloadUri = "media/content/" + fileName;
		System.out.println(fileDownloadUri);

		Media media = new Media();
		media.setUsername(username);
		Set<String> paths = new HashSet<String>();
		paths.add(fileDownloadUri);
		media.setPath(paths);
		media.setCreated(LocalDateTime.now());
		mediaRepository.save(media);
		
		Post post = new Post();
		post.setDescription(postDTO.getDescription());
		post.setLocation(postDTO.getLocation());
		Set<String> tags = new HashSet<>(postDTO.getTags());
		post.setTags(tags);
		post.setMedia(media);
		postRepository.save(post);

		List<String> followers = profileService.getFollowers(media.getUsername());
		for(String follower : followers) {
			sendNotification(NotificationType.POST, follower, media.getUsername(), Long.toString(post.getId()));
		}
	}
	
	@Override
	public Media save(Media media) {
		Media newMedia = mediaRepository.save(media);
		return newMedia;
	}

	@Override
	public void createStory(MultipartFile file, boolean closeFriends, String username) throws IOException {
		String fileName = saveFile(file, storageDirectoryPath);
		String fileDownloadUri = "media/content/" + fileName;

		System.out.println(fileDownloadUri);

		Media media = new Media();
		media.setUsername(username);
		Set<String> paths = new HashSet<String>();
		paths.add(fileDownloadUri);
		media.setPath(paths);
		media.setCreated(LocalDateTime.now());
		mediaRepository.save(media);
		
		Story story = new Story();
		story.setDateCreated(LocalDateTime.now());
		story.setCloseFriends(closeFriends);
		story.setHighlighted(false);
		story.setMedia(media);
		storyRepository.save(story);

		List<String> followers;
		if(story.isCloseFriends())
			followers = profileService.getCloseFriends(media.getUsername());
		else
			followers = profileService.getFollowers(media.getUsername());
		for(String follower : followers) {
			sendNotification(NotificationType.STORY, follower, media.getUsername(), media.getUsername());
		}
	}

	@Override
	public long createAlbumAsPost(List<MultipartFile> files, AlbumDTO albumDTO, String username) throws IOException {
		Set<String> fileNames = new HashSet<String>();
		for(MultipartFile file : files) {
			String fileName = saveFile(file, storageDirectoryPath);
			String fileDownloadUri = "media/content/" + fileName;
			System.out.println(fileDownloadUri);
			fileNames.add(fileDownloadUri);
		}

		Media media = new Media();
		media.setUsername(username);
		media.setPath(fileNames);
		media.setCreated(LocalDateTime.now());
		media = mediaRepository.save(media);
		
		Post post = new Post();
		post.setDescription(albumDTO.getDescription());
		post.setLocation(albumDTO.getLocation());
		Set<String> tags = new HashSet<>(albumDTO.getTags());
		post.setTags(tags);
		post.setMedia(media);
		postRepository.save(post);

		List<String> followers = profileService.getFollowers(media.getUsername());
		for(String follower : followers) {
			sendNotification(NotificationType.POST, follower, media.getUsername(), Long.toString(post.getId()));
		}

		return media.getId();
	}

	@Override
	public long createAlbumAsStory(List<MultipartFile> files, AlbumDTO albumDTO, String username) throws  IOException {
		Set<String> fileNames = new HashSet<String>();
		for(MultipartFile file : files) {
			String fileName = saveFile(file, storageDirectoryPath);
			String fileDownloadUri = "media/content/" + fileName;
			System.out.println(fileDownloadUri);
			fileNames.add(fileDownloadUri);
		}

		Media media = new Media();
		media.setUsername(username);
		media.setPath(fileNames);
		media.setCreated(LocalDateTime.now());
		media = mediaRepository.save(media);
		
		Story story = new Story();
		story.setDateCreated(LocalDateTime.now());
		story.setCloseFriends(albumDTO.isCloseFriends());
		story.setHighlighted(false);
		story.setMedia(media);
		storyRepository.save(story);

		List<String> followers;
		if(story.isCloseFriends())
			followers = profileService.getCloseFriends(media.getUsername());
		else
			followers = profileService.getFollowers(media.getUsername());
		for(String follower : followers) {
			sendNotification(NotificationType.STORY, follower, media.getUsername(), media.getUsername());
		}

		return media.getId();
	}

	@Override
	public void postComment(CommentDTO dto, String username) throws PostDoesNotExistException {
		Comment comment = new Comment();
		comment.setUsername(username); 
		comment.setDateCreated(LocalDateTime.now());
		comment.setContent(dto.getContent());
		commentRepository.save(comment);
		
		Optional<Post> post = postRepository.findById(dto.getId());
		if(!post.isPresent())
			throw new PostDoesNotExistException("You are trying to get post that does not exist!");
		Post oldPost= post.get();
		oldPost.getComments().add(comment);
		postRepository.save(oldPost);

		sendNotification(NotificationType.COMMENT, oldPost.getMedia().getUsername(), comment.getUsername(), Long.toString(oldPost.getId()));
	}

	@Override
	public UrlResource getContent(String contentName) throws MalformedURLException {
		return new UrlResource("file:" + storageDirectoryPath + File.separator + contentName);
	}

	@Override
	public List<AllCommentDTO> getAllComments(long postId) throws PostDoesNotExistException {
		Post post = postRepository.findOneById(postId);
		if(post == null) {
			throw new PostDoesNotExistException("You are trying to get post that does not exist!");
		}
		Set<Comment> comments =  post.getComments();
		List<Comment> comArray = new ArrayList<>(comments);
		sortByDateCreated(comArray);
		List<AllCommentDTO> contents = new ArrayList<AllCommentDTO>();
		for(Comment com : comArray) {
			contents.add(new AllCommentDTO(com.getUsername(), com.getContent()));
		}
		return contents;
	}
	
	private List<Comment> sortByDateCreated(List<Comment> dtos){
		Collections.sort(dtos, new Comparator<Comment>() {
		    @Override
		    public int compare(Comment c1, Comment c2) {
		        return c1.getDateCreated().compareTo(c2.getDateCreated());
		    }
		});
		return dtos;
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
		Path destination = Paths.get(storageDirectory.toString() + File.separator + fileName);
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

	@Override
	public void reactOnPost(RatingDTO dto, String username) throws PostDoesNotExistException {
		Post post = postRepository.findOneById(dto.getId());
		if(post == null) {
			throw new PostDoesNotExistException("You are trying to get post that does not exist!");
		}
		Set<Rating> ratings = post.getRatings();
		RatingType type = dto.isLike() ? RatingType.LIKE : RatingType.DISLIKE;
		for(Rating rat : ratings) {
			if(rat.getUsername().equals(username) && rat.getRatingType() == type) { 
				return;
			}
		}
		Rating rating = new Rating();
		rating.setUsername(username); 
		if(dto.isLike() == false) {
			rating.setRatingType(RatingType.DISLIKE);
		}else {
			rating.setRatingType(RatingType.LIKE);
		}
		ratingRepository.save(rating);
		post.getRatings().add(rating);
		postRepository.save(post);

		sendNotification(NotificationType.RATING, post.getMedia().getUsername(), rating.getUsername(), Long.toString(post.getId()));
	}

	@Override
	public ReactionsNumberDTO getReactionsNumber(long id) throws PostDoesNotExistException {
		Post post = postRepository.findOneById(id);
		if(post == null) {
			throw new PostDoesNotExistException("You are trying to get post that does not exist!");
		}
		ReactionsNumberDTO dto = new ReactionsNumberDTO();
		Set<Rating> likes = new HashSet<Rating>();
		Set<Rating> dislikes = new HashSet<Rating>();
		Set<Rating> ratings = post.getRatings();
		for(Rating rating : ratings) {
			if(rating.getRatingType() == RatingType.LIKE) {
				likes.add(rating);
			}else {
				dislikes.add(rating);
			}
		}
		dto.setLikes(likes.size());
		dto.setDislikes(dislikes.size());
		return dto;
	}

	@Override
	public AllReactionsDTO getAllReactions(long postId) throws PostDoesNotExistException {
		Post post = postRepository.findOneById(postId);
		if(post == null) {
			throw new PostDoesNotExistException("You are trying to get post that does not exist!");
		}
		Set<String> likes = new HashSet<String>();
		Set<String> dislikes = new HashSet<String>();
		Set<Rating> ratings = post.getRatings();
		for(Rating rat : ratings) {
			if(rat.getRatingType() == RatingType.LIKE) {
				likes.add(rat.getUsername());
			}else {
				dislikes.add(rat.getUsername());
			}
		}
		AllReactionsDTO dto = new AllReactionsDTO(likes, dislikes);
		return dto;
	}

	@Override
	public void checkProfile(long postId, String myUsername) throws PostDoesNotExistException, ProfilePrivateException, ProfileBlockedException {
		Post post = postRepository.findOneById(postId);
		if(post == null) {
			throw new PostDoesNotExistException("You are trying to get post that does not exist!");
		}
		String username = post.getMedia().getUsername();
		
		 List<String> followers = profileService.getFollowers(username);
	        boolean follower = followers.contains(myUsername);
	        if(!profileService.isPublic(username) && !follower && !username.equals(myUsername))
	            throw new ProfilePrivateException();
	     
	     List<String> blockedProfiles = profileService.getBlocked(myUsername);
	      	boolean blocked = blockedProfiles.contains(username);
	        if(blocked)
	            throw new ProfileBlockedException();
	
	        
	}

	@Override
	public String reportContent(String myUsername, InappropriateDTO content) {
		Post post = postRepository.findOneById(content.getIdOfContent());
		
		for(InapropriateContent con : inapropriateContentRepository.findAll()) {
			if(con.getUsername().equals(myUsername) && con.getMedia().getId() == post.getMedia().getId()) {
				return "You cannot report the same content twice.";
			}
		}
		
		InapropriateContent newContent = new InapropriateContent();
		newContent.setReviewed(false);
		newContent.setReason(content.getReason());
		newContent.setMedia(post.getMedia());
		newContent.setUsername(myUsername);
		
		inapropriateContentRepository.save(newContent);
		return "You successfully reported this post.";
	}

	private void sendNotification(NotificationType type, String receiver, String initiator, String resource) {
		NewNotificationDTO dto = new NewNotificationDTO(type, receiver, initiator, resource);
		notificationService.create(dto);
	}

	@Override
	public List<InappropriateListDTO> getInappropriateList() {
		List <InapropriateContent> inappropriateContents = inapropriateContentRepository.findAll().stream().filter(r -> !r.isReviewed()).collect(Collectors.toList());
		List<InappropriateListDTO> result = new ArrayList<InappropriateListDTO>();
		for(InapropriateContent ic : inappropriateContents) {
			InappropriateListDTO dto = new InappropriateListDTO();
			dto.setId(ic.getId());
			dto.setMediaId(ic.getMedia().getId());
			dto.setReason(ic.getReason());
			dto.setUsernameOfReporter(ic.getUsername());
			
			for(Post post : postRepository.findAll()) {
				if(post.getMedia().getId() == ic.getMedia().getId()) {
					dto.setPostId(post.getId());
					break;
				}
			}
			result.add(dto);
		}
		return result;
	}

	@Override
	public void shutProfileDown(InappropriateListDTO content) {
		String username = mediaRepository.findOneById(content.getMediaId()).getUsername();
		profileService.deactivateProfile(username);
		
		InapropriateContent inappropriateContent = inapropriateContentRepository.findOneById(content.getId());
		inappropriateContent.setReviewed(true);
		inapropriateContentRepository.save(inappropriateContent);
		
	}

	@Override
	public void approveInappropriateContent(InappropriateListDTO content) {
		InapropriateContent inappropriateContent = inapropriateContentRepository.findOneById(content.getId());
		inappropriateContent.setReviewed(true);
		inapropriateContentRepository.save(inappropriateContent);
		
	}

	@Override
	public void deleteInappropriateContent(InappropriateListDTO content) {
		InapropriateContent inappropriateContent = inapropriateContentRepository.findOneById(content.getId());
		inappropriateContent.setReviewed(true);
		inapropriateContentRepository.save(inappropriateContent);
		
		Media media = mediaRepository.findOneById(content.getMediaId());	
		Post post = postRepository.findOneById(content.getPostId());	
		Favourites favourite = favouritesRepository.findByPost(post);
		app.media.model.Collection collection = collectionRepository.findByFavourite(favourite);
		if(collection != null) {
			collectionRepository.delete(collection);
		}
		if(favourite !=null) {		
			favouritesRepository.delete(favourite);
		}
		postRepository.delete(post);
		
		inapropriateContentRepository.delete(inappropriateContent);
		mediaRepository.delete(media);
		
	}

	public void delete(long id) {
		Post post = postRepository.findAll().stream().filter(p -> p.getMedia().getId() == id)
				.findFirst().orElse(null);
		if(post != null) {
			postRepository.delete(post);
			return;
		}
		Story story = storyRepository.findAll().stream().filter(s -> s.getMedia().getId() == id)
				.findFirst().orElse(null);
		if(story != null)
			storyRepository.delete(story);
	}

	public boolean exists(long id) {
		return mediaRepository.findById(id).isPresent();
	}
	

	@Override
	public String getPath(long id) {
		Media media = mediaRepository.findById(id).orElse(null);
		if(media == null)
			return null;
		return media.getPath().stream().findFirst().orElse(null);
	}

	@Override
	public long createNewMedia(long id, String username) {
		Media oldMedia = mediaRepository.findById(id).get();
		Media newMedia = new Media();
		newMedia.setCreated(oldMedia.getCreated());
		newMedia.setPath(new HashSet<>(oldMedia.getPath()));
		newMedia.setUsername(username);
		newMedia = mediaRepository.save(newMedia);

		Post oldPost = postRepository.findAll().stream().filter(p -> p.getMedia().getId() == id).findFirst().orElse(null);
		if(oldPost != null) {
			Post post = new Post();
			post.setMedia(newMedia);
			post.setDescription(oldPost.getDescription());
			post.setLocation(oldPost.getLocation());
			post.setTags(new HashSet<>(oldPost.getTags()));
			postRepository.save(post);
			return newMedia.getId();
		}
		Story oldStory = storyRepository.findAll().stream().filter(s -> s.getMedia().getId() == id).findFirst().orElse(null);
		if(oldStory != null) {
			Story newStory = new Story();
			newStory.setMedia(newMedia);
			newStory.setCloseFriends(false);
			newStory.setDateCreated(LocalDateTime.now());
			storyRepository.save(newStory);
			return newMedia.getId();
		}
		return -1;
	}
}
