package database

import (
	"log"

	"github.com/anja-meseldzic/XML-Nistagram/microservices/auth/model"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

func InitDB() *gorm.DB {
	dsn := "host=localhost user=postgres password=root dbname=auth port=5432 sslmode=disable"
	database, err := gorm.Open(postgres.Open(dsn), &gorm.Config{})
	if err != nil {
		log.Fatal(err)
	}
	database.AutoMigrate(&model.User{}, &model.RegularUser{}, &model.Admin{})

	/*Loading test data*/
	//users := []model.User{
	//	{ID: uuid.New(), Username: "petar", Password: "petar"},
	//	{ID: uuid.New(), Username: "ivan", Password: "ivan"},
	//}
	//for _, user := range users {
	//	database.Create(&user)
	//}
	return database
}
