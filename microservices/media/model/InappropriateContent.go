package model

import (
	"github.com/google/uuid"
)

type InappropriateContent struct {
	ID       uuid.UUID `json:"id"`
	Reviewed bool      `json:"reviewed" gorm:"not-null"`
	Media    Media     `json:"media" gorm:"foreignKey:ID"`
	MediaID  uuid.UUID
}
