package model

import "github.com/google/uuid"

type Blocked struct {
	ID        uuid.UUID `json:"id"`
	Profile   Profile   `json:"profile"`
	ProfileID uuid.UUID `json:"profile-id"`
	Blocked   []Profile `json:"blocked" gorm:"foreignKey:ID"`
}
