package model

import (
	"github.com/google/uuid"
)

type Admin struct {
	ID       uuid.UUID `json:"id"`
	User User
}
