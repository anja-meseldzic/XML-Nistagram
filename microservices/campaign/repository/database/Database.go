package database

import (
	"log"

	"github.com/anja-meseldzic/XML-Nistagram/microservices/campaign/model"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

func InitDB() *gorm.DB {
	dsn := "host=localhost user=postgres password=root dbname=campaign port=5432 sslmode=disable"
	database, err := gorm.Open(postgres.Open(dsn), &gorm.Config{})
	if err != nil {
		log.Fatal(err)
	}

	database.AutoMigrate(&model.Campaign{}, &model.Targets{}, &model.InfluencerCampaignRequest{}, &model.CampaignUpdate{}, &model.OneTimeCampaign{}, &model.MultipleTimeCampaign{})

	return database
}
