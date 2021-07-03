package app.campaign.service.impl;

import app.campaign.dto.CampaignDTO;
import app.campaign.dto.DetailsDTO;
import app.campaign.model.AgeGroup;
import app.campaign.model.Campaign;
import app.campaign.model.RepeatedCampaignDetails;
import app.campaign.model.TargetGroup;
import app.campaign.repository.AgeGroupRepository;
import app.campaign.repository.CampaignRepository;
import app.campaign.service.CampaignService;
import app.campaign.service.MediaService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CampaignServiceImpl implements CampaignService {

    private CampaignRepository campaignRepository;
    private AgeGroupRepository ageGroupRepository;
    private MediaService mediaService;

    public CampaignServiceImpl(CampaignRepository campaignRepository, AgeGroupRepository ageGroupRepository, MediaService mediaService) {
        this.campaignRepository = campaignRepository;
        this.ageGroupRepository = ageGroupRepository;
        this.mediaService = mediaService;
    }

    @Override
    public void create(CampaignDTO dto, String agent) throws Exception {
        Campaign campaign = createCampaign(dto, agent);
        campaignRepository.save(campaign);
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
        if(campaign.active())
            return true;
        return false;
    }

    @Override
    public boolean isPartOfCampaign(long mediaId) {
        return campaignRepository.findAll().stream()
                .filter(c -> c.getMediaId() == mediaId)
                .findFirst().isPresent();
    }

    private Campaign createCampaign(CampaignDTO dto, String agent) throws Exception {
        if(!mediaService.exists(dto.getMediaId()))
            throw new Exception("Media content not created properly");

        Campaign campaign = new Campaign();

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
        for(long id : dto.getTargetedAges()) {
            AgeGroup ageGroup = ageGroupRepository.findById(id).orElse(null);
            if(ageGroup != null)
                ageGroups.add(ageGroup);
        }
        targetGroup.setAgeGroups(ageGroups);
        return targetGroup;
    }
}
