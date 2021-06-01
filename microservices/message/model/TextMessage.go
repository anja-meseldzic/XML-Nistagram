package model

import (
	"github.com/google/uuid"
)

type TextMessage struct {
	ID uuid.UUID `json:"id"`
	Content string `json:"content" gorm:"not-null"`
	Message Message
}
