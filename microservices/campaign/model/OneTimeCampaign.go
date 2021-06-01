package model

import (
	"github.com/google/uuid"
	"time"
)

type OneTimeCampaign struct{
	ID uuid.UUID `json:"id"`
	Date time.Time `json:"date" gorm:"not null"`
	Campaign Campaign
}
