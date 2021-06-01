package model

import "github.com/google/uuid"

type CloseFriends struct {
	ID uuid.UUID `json:"id"`
	Profile Profile
	CloseFriends []Profile `json:"close-friends"`
}
