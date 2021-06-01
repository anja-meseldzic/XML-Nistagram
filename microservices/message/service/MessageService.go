package service

import (
	"github.com/anja-meseldzic/XML-Nistagram/microservices/message/repository"
)

type MessageService struct {
	Repo *repository.MessageRepository
}
