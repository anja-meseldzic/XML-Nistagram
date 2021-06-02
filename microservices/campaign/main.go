package main

import (
	"fmt"
	"log"
	"net/http"

	"github.com/anja-meseldzic/XML-Nistagram/microservices/campaign/controller"
	db "github.com/anja-meseldzic/XML-Nistagram/microservices/campaign/repository/database"

	"github.com/anja-meseldzic/XML-Nistagram/microservices/campaign/repository"
	"github.com/anja-meseldzic/XML-Nistagram/microservices/campaign/service"
	"github.com/gorilla/mux"
	"gorm.io/gorm"
)

func initRepo(database *gorm.DB) *repository.CampaignRepository {
	return &repository.CampaignRepository{Database: database}
}
func initServices(repo *repository.CampaignRepository) *service.CampaignService {
	return &service.CampaignService{Repo: repo}
}
func initController(service *service.CampaignService) *controller.CampaignController {
	return &controller.CampaignController{Service: service}
}
func registerCampaignEndpoints(controller *controller.CampaignController, router *mux.Router) {

}
func main() {
	fmt.Println("Campaign service started")
	//jednom se inicijalizuje i injektuje u sve repozitorijume
	database := db.InitDB()
	//jednom se inicijalizuje i injektuje u svaku registraciju endpointa
	router := mux.NewRouter().StrictSlash(true)
	//poziva se za svaki repo, servis i kontroler
	campaignRepo := initRepo(database)
	campaignService := initServices(campaignRepo)
	campaignController := initController(campaignService)
	registerCampaignEndpoints(campaignController, router)
	startServer(router)
}
func startServer(router *mux.Router) {
	log.Fatal(http.ListenAndServe("localhost:8082", router))
}
