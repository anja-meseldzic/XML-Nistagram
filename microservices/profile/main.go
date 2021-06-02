package main

import (
	"fmt"
	"log"
	"net/http"

	"github.com/anja-meseldzic/XML-Nistagram/microservices/profile/controller"
	db "github.com/anja-meseldzic/XML-Nistagram/microservices/profile/repository/database"

	"github.com/anja-meseldzic/XML-Nistagram/microservices/profile/repository"
	"github.com/anja-meseldzic/XML-Nistagram/microservices/profile/service"
	"github.com/gorilla/mux"
	"gorm.io/gorm"
)

func initRepo(database *gorm.DB) *repository.ProfileRepository {
	return &repository.ProfileRepository{Database: database}
}
func initServices(repo *repository.ProfileRepository) *service.ProfileService {
	return &service.ProfileService{Repo: repo}
}
func initController(service *service.ProfileService) *controller.ProfileController {
	return &controller.ProfileController{Service: service}
}
func registerCampaignEndpoints(controller *controller.ProfileController, router *mux.Router) {

}
func main() {
	fmt.Println("Profile service started")
	//jednom se inicijalizuje i injektuje u sve repozitorijume
	database := db.InitDB()
	//jednom se inicijalizuje i injektuje u svaku registraciju endpointa
	router := mux.NewRouter().StrictSlash(true)
	//poziva se za svaki repo, servis i kontroler
	profileRepo := initRepo(database)
	profileService := initServices(profileRepo)
	profileController := initController(profileService)
	registerCampaignEndpoints(profileController, router)
	startServer(router)
}
func startServer(router *mux.Router) {
	log.Fatal(http.ListenAndServe("localhost:8085", router))
}
