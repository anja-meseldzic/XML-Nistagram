package model

import (
	"github.com/google/uuid"
)

type Admin struct {
	ID       uuid.UUID `json:"id"`
	UserID uuid.UUID
	User User `json:"user" gorm:"constraint:onUpdate:CASCADE;"`
}
