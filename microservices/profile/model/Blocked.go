package model

import "github.com/google/uuid"

type Blocked struct {
	ID uuid.UUID `json:"id"`
	Profile Profile
	Blocked []Profile `json:"blocked"`
}
