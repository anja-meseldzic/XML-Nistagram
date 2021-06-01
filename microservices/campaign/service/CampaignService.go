package service

import (
	"github.com/anja-meseldzic/XML-Nistagram/microservices/campaign/repository"
)

type CampaignService struct {
	Repo *repository.CampaignRepository
}
