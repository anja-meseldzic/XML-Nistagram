package model

import "github.com/google/uuid"

type OneTimeMessage struct {
	ID           uuid.UUID `json:"id"`
	LinkToSource string    `json:"link-to-source" gorm:"not-null"`
	//Messag       Message   `json:"messag" gorm:"foreignKey:ID"`
	MessageID uuid.UUID
	Seen      bool `json:"seen" gorm:"not-null"`
}
