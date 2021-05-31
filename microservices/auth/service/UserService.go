package service

import (
	"github.com/anja-meseldzic/XML-Nistagram/microservices/auth/model"
	"github.com/anja-meseldzic/XML-Nistagram/microservices/auth/repository"
	"github.com/google/uuid"
)

type UserService struct {
	Repo *repository.UserRepository
}

func (service *UserService) CreateUser(user *model.User) error {
	service.Repo.CreateUser(user)
	return nil
}

func (service *UserService) UserExists(userId string) (bool, error) {
	id, err := uuid.Parse(userId)
	if err != nil {
		print(err)
		return false, err
	}
	exists := service.Repo.UserExists(id)
	return exists, nil
}