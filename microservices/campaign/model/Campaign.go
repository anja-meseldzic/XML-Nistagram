package model

import "github.com/google/uuid"

type Campaign struct{
	ID       uuid.UUID `json:"id"`
	ProfileID uuid.UUID `json:"profile-id"`
	PathToContent string   `json:"path-to-content" gorm:"not null"`
	ContentType TypeOfCampaignContent `json:"content-type" gorm:"not null"`
	TargetGroup []int64 `json:"target-group" gorm:"not null"`
}



