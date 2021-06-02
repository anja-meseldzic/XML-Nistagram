package model

import "github.com/google/uuid"

type Muted struct {
	ID        uuid.UUID `json:"id"`
	Profile   Profile   `json:"profile"`
	ProfileID uuid.UUID
	Muted     []Profile `json:"muted" gorm:"foreignKey:ID"`
}
