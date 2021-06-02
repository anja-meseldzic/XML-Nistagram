package repository

import (
	"gorm.io/gorm"
)

type ProfileRepository struct {
	Database *gorm.DB
}
