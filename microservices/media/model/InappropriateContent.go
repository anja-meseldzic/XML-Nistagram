package model

import (
	"github.com/google/uuid"
)

type InappropriateContent struct {
	ID       uuid.UUID `json:"id"`
	Reviewed bool      `json:"reviewed" gorm:"not-null"`
	Med      Media     `json:"med" gorm:"foreignKey:ID"`
	MediaID  uuid.UUID
}
