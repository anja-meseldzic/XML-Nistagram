package model

import (
	"github.com/google/uuid"
	"time"
)

type Story struct {
	ID uuid.UUID `json:"id"`
	DateCreated time.Time `json:"date-created" gorm:"not-null"`
	CloseFriends bool `json:"close-friends" gorm:"not-null"`
	ExpiresInHours int `json:"expires-in-hours" gorm:"not-null"`
	Media Media
	IsHighlight bool `json:"is-highlight" gorm:"not-null"`
}
