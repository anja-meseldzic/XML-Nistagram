package model

import "github.com/google/uuid"

type MediaMessage struct {
	ID uuid.UUID `json:"id"`
	LinkToSource string `json:"link-to-source" gorm:"not-null"`
	Message Message
}

