package controller

import (
	"encoding/json"
	"fmt"
	"github.com/anja-meseldzic/XML-Nistagram/microservices/auth/model"
	"github.com/anja-meseldzic/XML-Nistagram/microservices/auth/service"
	"github.com/gorilla/mux"
	"net"
	"net/http"
)

type UserController struct {
	Service *service.UserService
}

func (controller *UserController) Hello(w http.ResponseWriter, r *http.Request) {
	addrs, _ := net.InterfaceAddrs()
	for i, addr := range addrs {
		fmt.Printf("%d %v\n", i, addr)
	}
}

func (controller *UserController) CreateUser(w http.ResponseWriter, r *http.Request) {
	fmt.Println("creating")
	var user model.User
	err := json.NewDecoder(r.Body).Decode(&user)
	if err != nil {
		w.WriteHeader(http.StatusBadRequest)
		return
	}
	fmt.Println(user)
	err = controller.Service.CreateUser(&user)
	if err != nil {
		fmt.Println(err)
		w.WriteHeader(http.StatusExpectationFailed)
	}
	w.WriteHeader(http.StatusCreated)
	w.Header().Set("Content-Type", "application/json")
}

func (controller *UserController) Verify(w http.ResponseWriter, r *http.Request) {
	fmt.Println("verifying")
	vars := mux.Vars(r)
	id := vars["userId"]
	if id == "" {
		w.WriteHeader(http.StatusBadRequest)
		return
	}
	exists, err := controller.Service.UserExists(id)
	if err != nil {
		w.WriteHeader(http.StatusBadRequest)
		return
	}
	if exists {
		w.WriteHeader(http.StatusOK)
	} else {
		w.WriteHeader(http.StatusNotFound)
	}
}
