package app.campaign.service.impl;

import app.campaign.dto.CampaignDTO;
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
import java.util.HashSet;
import java.util.Set;

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
    public void Create(CampaignDTO dto, String agent) throws Exception {
        Campaign campaign = createCampaign(dto, agent);
        campaignRepository.save(campaign);
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

        RepeatedCampaignDetails details = createDetails(dto);
        if(details != null) {
            LocalDateTime time = campaign.getStart();
            time.with(LocalTime.MIDNIGHT);
            campaign.setStart(time);
        }
        campaign.addDetails(details);

        return campaign;
    }

    private RepeatedCampaignDetails createDetails(CampaignDTO dto) throws Exception {
        if(dto.getDetails() == null)
            return null;
        RepeatedCampaignDetails details = new RepeatedCampaignDetails();
        details.setCreated(LocalDateTime.now());
        details.setEndDate(dto.getDetails().getEndDate());
        details.setTimesPerDay(dto.getDetails().getTimesPerDay());
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
