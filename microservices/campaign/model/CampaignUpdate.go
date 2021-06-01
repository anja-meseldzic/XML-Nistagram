package model

import (
	"github.com/google/uuid"
	"time"
)

type CampaignUpdate struct{
	ID uuid.UUID `json:"id"`
	DateCreated time.Time `json:"date-created" gorm:"not null"`
	StartDate time.Time `json:"start-date" gorm:"not null"`
	EndDate time.Time `json:"end-date" gorm:"not null"`
	TimesPerDay int `json:"times-per-day" gorm:"not null"`
	Campaign MultipleTimeCampaign
	HoursUntilApply int `json:"hours-until-apply" gorm:"not null"`
}
