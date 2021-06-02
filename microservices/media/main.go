package main

import (
	"fmt"
	"log"
	"net/http"

	"github.com/anja-meseldzic/XML-Nistagram/microservices/media/controller"
	db "github.com/anja-meseldzic/XML-Nistagram/microservices/media/repository/database"

	"github.com/anja-meseldzic/XML-Nistagram/microservices/media/repository"
	"github.com/anja-meseldzic/XML-Nistagram/microservices/media/service"
	"github.com/gorilla/mux"
	"gorm.io/gorm"
)

func initRepo(database *gorm.DB) *repository.MediaRepository {
	return &repository.MediaRepository{Database: database}
}
func initServices(repo *repository.MediaRepository) *service.MediaService {
	return &service.MediaService{Repo: repo}
}
func initController(service *service.MediaService) *controller.MediaController {
	return &controller.MediaController{Service: service}
}
func registerCampaignEndpoints(controller *controller.MediaController, router *mux.Router) {

}
func main() {
	fmt.Println("Media service started")
	//jednom se inicijalizuje i injektuje u sve repozitorijume
	database := db.InitDB()
	//jednom se inicijalizuje i injektuje u svaku registraciju endpointa
	router := mux.NewRouter().StrictSlash(true)
	//poziva se za svaki repo, servis i kontroler
	mediaRepo := initRepo(database)
	mediaService := initServices(mediaRepo)
	mediaController := initController(mediaService)
	registerCampaignEndpoints(mediaController, router)
	startServer(router)
}
func startServer(router *mux.Router) {
	log.Fatal(http.ListenAndServe("localhost:8083", router))
}
