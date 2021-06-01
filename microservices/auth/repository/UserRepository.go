package repository

import (
	"fmt"
	"github.com/anja-meseldzic/XML-Nistagram/microservices/auth/model"
	"github.com/google/uuid"
	"gorm.io/gorm"
)

type UserRepository struct {
	Database *gorm.DB
}

func (repo *UserRepository) CreateUser(user *model.User) error {
	result := repo.Database.Create(user)
	fmt.Println(result.RowsAffected)
	return nil
}

func (repo *UserRepository) Register(user *model.RegularUser) error {
	repo.Database.Create(user)
	return nil
}

func (repo *UserRepository) UserExists(userId uuid.UUID) bool {
	var count int64
	repo.Database.Where("id = ?", userId).Find(&model.User{}).Count(&count)
	return count != 0
}
