package app.campaign.service.impl;

import app.campaign.dto.*;
import app.campaign.model.*;
import app.campaign.repository.AgeGroupRepository;
import app.campaign.repository.CampaignRepository;
import app.campaign.repository.LinkClickRepository;
import app.campaign.service.AuthService;
import app.campaign.service.CampaignService;
import app.campaign.service.MediaService;
import app.campaign.service.ProfileService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CampaignServiceImpl implements CampaignService {

    private CampaignRepository campaignRepository;
    private AgeGroupRepository ageGroupRepository;
    private MediaService mediaService;
    private ProfileService profileService;
    private AuthService authService;
    private LinkClickRepository linkClickRepository;

    public CampaignServiceImpl(CampaignRepository campaignRepository, AgeGroupRepository ageGroupRepository,
                               MediaService mediaService, ProfileService profileService, AuthService authService,
                               LinkClickRepository linkClickRepository) {
        this.campaignRepository = campaignRepository;
        this.ageGroupRepository = ageGroupRepository;
        this.mediaService = mediaService;
        this.profileService = profileService;
        this.authService = authService;
        this.linkClickRepository = linkClickRepository;
    }

    @Override
    public void create(CampaignDTO dto, String agent) throws Exception {
        try {
            Campaign campaign = createCampaign(dto, agent);
            campaignRepository.save(campaign);
        } catch (Exception e) {
            if(mediaService.exists(dto.getMediaId()))
                mediaService.delete(dto.getMediaId());
            throw e;
        }
    }

    @Override
    public void delete(long id, String agent) throws Exception {
        Campaign campaign = campaignRepository.findById(id).orElse(null);
        if(campaign == null)
            return;
        if(!campaign.getAgentUsername().equals(agent))
            throw new Exception("You can't delete a campaign that is not yours");
        if(campaign.started())
            throw new Exception("Campaign has started");
        mediaService.delete(campaign.getMediaId());
        campaignRepository.delete(campaign);
    }

    @Override
    public void update(long id, DetailsDTO dto, String agent) throws Exception {
        Campaign campaign = campaignRepository.findById(id).orElse(null);
        if(campaign == null)
            return;
        if(!campaign.getAgentUsername().equals(agent))
            throw new Exception("You can't change a campaign that is not yours");
        if(!campaign.isRepeated())
            throw new Exception("You can't change a one-time campaign");
        if(campaign.ended())
            throw new Exception("You can't change a campaign that has ended");
        campaign.addDetails(createDetails(dto));
        campaignRepository.save(campaign);
    }

    @Override
    public List<CampaignDTO> get(String agent) {
        return campaignRepository.findAll().stream()
                .filter(c -> c.getAgentUsername().equals(agent))
                .map(c -> createCampaignDTO(c))
                .sorted(Comparator.comparing(dto -> dto.getStart()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean shouldDisplayMedia(long mediaId) {
        Campaign campaign = campaignRepository.findAll().stream()
                .filter(c -> c.getMediaId() == mediaId).findFirst().orElse(null);
        if(campaign == null)
            return false;
        if(campaign.started())
            return true;
        return false;
    }

    @Override
    public boolean isPartOfCampaign(long mediaId) {
        return campaignRepository.findAll().stream()
                .filter(c -> c.getMediaId() == mediaId)
                .findFirst().isPresent();
    }

    @Override
    public List<CampaignForUserDTO> getCampaigns(String username) {
        List<Campaign> campaigns = new ArrayList<>();
        for(Campaign campaign : getActive()) {
            if(campaign.getAgentUsername().equals(username)) {
                campaigns.add(campaign);
            } else if (profileService.getFollowers(campaign.getAgentUsername()).contains(username)) {
                campaigns.add(campaign);
            } else if (authService.getTargetGroup(campaign.getTargetGroup()).contains(username)) {
                campaigns.add(campaign);
            }
        }

        List<CampaignForUserDTO> result = new ArrayList<>();
        for(Campaign campaign : campaigns) {
            for(LocalDateTime exposureDate : campaign.getExposureDates()) {
                CampaignForUserDTO cfu = new CampaignForUserDTO();
                cfu.mediaId = campaign.getMediaId();
                cfu.published = exposureDate;
                result.add(cfu);
            }
        }
        return result;
    }

    @Override
    public String getLink(long id) {
        return campaignRepository.findAll().stream().filter(c -> c.getMediaId() == id)
                .findFirst().map(c -> c.getLink()).orElse(null);
    }

    @Override
    public void saveLinkClick(long mediaId, String profile) {
        Campaign campaign = campaignRepository.findAll().stream().filter(c -> c.getMediaId() == mediaId)
                .findFirst().orElse(null);
        if(campaign != null) {
            linkClickRepository.save(new LinkClick(campaign, profile));
        }
    }

    private Campaign createCampaign(CampaignDTO dto, String agent) throws Exception {
        if(!mediaService.exists(dto.getMediaId()))
            throw new Exception("Media content not created properly");

        Campaign campaign = new Campaign();

        campaign.setId(0);
        campaign.setAgentUsername(agent);
        campaign.setLink(dto.getLink());
        campaign.setStart(dto.getStart());
        campaign.setMediaId(dto.getMediaId());
        campaign.setTargetGroup(createTargetGroup(dto));

        RepeatedCampaignDetails details = createDetails(dto.getDetails());
        if(details != null) {
            LocalDateTime time = campaign.getStart();
            time.with(LocalTime.MIDNIGHT);
            campaign.setStart(time);
        }
        campaign.addDetails(details);

        return campaign;
    }

    private CampaignDTO createCampaignDTO(Campaign campaign) {
        CampaignDTO dto = new CampaignDTO();
        dto.setId(campaign.getId());
        dto.setMediaId(campaign.getMediaId());
        dto.setStart(campaign.getStart());
        dto.setLink(campaign.getLink());
        dto.setTargetedGenders(campaign.getTargetGroup().getGenders());
        if(campaign.getActiveDetails() == null)
            dto.setDetails(null);
        else {
            DetailsDTO detDTO = new DetailsDTO();
            detDTO.setEndDate(campaign.getActiveDetails().getEndDate());
            detDTO.setTimesPerDay(campaign.getActiveDetails().getTimesPerDay());
            dto.setDetails(detDTO);
        }
        dto.setTargetedAges(campaign.getTargetGroup().getAgeGroups().stream()
                .map(a -> a.getId()).collect(Collectors.toSet()));
        dto.report = getReport(dto.getId());
        return dto;
    }

    private RepeatedCampaignDetails createDetails(DetailsDTO dto) throws Exception {
        if(dto == null)
            return null;
        RepeatedCampaignDetails details = new RepeatedCampaignDetails();
        details.setCreated(LocalDateTime.now());
        details.setEndDate(dto.getEndDate());
        details.setTimesPerDay(dto.getTimesPerDay());
        return details;
    }

    private TargetGroup createTargetGroup(CampaignDTO dto) {
        TargetGroup targetGroup = new TargetGroup();
        targetGroup.setGenders(dto.getTargetedGenders());
        Set<AgeGroup> ageGroups = new HashSet<>();
        if(dto.getTargetedAges() != null) {
            for(long id : dto.getTargetedAges()) {
                AgeGroup ageGroup = ageGroupRepository.findById(id).orElse(null);
                if(ageGroup != null)
                    ageGroups.add(ageGroup);
            }
        }
        targetGroup.setAgeGroups(ageGroups);
        return targetGroup;
    }

    List<Campaign> getActive() {
        return campaignRepository.findAll().stream().filter(c -> c.started()).collect(Collectors.toList());
    }

    @Override
    public ReportDto getReport(long id) {
        Campaign campaign = campaignRepository.findById(id).orElse(null);
        if(campaign == null) return null;
        ReportDto report = new ReportDto();
        report.postReport = mediaService.getReport(campaign.getMediaId());
        report.timesPublished = campaign.getExposureDates().size();
        List<LinkClick> clicks = linkClickRepository.findAll().stream().filter(l -> l.getCampaign().getId() == campaign.getId()).collect(Collectors.toList());
        report.totalClicks = clicks.size();
        report.clicks = new ArrayList<>();
        Map<String, Integer> rclicks = new HashMap<>();
        clicks.forEach(l -> rclicks.put(l.getProfile(), 0));
        clicks.forEach(l -> rclicks.put(l.getProfile(), rclicks.get(l.getProfile()) + 1));
        rclicks.keySet().forEach(rc -> report.clicks.add(new ClicksByProfileDto(rc, rclicks.get(rc))));
        return report;
    }
}
