package controller

import (
	"github.com/anja-meseldzic/XML-Nistagram/microservices/campaign/service"
)

type CampaignController struct {
	Service *service.CampaignService
}
