package repository

import (
	"gorm.io/gorm"
)

type MessageRepository struct {
	Database *gorm.DB
}
