package database

import (
	"github.com/anja-meseldzic/XML-Nistagram/microservices/auth/model"
	"github.com/google/uuid"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
	"log"
)

func InitDB() *gorm.DB {
	dsn := "host=localhost user=postgres password=root dbname=go port=5432 sslmode=disable"
	database, err := gorm.Open(postgres.Open(dsn), &gorm.Config{})
	if err != nil {
		log.Fatal(err)
	}
	database.AutoMigrate(&model.User{})

	/*Loading test data*/
	users := []model.User{
		{ID: uuid.New(), Username: "petar", Password: "petar"},
		{ID: uuid.New(), Username: "ivan", Password: "ivan"},
	}
	for _, user := range users {
		database.Create(&user)
	}
	return database
}