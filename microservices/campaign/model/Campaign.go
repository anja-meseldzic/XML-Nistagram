package model

import "github.com/google/uuid"

type Campaign struct {
	ID            uuid.UUID             `json:"id"`
	ProfileID     uuid.UUID             `json:"profile-id"`
	PathToContent string                `json:"path-to-content"`
	ContentType   TypeOfCampaignContent `json:"content-type" `
	TargetGroup   []Targets             `json:"target-group" gorm:"foreignKey:ID"`
}

type Targets struct {
	ID     uuid.UUID `json:"id"`
	Target int       `json:"target"`
}
