package service

import (
	"github.com/anja-meseldzic/XML-Nistagram/microservices/media/repository"
)

type MediaService struct {
	Repo *repository.MediaRepository
}
