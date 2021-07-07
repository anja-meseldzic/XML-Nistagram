package app.campaign.dto;

import java.util.ArrayList;

public class InfluencerCampaignDTO {
	private ArrayList<String> influencers;
	private long campaignId;
	
	public InfluencerCampaignDTO() {
		super();
		
	}
	public InfluencerCampaignDTO(ArrayList<String> influencers, long campaignId) {
		super();
		this.influencers = influencers;
		this.campaignId = campaignId;
	}
	public ArrayList<String> getInfluencers() {
		return influencers;
	}
	public void setInfluencers(ArrayList<String> influencers) {
		this.influencers = influencers;
	}
	public long getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(long campaignId) {
		this.campaignId = campaignId;
	}
}
