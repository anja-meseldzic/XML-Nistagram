package database

import (
	"log"

	"github.com/anja-meseldzic/XML-Nistagram/microservices/media/model"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

func InitDB() *gorm.DB {
	dsn := "host=localhost user=postgres password=root dbname=media port=5432 sslmode=disable"
	database, err := gorm.Open(postgres.Open(dsn), &gorm.Config{})
	if err != nil {
		log.Fatal(err)
	}

	database.AutoMigrate(&model.Comment{}, &model.Favourites{}, &model.InappropriateContent{}, &model.Media{}, &model.Post{}, &model.Taggs{}, &model.Rating{}, &model.Story{})

	return database
}
