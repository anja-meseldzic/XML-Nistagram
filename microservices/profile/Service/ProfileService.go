package service

import (
	"github.com/anja-meseldzic/XML-Nistagram/microservices/profile/repository"
)

type ProfileService struct {
	Repo *repository.ProfileRepository
}
