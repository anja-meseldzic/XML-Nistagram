package model

import (
	"github.com/google/uuid"
)

type Profile struct {
	ID            uuid.UUID `json:"id"`
	Verified      bool      `json:"verified" gorm:"not-null"`
	Active        bool      `json:"active" gorm:"not-null"`
	AllowTagging  bool      `json:"allow-tagging" gorm:"not-null"`
	AllowMessages bool      `json:"allow-messages" gorm:"not-null"`
	Private       bool      `json:"private" gorm:"not-null"`
	RegularUserID uuid.UUID `json:"regular-user-id"`
}
