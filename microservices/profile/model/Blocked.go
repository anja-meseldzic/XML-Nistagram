package model

import "github.com/google/uuid"

type Blocked struct {
	ID               uuid.UUID `json:"id"`
	ProfileID        uuid.UUID `json:"blocked_by"`
	BlockedProfileID uuid.UUID `json:"blocked"`
}
