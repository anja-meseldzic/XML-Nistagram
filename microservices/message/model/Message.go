package model

import (
	"github.com/google/uuid"
	"time"
)

type Message struct {
	ID uuid.UUID `json:"id"`
	Sender string `json:"sender" gorm:"not-null"`
	Receiver string `json:"receiver" gorm:"not-null"`
	Created time.Time `json:"created" gorm:"not-null"`
}
