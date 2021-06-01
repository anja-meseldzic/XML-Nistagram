package model

import "github.com/google/uuid"

type OneTimeMessage struct {
	ID uuid.UUID `json:"id"`
	LinkToSource string `json:"link-to-source" gorm:"not-null"`
	Message Message
	Seen bool `json:"seen" gorm:"not-null"`
}


