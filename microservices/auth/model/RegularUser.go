package model

import (
	"github.com/google/uuid"
	"time"
)

type RegularUser struct {
	ID       uuid.UUID `json:"id"`
	Name string    `json:"name" gorm:"not null"`
	LastName string    `json:"last-name" gorm:"not null"`
	Email string    `json:"email" gorm:"unique;not null"`
	PhoneNumber string    `json:"phone-number" gorm:"not null"`
	DateOfBirth time.Time    `json:"date-of-birth" gorm:"not null"`
	Website string    `json:"website" gorm:"not null"`
	Biography string    `json:"biography" gorm:"not null"`
	Gender Gender `json:"gender"`
	UserID uuid.UUID
	User User `json:"user" gorm:"constraint:onUpdate:CASCADE;"`
}
