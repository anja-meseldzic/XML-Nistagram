package database

import (
	"log"

	"github.com/anja-meseldzic/XML-Nistagram/microservices/profile/model"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

func InitDB() *gorm.DB {
	dsn := "host=localhost user=postgres password=root dbname=profile port=5432 sslmode=disable"
	database, err := gorm.Open(postgres.Open(dsn), &gorm.Config{})
	if err != nil {
		log.Fatal(err)
	}

	database.AutoMigrate(&model.AgentRegistrationRequest{}, &model.Blocked{}, &model.CloseFriends{}, &model.Followers{}, &model.Muted{}, &model.Profile{}, &model.VerificationRequest{})

	return database
}
