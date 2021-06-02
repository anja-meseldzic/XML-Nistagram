package model

import "github.com/google/uuid"

type CloseFriends struct {
	ID            uuid.UUID `json:"id"`
	ProfileID     uuid.UUID
	CloseFriendID uuid.UUID
}
