package model

import "github.com/google/uuid"

type InfluencerCampaignRequest struct {
	ID       uuid.UUID `json:"id"`
	ProfileID uuid.UUID `json:"profile-id"`
	Campaign Campaign
	Approved bool `json:"approved" gorm:"not null"`

}
