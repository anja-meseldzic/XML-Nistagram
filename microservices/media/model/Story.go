package model

import (
	"time"

	"github.com/google/uuid"
)

type Story struct {
	ID             uuid.UUID `json:"id"`
	DateCreated    time.Time `json:"date-created" gorm:"not-null"`
	CloseFriends   bool      `json:"close-friends" gorm:"not-null"`
	ExpiresInHours int       `json:"expires-in-hours" gorm:"not-null"`
	Media          Media     `json:"med" gorm:"foreignKey:ID"`
	MediaID        uuid.UUID
	IsHighlight    bool `json:"is-highlight" gorm:"not-null"`
}
