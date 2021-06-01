package controller

import (
	"github.com/anja-meseldzic/XML-Nistagram/microservices/message/service"
)

type MessageController struct {
	Service *service.MessageService
}
