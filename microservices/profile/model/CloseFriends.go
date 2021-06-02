package model

import "github.com/google/uuid"

type CloseFriends struct {
	ID           uuid.UUID `json:"id"`
	Profile      Profile   `json:"profile"`
	ProfileID    uuid.UUID `json:"profile-id"`
	CloseFriends []Profile `json:"close-friends" gorm:"foreignKey:ID"`
}
