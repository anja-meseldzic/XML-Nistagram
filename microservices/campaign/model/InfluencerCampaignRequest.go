package model

import "github.com/google/uuid"

type InfluencerCampaignRequest struct {
	ID         uuid.UUID `json:"id"`
	ProfileID  uuid.UUID `json:"profile-id"`
	Camp       Campaign  `json:"camp" gorm:"foreignKey:ID"`
	CampaignID uuid.UUID
	Approved   bool `json:"approved" gorm:"not null"`
}
