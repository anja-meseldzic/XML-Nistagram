package model

import "github.com/google/uuid"

type VerificationRequest struct {
	ID        uuid.UUID `json:"id"`
	ProfileID uuid.UUID
	Category  VerificationCategory
	FilePath  string `json:"file-path" gorm:"not-null"`
	Approved  bool   `json:"approved" gorm:"not-null"`
}
