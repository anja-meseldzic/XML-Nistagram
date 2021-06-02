package controller

import (
	"github.com/anja-meseldzic/XML-Nistagram/microservices/profile/service"
)

type ProfileController struct {
	Service *service.ProfileService
}
