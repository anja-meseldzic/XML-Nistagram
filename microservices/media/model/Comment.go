package model

import (
	"github.com/google/uuid"
	"time"
)

type Comment struct{
	ID uuid.UUID `json:"id"`
	ProfileID uuid.UUID `json:"profile-id"`
	Content string `json:"content" gorm:"not-null"`
	DateCreated time.Time `json:"date-created" gorm:"not-null"`
}
