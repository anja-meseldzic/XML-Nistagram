package model

import "github.com/google/uuid"

type Muted struct {
	ID        uuid.UUID `json:"id"`
	ProfileID uuid.UUID
	//Muted []Profile `json:"muted"`
}
