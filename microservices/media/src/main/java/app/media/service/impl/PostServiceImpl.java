package app.media.service.impl;

import app.media.dtos.PostInfoDTO;
import app.media.dtos.SearchResultDTO;
import app.media.exception.PostDoesNotExistException;
import app.media.exception.ProfileBlockedException;
import app.media.exception.ProfilePrivateException;
import app.media.model.Post;
import app.media.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.media.repository.PostRepository;
import app.media.service.PostService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{

	private PostRepository postRepository;
	private ProfileService profileService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, ProfileService profileService) {
        this.postRepository = postRepository;
        this.profileService = profileService;
    }

    @Override
    public List<PostInfoDTO> getFeed(String username) {
        System.out.println("USERNAME FROM TOKEN: " + username);
        List<PostInfoDTO> result = new ArrayList<>();

        List<String> targetedProfiles = profileService.getFollowing(username);
        List<String> mutedProfiles = profileService.getMuted(username);
        List<String> blockedProfiles = profileService.getBlocked(username);

        targetedProfiles.removeAll(mutedProfiles);
        targetedProfiles.removeAll(blockedProfiles);

        List<Post> targetedPosts = postRepository.findAll().stream()
                .filter(p -> targetedProfiles.contains(p.getMedia().getUsername()))
                .collect(Collectors.toList());Collectors.toList();

        for(Post post : targetedPosts)
            result.add(toPostInfoDTO(post));
        postRepository.findAll().stream()
                .filter(p -> p.getMedia().getUsername().equals(username))
                .forEach(p -> result.add(toPostInfoDTO(p)));

        result.sort((r1, r2) -> r1.getCreated().isBefore(r2.getCreated()) ? 1 : -1);
        return result;
    }

    @Override
    public List<PostInfoDTO> getForProfile(String requestedBy, String profile) throws ProfilePrivateException, ProfileBlockedException {
        System.out.println("USERNAME FROM TOKEN: " + requestedBy);
        List<PostInfoDTO> result;
        if(requestedBy == null)
            result = getForProfileWhenUnauthenticated(profile);
        else
            result = getForProfileWhenAuthenticated(requestedBy, profile);
        result.sort((r1, r2) -> r1.getCreated().isBefore(r2.getCreated()) ? 1 : -1);
        return result;
    }

    @Override
    public List<SearchResultDTO> search(String requestedBy, String criterion) {
        System.out.println("USERNAME FROM TOKEN: " + requestedBy);
        List<SearchResultDTO> result = new ArrayList<>();
        for(String location : getAllLocations(requestedBy))
            if(location.contains(criterion.toLowerCase()))
                result.add(new SearchResultDTO(location, "location"));
        for(String hashtag : getAllHashtags(requestedBy))
            if(hashtag.contains(criterion.toLowerCase()))
                result.add(new SearchResultDTO(hashtag, "hashtag"));
        for(String profile : getAllProfiles(requestedBy))
            if(profile.contains(criterion.toLowerCase()))
                result.add(new SearchResultDTO(profile, "profile"));
        return result;
    }

    @Override
    public List<PostInfoDTO> getAllWithLocation(String requestedBy, String location) {
        System.out.println("USERNAME FROM TOKEN: " + requestedBy);
        return postRepository.findAll().stream()
                .filter(p -> (profileService.isPublic(p.getMedia().getUsername())
                        || p.getMedia().getUsername().equals(requestedBy)
                        || (profileService.getFollowing(p.getMedia().getUsername()).contains(requestedBy)
                        && !profileService.getBlocked(requestedBy).contains(p.getMedia().getUsername())))
                        &&p.getLocation() != null && location.toLowerCase().equals(p.getLocation().toLowerCase()))
                .map(p -> toPostInfoDTO(p))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostInfoDTO> getAllWithHashtag(String requestedBy, String hashtag) {
        System.out.println("USERNAME FROM TOKEN: " + requestedBy);
        return postRepository.findAll().stream()
                .filter(p -> (profileService.isPublic(p.getMedia().getUsername())
                        || p.getMedia().getUsername().equals(requestedBy)
                        || (profileService.getFollowing(p.getMedia().getUsername()).contains(requestedBy)
                        && !profileService.getBlocked(requestedBy).contains(p.getMedia().getUsername())))
                        && p.getTags() != null && p.getTags().stream().filter(t -> hashtag.toLowerCase().equals(t.toLowerCase())).count() > 0)
                .map(p -> toPostInfoDTO(p))
                .collect(Collectors.toList());
    }

    @Override
    public PostInfoDTO get(String requestedBy, long postId) throws PostDoesNotExistException {
        System.out.println("USERNAME FROM TOKEN: " + requestedBy);
        Post post = postRepository.findAll().stream()
                .filter(p -> (profileService.isPublic(p.getMedia().getUsername())
                        || p.getMedia().getUsername().equals(requestedBy)
                        || (profileService.getFollowers(p.getMedia().getUsername()).contains(requestedBy)
                        && !profileService.getBlocked(requestedBy).contains(p.getMedia().getUsername())))
                        && p.getId() == postId).findFirst().orElse(null);
        if(post == null)
            throw new PostDoesNotExistException();
        return toPostInfoDTO(post);
    }

    private Set<String> getAllLocations(String requestedBy) {
        Set<String> result = new HashSet<>();
        postRepository.findAll().stream()
                .filter(p ->p.getLocation() != null && (
                            profileService.isPublic(p.getMedia().getUsername())
                            || p.getMedia().getUsername().equals(requestedBy)
                            || (profileService.getFollowing(p.getMedia().getUsername()).contains(requestedBy)
                                && !profileService.getBlocked(requestedBy).contains(p.getMedia().getUsername()))))
                .forEach(p -> result.add(p.getLocation().toLowerCase()));
        return result;
    }

    private Set<String> getAllHashtags(String requestedBy) {
        Set<String> result = new HashSet<>();
        postRepository.findAll().stream()
                .filter(p -> p.getTags() != null && (
                        profileService.isPublic(p.getMedia().getUsername())
                        || p.getMedia().getUsername().equals(requestedBy)
                        || (profileService.getFollowing(p.getMedia().getUsername()).contains(requestedBy)
                        && !profileService.getBlocked(requestedBy).contains(p.getMedia().getUsername()))))
                .forEach(p -> p.getTags().forEach(t -> result.add(t.toLowerCase())));
        return result;
    }

    private Set<String> getAllProfiles(String requestedBy) {
        if(requestedBy != null) {
            return profileService.getAll().stream()
                    .filter(p -> !profileService.getBlocked(requestedBy).contains(p))
                    .collect(Collectors.toSet());
        }
        return profileService.getAll().stream().collect(Collectors.toSet());
    }

    private List<PostInfoDTO> getForProfileWhenUnauthenticated(String profile) throws ProfilePrivateException {
        if(!profileService.isPublic(profile))
            throw new ProfilePrivateException();
        return getForProfile(profile);
    }

    private List<PostInfoDTO> getForProfileWhenAuthenticated(String requestedBy, String profile) throws ProfilePrivateException, ProfileBlockedException {
        List<String> followers = profileService.getFollowers(profile);
        boolean follower = followers.contains(requestedBy);
        if(!profileService.isPublic(profile) && !follower && !profile.equals(requestedBy))
            throw new ProfilePrivateException();

        List<String> blockedProfiles = profileService.getBlocked(requestedBy);
        boolean blocked = blockedProfiles.contains(profile);
        if(blocked)
            throw new ProfileBlockedException();

        return getForProfile(profile);
    }

    private List<PostInfoDTO> getForProfile(String profile) {
        return postRepository.findAll().stream()
                .filter(p -> p.getMedia().getUsername().equals(profile))
                .map(p -> toPostInfoDTO(p))
                .collect(Collectors.toList());
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
        return result;
    }
}
