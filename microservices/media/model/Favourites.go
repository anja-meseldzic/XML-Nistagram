package model

import "github.com/google/uuid"

type Favourites struct {
	ID uuid.UUID `json:"id"`
	ProfileID uuid.UUID `json:"profile-id"`
	Post map[string][]Post `json:"post"`
}
