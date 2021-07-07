package app.media.service.impl;

import app.media.dtos.*;
import app.media.exception.PostDoesNotExistException;
import app.media.exception.ProfileBlockedException;
import app.media.exception.ProfilePrivateException;
import app.media.model.Collection;
import app.media.model.Favourites;
import app.media.model.Post;
import app.media.model.Rating;
import app.media.model.RatingType;
import app.media.service.CampaignService;
import app.media.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.media.repository.CollectionRepository;
import app.media.repository.FavouritesRepository;
import app.media.repository.PostRepository;
import app.media.service.PostService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

	private PostRepository postRepository;
	private ProfileService profileService;
	private FavouritesRepository favouritesRepository;
	private CollectionRepository collectionRepository;
	private CampaignService campaignService;

	@Autowired
	public PostServiceImpl(PostRepository postRepository, ProfileService profileService, FavouritesRepository favRepo,
			CollectionRepository collectionRepository, CampaignService campaignService) {
		this.postRepository = postRepository;
		this.profileService = profileService;
		this.favouritesRepository = favRepo;
		this.collectionRepository = collectionRepository;
		this.campaignService = campaignService;

	}

	@Override
	public List<PostInfoDTO> getDislikedContent(String username) {
		List<PostInfoDTO> result = new ArrayList<>();
		
		List<String> targetedProfiles = profileService.getAll();
		List<String> blockedProfiles = profileService.getBlocked(username);
		targetedProfiles.removeAll(blockedProfiles);
		
		List<Post> targetedPostsWithoutBlockedProfiles = postRepository.findAll().stream()
				.filter(p -> targetedProfiles.contains(p.getMedia().getUsername())).collect(Collectors.toList());
		Collectors.toList();
		
		for(Post post : targetedPostsWithoutBlockedProfiles) {
			for(Rating rating : post.getRatings())
			{
				if(rating.getUsername().equals(username) && rating.getRatingType() == RatingType.DISLIKE) {
					result.add(toPostInfoDTO(post));
				}
			}
		}
		result.sort((r1, r2) -> r1.getCreated().isBefore(r2.getCreated()) ? 1 : -1);
		return result;
	}
	
	@Override
	public List<PostInfoDTO> getLikedContent(String username) {
		List<PostInfoDTO> result = new ArrayList<>();
		
		List<String> targetedProfiles = profileService.getAll();
		List<String> blockedProfiles = profileService.getBlocked(username);
		targetedProfiles.removeAll(blockedProfiles);
		
		List<Post> targetedPostsWithoutBlockedProfiles = postRepository.findAll().stream()
				.filter(p -> targetedProfiles.contains(p.getMedia().getUsername())).collect(Collectors.toList());
		Collectors.toList();
		
		for(Post post : targetedPostsWithoutBlockedProfiles) {
			for(Rating rating : post.getRatings())
			{
				if(rating.getUsername().equals(username) && rating.getRatingType() == RatingType.LIKE) {
					result.add(toPostInfoDTO(post));
				}
			}
		}
		result.sort((r1, r2) -> r1.getCreated().isBefore(r2.getCreated()) ? 1 : -1);
		return result;
	}
	
	@Override
	public List<PostInfoDTO> getFeed(String username) {
		List<Post> result = new ArrayList<>();

		List<String> targetedProfiles = profileService.getFollowing(username);
		List<String> mutedProfiles = profileService.getMuted(username);
		List<String> blockedProfiles = profileService.getBlocked(username);
		List<String> inactiveProfiles = profileService.getAllInactiveProfiles();
		
		targetedProfiles.removeAll(mutedProfiles);
		targetedProfiles.removeAll(blockedProfiles);
		targetedProfiles.removeAll(inactiveProfiles);

		List<Post> targetedPosts = postRepository.findAll().stream()
				.filter(p -> targetedProfiles.contains(p.getMedia().getUsername())).collect(Collectors.toList());
		Collectors.toList();

		for (Post post : targetedPosts)
			result.add(post);
		for(Post post : postRepository.findAll().stream().filter(p -> p.getMedia().getUsername().equals(username)).collect(Collectors.toList()))
			result.add(post);

		result = syncFeed(result, username);
		result.sort((r1, r2) -> r1.getMedia().getCreated().isBefore(r2.getMedia().getCreated()) ? 1 : -1);

		List<PostInfoDTO> res = new ArrayList<>();
		for(Post post : result) {
			if(!mutedProfiles.contains(post.getMedia().getUsername())
			&& !blockedProfiles.contains(post.getMedia().getUsername())
			&& !inactiveProfiles.contains(post.getMedia().getUsername())) {
				res.add(toPostInfoDTO(post));
			}
		}
		return res;
	}

	@Override
	public List<PostInfoDTO> getForProfile(String requestedBy, String profile)
			throws ProfilePrivateException, ProfileBlockedException {
		System.out.println("USERNAME FROM TOKEN: " + requestedBy);
		List<PostInfoDTO> result;
		if (requestedBy == null)
			result = getForProfileWhenUnauthenticated(profile);
		else
			result = getForProfileWhenAuthenticated(requestedBy, profile);
		result.sort((r1, r2) -> r1.getCreated().isBefore(r2.getCreated()) ? 1 : -1);
		return result;
	}

	@Override
	public List<SearchResultDTO> search(String requestedBy, String criterion) {
		List<SearchResultDTO> result = new ArrayList<>();
		for (String location : getAllLocations(requestedBy))
			if (location.contains(criterion.toLowerCase()))
				result.add(new SearchResultDTO(location, "location"));
		for (String hashtag : getAllHashtags(requestedBy))
			if (hashtag.contains(criterion.toLowerCase()))
				result.add(new SearchResultDTO(hashtag, "hashtag"));
		for (String profile : getAllProfiles(requestedBy))
			if (profile.contains(criterion.toLowerCase()))
				result.add(new SearchResultDTO(profile, "profile"));
		return result;
	}

	@Override
	public List<PostInfoDTO> getAllWithLocation(String requestedBy, String location) {
		List<Post> posts = postRepository.findAll().stream()
				.filter(p -> (profileService.isPublic(p.getMedia().getUsername())
						|| p.getMedia().getUsername().equals(requestedBy)
						|| (profileService.getFollowing(p.getMedia().getUsername()).contains(requestedBy)
								&& !profileService.getBlocked(requestedBy).contains(p.getMedia().getUsername())))
						&& p.getLocation() != null && location.toLowerCase().equals(p.getLocation().toLowerCase()))
				.collect(Collectors.toList());
		posts = syncWithCampaigns(posts);
		return posts.stream().map(p -> toPostInfoDTO(p)).collect(Collectors.toList());
	}

	@Override
	public List<PostInfoDTO> getAllWithHashtag(String requestedBy, String hashtag) {
		List<Post> posts = postRepository.findAll().stream()
				.filter(p -> (profileService.isPublic(p.getMedia().getUsername())
						|| p.getMedia().getUsername().equals(requestedBy)
						|| (profileService.getFollowing(p.getMedia().getUsername()).contains(requestedBy)
								&& !profileService.getBlocked(requestedBy).contains(p.getMedia().getUsername())))
						&& p.getTags() != null
						&& p.getTags().stream().filter(t -> hashtag.toLowerCase().equals(t.toLowerCase())).count() > 0)
				.collect(Collectors.toList());
		posts = syncWithCampaigns(posts);
		return posts.stream().map(p -> toPostInfoDTO(p)).collect(Collectors.toList());
	}

	@Override
	public PostInfoDTO get(String requestedBy, long postId) throws PostDoesNotExistException {
		System.out.println("USERNAME FROM TOKEN: " + requestedBy);
		Post post = postRepository.findAll().stream()
				.filter(p -> (profileService.isPublic(p.getMedia().getUsername())
						|| p.getMedia().getUsername().equals(requestedBy)
						|| (profileService.getFollowers(p.getMedia().getUsername()).contains(requestedBy)
								&& !profileService.getBlocked(requestedBy).contains(p.getMedia().getUsername())))
						&& p.getId() == postId)
				.findFirst().orElse(null);
		if (post == null || (campaignService.isPartOfCampaign(post.getMedia().getId())
				&& !campaignService.shouldDispaly(post.getMedia().getId())))
			throw new PostDoesNotExistException();
		return toPostInfoDTO(post);
	}

	private Set<String> getAllLocations(String requestedBy) {
		Set<String> result = new HashSet<>();
		postRepository.findAll().stream()
				.filter(p -> p.getLocation() != null && (profileService.isPublic(p.getMedia().getUsername())
						|| p.getMedia().getUsername().equals(requestedBy)
						|| (profileService.getFollowing(p.getMedia().getUsername()).contains(requestedBy)
								&& !profileService.getBlocked(requestedBy).contains(p.getMedia().getUsername()))))
				.forEach(p -> result.add(p.getLocation().toLowerCase()));
		return result;
	}

	private Set<String> getAllHashtags(String requestedBy) {
		Set<String> result = new HashSet<>();
		postRepository.findAll().stream()
				.filter(p -> p.getTags() != null && (profileService.isPublic(p.getMedia().getUsername())
						|| p.getMedia().getUsername().equals(requestedBy)
						|| (profileService.getFollowing(p.getMedia().getUsername()).contains(requestedBy)
								&& !profileService.getBlocked(requestedBy).contains(p.getMedia().getUsername()))))
				.forEach(p -> p.getTags().forEach(t -> result.add(t.toLowerCase())));
		return result;
	}

	private Set<String> getAllProfiles(String requestedBy) {
		if (requestedBy != null) {
			return profileService.getAll().stream().filter(p -> !profileService.getBlocked(requestedBy).contains(p) && !profileService.getAllInactiveProfiles().contains(p))
					.collect(Collectors.toSet());
		}
		return profileService.getAll().stream().collect(Collectors.toSet());
	}

	private List<PostInfoDTO> getForProfileWhenUnauthenticated(String profile) throws ProfilePrivateException {
		if (!profileService.isPublic(profile))
			throw new ProfilePrivateException();
		return getForProfile(profile);
	}

	private List<PostInfoDTO> getForProfileWhenAuthenticated(String requestedBy, String profile)
			throws ProfilePrivateException, ProfileBlockedException {
		List<String> followers = profileService.getFollowers(profile);
		boolean follower = followers.contains(requestedBy);
		if (!profileService.isPublic(profile) && !follower && !profile.equals(requestedBy))
			throw new ProfilePrivateException();

		List<String> blockedProfiles = profileService.getBlocked(requestedBy);
		boolean blocked = blockedProfiles.contains(profile);
		if (blocked)
			throw new ProfileBlockedException();

		return getForProfile(profile);
	}

	private List<PostInfoDTO> getForProfile(String profile) {
		List<Post> posts = postRepository.findAll().stream().filter(p -> p.getMedia().getUsername().equals(profile))
							.collect(Collectors.toList());
		posts = syncWithCampaigns(posts);
		return posts.stream().map(p -> toPostInfoDTO(p)).collect(Collectors.toList());
	}

	private PostInfoDTO toPostInfoDTO(Post post) {
		PostInfoDTO result = new PostInfoDTO();
		result.setId(post.getId());
		result.setUsername(post.getMedia().getUsername());
		result.setDescription(post.getDescription());
		result.setHashtags(post.getTags());
		result.setLocation(post.getLocation());
		result.setCreated(post.getMedia().getCreated());
		result.setUrls(post.getMedia().getPath());
		result.setLink(getLink(post.getMedia().getId()));
		return result;
	}

	@Override
	public List<PostInfoDTO> getFavouritesForProfile(String profile) {
		List<PostInfoDTO> result = new ArrayList<PostInfoDTO>();
		for (Favourites f : favouritesRepository.findAll().stream().filter(p -> p.getUsername().equals(profile))
				.collect(Collectors.toList())) {
			for (Post p : postRepository.findAll()) {
				if (p.getId() == f.getPost().getId()) {
					result.add(toPostInfoDTO(p));
				}
			}
		}
		return result;
	}

	@Override
	public Favourites saveToFavourites(long postId, String username) {
		Favourites favourite = new Favourites();
		Post post = postRepository.findById(postId).get();
		favourite.setPost(post);
		favourite.setUsername(username);

		boolean exists = false;
		for (Favourites f : favouritesRepository.findAll()) {
			if (f.getPost().equals(post) && f.getUsername().equals(username)) {
				exists = true;
			}
		}
		if (!exists) {
			favouritesRepository.save(favourite);
		}
		return favourite;
	}

	@Override
	public void addFavouritesToCollection(String loggedInUsername, CollectionDTO dto) {
		Collection collection = new Collection();
		collection.setUsername(loggedInUsername);
		Post post = postRepository.findOneById(dto.getId());
		Favourites fav = favouritesRepository.findByPost(post);
		collection.setFavourite(fav);
		collection.setName(dto.getName());

		collectionRepository.save(collection);
	}

	@Override
	public List<CollectionInfoDTO> getCollectionsForProfile(String loggedInUsername) {
		List<CollectionInfoDTO> result = new ArrayList<CollectionInfoDTO>();
		for (Collection c : collectionRepository.findAll().stream()
				.filter(c -> c.getUsername().equals(loggedInUsername)).collect(Collectors.toList())) {
			result.add(toCollectionInfoDto(c));
		}
		return result;
	}
	
	private CollectionInfoDTO toCollectionInfoDto(Collection c) {
		CollectionInfoDTO dto = new CollectionInfoDTO();
		dto.setId(c.getId());
		dto.setName(c.getName());
		dto.setUrls(c.getFavourite().getPost().getMedia().getPath());
		return dto;
	}

	private List<Post> syncWithCampaigns(List<Post> posts) {
		List<Post> result = new ArrayList<>();
		for(Post post : posts) {
			if(!campaignService.isPartOfCampaign(post.getMedia().getId()))
				result.add(post);
			else if(campaignService.shouldDispaly(post.getMedia().getId()))
				result.add(post);
		}
		return result;
	}

	public List<Post> syncFeed(List<Post> posts, String username) {
		List<Post> result = new ArrayList<>();
		for(Post post : posts) {
			if(!campaignService.isPartOfCampaign(post.getMedia().getId()))
				result.add(post);
		}
		for(CampaignForUserDTO c : campaignService.getCampaignsForUser(username)) {
			Post post = postRepository.findAll().stream().filter(p -> p.getMedia().getId() == c.mediaId)
					.findFirst().orElse(null);
			if(post != null) {
				post.getMedia().setCreated(c.published);
				result.add(post);
			}
		}
		return result;
	}

	@Override
	public String getLink(long id) {
		return campaignService.getLink(id);
	}

	@Override
    public void saveLinkClick(long id, String username) {
	    Post post = postRepository.findById(id).orElse(null);
	    if(post != null) {
            campaignService.saveLinkClick(post.getMedia().getId(), username);
        }
    }

	@Override
	public ReportDto getReport(long id) {
		List<Post> posts = postRepository.findAll().stream()
				.filter(p -> p.getMedia().getId() == id).collect(Collectors.toList());
		if(posts.size() == 0) return null;
		ReportDto result = new ReportDto();
		result.commentCount = 0;
		result.likeCount = 0;
		result.dislikeCount = 0;
		for(Post post : posts) {
			result.commentCount += post.getComments().size();
			result.likeCount += post.getRatings().stream().filter(r -> r.getRatingType() == RatingType.LIKE).count();
			result.dislikeCount += post.getRatings().stream().filter(r -> r.getRatingType() == RatingType.DISLIKE).count();
		}
		return result;
	}
}
