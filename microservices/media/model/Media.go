package model

import (
	"github.com/google/uuid"
)

type Media struct{
	ID uuid.UUID `json:"id"`
	ProfileID uuid.UUID `json:"profile-id"`
	Path string `json:"path" gorm:"not-null"`

}
