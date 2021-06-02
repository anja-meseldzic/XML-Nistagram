package model

import "github.com/google/uuid"

type AgentRegistrationRequest struct {
	ID        uuid.UUID `json:"id"`
	ProfileID uuid.UUID
	Email     string `json:"email" gorm:"not-null"`
	Website   string `json:"website" gorm:"not-null"`
	Reviewed  bool   `json:"reviewed" gorm:"not-null"`
}
