package database

import (
	"log"

	"github.com/anja-meseldzic/XML-Nistagram/microservices/message/model"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

func InitDB() *gorm.DB {
	dsn := "host=localhost user=postgres password=root dbname=message port=5432 sslmode=disable"
	database, err := gorm.Open(postgres.Open(dsn), &gorm.Config{})
	if err != nil {
		log.Fatal(err)
	}

	database.AutoMigrate(&model.MediaMessage{}, &model.Message{}, &model.Notification{}, &model.OneTimeMessage{}, &model.TextMessage{})

	return database
}
