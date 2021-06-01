package main

import (
	"fmt"
	"log"
	"net/http"

	"github.com/anja-meseldzic/XML-Nistagram/microservices/message/controller"
	db "github.com/anja-meseldzic/XML-Nistagram/microservices/message/repository/database"

	"github.com/anja-meseldzic/XML-Nistagram/microservices/message/repository"
	"github.com/anja-meseldzic/XML-Nistagram/microservices/message/service"
	"github.com/gorilla/mux"
	"gorm.io/gorm"
)

func initRepo(database *gorm.DB) *repository.MessageRepository {
	return &repository.MessageRepository{Database: database}
}
func initServices(repo *repository.MessageRepository) *service.MessageService {
	return &service.MessageService{Repo: repo}
}
func initController(service *service.MessageService) *controller.MessageController {
	return &controller.MessageController{Service: service}
}
func registerCampaignEndpoints(controller *controller.MessageController, router *mux.Router) {

}
func main() {
	fmt.Println("Message service started")
	//jednom se inicijalizuje i injektuje u sve repozitorijume
	database := db.InitDB()
	//jednom se inicijalizuje i injektuje u svaku registraciju endpointa
	router := mux.NewRouter().StrictSlash(true)
	//poziva se za svaki repo, servis i kontroler
	messageRepo := initRepo(database)
	messageService := initServices(messageRepo)
	messageController := initController(messageService)
	registerCampaignEndpoints(messageController, router)
	startServer(router)
}
func startServer(router *mux.Router) {
	log.Fatal(http.ListenAndServe("localhost:8084", router))
}
