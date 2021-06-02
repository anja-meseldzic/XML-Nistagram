package model

import "github.com/google/uuid"

type Rating struct {
	ID         uuid.UUID `json:"id"`
	ProfileID  uuid.UUID `json:"profile-id"`
	RatingType RatingType
	PostID     uuid.UUID
}
