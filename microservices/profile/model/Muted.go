package model

import "github.com/google/uuid"

type Muted struct {
	ID uuid.UUID `json:"id"`
	Profile Profile
	Muted []Profile `json:"muted"`
}
