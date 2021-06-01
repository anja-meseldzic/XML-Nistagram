package model

import (
	"github.com/google/uuid"
)

type User struct {
	ID       uuid.UUID `json:"id"`
	Username string    `json:"username" gorm:"unique;not null"`
	Password string    `json:"password" gorm:"not null"`
	Role Role
}