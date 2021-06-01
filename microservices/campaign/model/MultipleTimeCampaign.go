package model

import (
	"github.com/google/uuid"
	"time"
)

type MultipleTimeCampaign struct {
	ID uuid.UUID `json:"id"`
	StartDate time.Time `json:"start-date" gorm:"not null"`
	EndDate time.Time `json:"end-date" gorm:"not null"`
	TimesPerDay int `json:"times-per-day" gorm:"not null"`
	Campaign Campaign
}
