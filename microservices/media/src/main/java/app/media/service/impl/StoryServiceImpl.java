package app.media.service.impl;

import app.media.dtos.CampaignForUserDTO;
import app.media.dtos.StoryInfoDTO;
import app.media.exception.ProfileBlockedException;
import app.media.exception.ProfilePrivateException;
import app.media.model.Post;
import app.media.model.Story;
import app.media.repository.StoryRepository;
import app.media.service.CampaignService;
import app.media.service.ProfileService;
import app.media.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoryServiceImpl implements StoryService {

	private StoryRepository storyRepository;
	private ProfileService profileService;
	private CampaignService campaignService;

	@Autowired
	public StoryServiceImpl(StoryRepository storyRepository, ProfileService profileService, CampaignService campaignService) {
		this.storyRepository = storyRepository;
		this.profileService = profileService;
		this.campaignService = campaignService;
	}

	@Override
	public List<StoryInfoDTO> getFeed(String username) {
		List<Story> result = new ArrayList<>();

		List<String> targetedProfiles = profileService.getFollowing(username);
		List<String> mutedProfiles = profileService.getMuted(username);
		List<String> blockedProfiles = profileService.getBlocked(username);
		List<String> inactiveProfiles = profileService.getAllInactiveProfiles();
		
		targetedProfiles.removeAll(mutedProfiles);
		targetedProfiles.removeAll(blockedProfiles);
		targetedProfiles.removeAll(inactiveProfiles);

		List<Story> targetedStories = storyRepository.findAll().stream()
				.filter(s -> targetedProfiles.contains(s.getMedia().getUsername())).collect(Collectors.toList());

		for (Story story : targetedStories) {
			if (story.isActive() && (!story.isCloseFriends()
					|| profileService.getCloseFriends(story.getMedia().getUsername()).contains(username)))
				result.add(story);
		}
		for (Story story : storyRepository.findAll().stream().filter(s -> s.getMedia().getUsername().equals(username) && s.isActive()).collect(Collectors.toList()))
			result.add(story);

		result = syncFeed(result, username);
		result.sort((r1, r2) -> r1.getMedia().getCreated().isBefore(r2.getMedia().getCreated()) ? 1 : -1);

		List<StoryInfoDTO> res = new ArrayList<>();
		for(Story story : result) {
			if(!mutedProfiles.contains(story.getMedia().getUsername())
				&& ! blockedProfiles.contains(story.getMedia().getUsername())
				&& ! inactiveProfiles.contains(story.getMedia().getUsername())) {
				for(String path : story.getMedia().getPath()) {
					res.add(toStoryInfoDTO(story, path));
				}
			}
		}

		return res;
	}

	@Override
	public List<StoryInfoDTO> getForProfile(String requestedBy, String profile)
			throws ProfilePrivateException, ProfileBlockedException {
		List<StoryInfoDTO> result = new ArrayList<>();

		List<String> followers = profileService.getFollowing(profile);
		boolean follower = followers.contains(requestedBy);
		if (!profileService.isPublic(profile) && !follower && !profile.equals(requestedBy))
			throw new ProfilePrivateException();

		if (requestedBy != null) {
			List<String> blockedProfiles = profileService.getBlocked(requestedBy);
			boolean blocked = blockedProfiles.contains(profile);
			if (blocked)
				throw new ProfileBlockedException();
		}

		List<Story> stories = storyRepository.findAll().stream()
				.filter(p -> p.getMedia().getUsername().equals(profile) && p.isActive()
						&& (!p.isCloseFriends() || profile.equals(requestedBy)
								|| profileService.getCloseFriends(profile).contains(requestedBy)))
				.collect(Collectors.toList());
		stories = syncWithCampaigns(stories);
		stories.addAll(addInactiveFromCampaigns(profile));
		stories.forEach(s -> s.getMedia().getPath().forEach(url -> result.add(toStoryInfoDTO(s, url))));

		result.sort((r1, r2) -> r1.getCreated().isBefore(r2.getCreated()) ? 1 : -1);
		return result;
	}

	private StoryInfoDTO toStoryInfoDTO(Story story, String url) {
		StoryInfoDTO result = new StoryInfoDTO();
		result.setId(story.getId());
		result.setCreated(story.getMedia().getCreated());
		result.setUsername(story.getMedia().getUsername());
		result.setUrl(url);
		result.setLink(getLink(story.getMedia().getId()));
		return result;
	}

	@Override
	public List<StoryInfoDTO> getAllUserStories(String username) {

		List<StoryInfoDTO> result = new ArrayList<StoryInfoDTO>();
		List<Story> stories = storyRepository.findAll().stream()
				.filter(s -> s.getMedia().getUsername().equals(username)).collect(Collectors.toList());
		stories = syncWithCampaigns(stories);
		stories.forEach(s -> s.getMedia().getPath().forEach(url -> result.add(toStoryInfoDTO(s, url))));
		return result;
	}

	@Override
	public List<StoryInfoDTO> getStoryHighlights(String username) {
		List<StoryInfoDTO> result = new ArrayList<StoryInfoDTO>();
		List<Story> stories = storyRepository.findAll().stream()
				.filter(s -> s.getMedia().getUsername().equals(username) && s.isHighlighted()).collect(Collectors.toList());
		stories = syncWithCampaigns(stories);
		stories.forEach(s -> s.getMedia().getPath().forEach(url -> result.add(toStoryInfoDTO(s, url))));
		return result;
	}
	
	@Override
	public void addToStoryHighlights(StoryInfoDTO dto) {
		Story story= storyRepository.findById(dto.getId()).get();
		story.setHighlighted(true);
		storyRepository.save(story);
	}

	private List<Story> syncWithCampaigns(List<Story> stories) {
		List<Story> result = new ArrayList<>();
		for(Story story : stories) {
			if(!campaignService.isPartOfCampaign(story.getMedia().getId()))
				result.add(story);
			else if(campaignService.shouldDispaly(story.getMedia().getId()))
				result.add(story);
		}
		return result;
	}

	private List<Story> addInactiveFromCampaigns(String user) {
		return storyRepository.findAll().stream().filter(s -> s.getMedia().getUsername().equals(user))
				.filter(s -> !s.isActive())
				.filter(s -> campaignService.isPartOfCampaign(s.getMedia().getId()))
				.filter(s -> campaignService.shouldDispaly(s.getMedia().getId()))
				.collect(Collectors.toList());
	}

	public List<Story> syncFeed(List<Story> stories, String username) {
		List<Story> result = new ArrayList<>();
		for(Story story : stories) {
			if(!campaignService.isPartOfCampaign(story.getMedia().getId()))
				result.add(story);
		}
		for(CampaignForUserDTO c : campaignService.getCampaignsForUser(username)) {
			Story story = storyRepository.findAll().stream().filter(s -> s.getMedia().getId() == c.mediaId)
					.findFirst().orElse(null);
			if(story != null) {
				story.getMedia().setCreated(c.published);
				result.add(story);
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
		Story story = storyRepository.findById(id).orElse(null);
		if(story != null) {
			campaignService.saveLinkClick(story.getMedia().getId(), username);
		}
	}
}
