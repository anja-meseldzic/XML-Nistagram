package main

import (
	"github.com/anja-meseldzic/XML-Nistagram/microservices/auth/controller"
	"github.com/anja-meseldzic/XML-Nistagram/microservices/auth/repository"
	db "github.com/anja-meseldzic/XML-Nistagram/microservices/auth/repository/database"
	"github.com/anja-meseldzic/XML-Nistagram/microservices/auth/service"
	"github.com/gorilla/mux"
	"gorm.io/gorm"
	"log"
	"net/http"
)

func initRepo(database *gorm.DB) *repository.UserRepository {
	return &repository.UserRepository{Database: database}
}

func initServices(repo *repository.UserRepository) *service.UserService {
	return &service.UserService{Repo: repo}
}

func initController(service *service.UserService) *controller.UserController {
	return &controller.UserController{Service: service}
}
func registerUserEndpoints(controller *controller.UserController, router *mux.Router) {
	router.HandleFunc("/", controller.Hello).Methods("GET")
	router.HandleFunc("/", controller.CreateUser).Methods("POST")
	router.HandleFunc("/verify/{userId}", controller.Verify).Methods("GET")
}

func startServer(router *mux.Router) {
	log.Fatal(http.ListenAndServe("localhost:8080", router))
}

func main() {
	//jednom se inicijalizuje i injektuje u sve repozitorijume
	database := db.InitDB()
	//jednom se inicijalizuje i injektuje u svaku registraciju endpointa
	router := mux.NewRouter().StrictSlash(true)

	//poziva se za svaki repo, servis i kontroler
	userRepo := initRepo(database)
	userService := initServices(userRepo)
	userController := initController(userService)
	registerUserEndpoints(userController, router)

	startServer(router)
}