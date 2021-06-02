package model

import "github.com/google/uuid"

type Followers struct {
	ID        uuid.UUID `json:"id"`
	Profile   Profile   `json:"profile"`
	ProfileID uuid.UUID `json:"blocked_by"`
	Followers []Profile `json:"followers" gorm:"foreignKey:ID"`
	Following []Profile `json:"following" gorm:"foreignKey:ID"`
}
