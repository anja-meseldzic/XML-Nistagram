package repository

import (
	"gorm.io/gorm"
)

type MediaRepository struct {
	Database *gorm.DB
}
