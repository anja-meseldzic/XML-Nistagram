package model

import (
	"time"

	"github.com/google/uuid"
)

type OneTimeCampaign struct {
	ID   uuid.UUID `json:"id"`
	Date time.Time `json:"date" gorm:"not null"`
	//Camp       Campaign  `json:"camp" gorm:"foreignKey:ID"`
	CampaignID uuid.UUID
}
