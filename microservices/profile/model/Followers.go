package model

import "github.com/google/uuid"

type Followers struct {
	ID         uuid.UUID `json:"id"`
	ProfileID  uuid.UUID
	FollowerID uuid.UUID `json:"followers" gorm:"foreignKey:ProfileID"`
}
type Following struct {
	ID          uuid.UUID `json:"id"`
	ProfileID   uuid.UUID
	FollowingID uuid.UUID
}
