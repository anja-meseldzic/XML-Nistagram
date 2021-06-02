package model

import (
	"time"

	"github.com/google/uuid"
)

type Comment struct {
	ID          uuid.UUID `json:"id"`
	ProfileID   uuid.UUID `json:"profile-id"`
	Content     string    `json:"content" gorm:"not-null"`
	DateCreated time.Time `json:"date-created" gorm:"not-null"`
	PostID      uuid.UUID
}
