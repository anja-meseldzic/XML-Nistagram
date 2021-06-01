package model

import "github.com/google/uuid"

type MediaMessage struct {
	ID           uuid.UUID `json:"id"`
	LinkToSource string    `json:"link-to-source" gorm:"not-null"`
	Messages     Message   `json:"messages" gorm:"foreignKey:ID"`
	MessageID    uuid.UUID
}
