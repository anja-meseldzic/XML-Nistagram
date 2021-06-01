package model

import (
	"github.com/google/uuid"
)

type Post struct {
	ID uuid.UUID `json:"id"`
	Location string `json:"close-friends"`
	Description string `json:"expires-in-hours"`
	Tag []string `json:"tag"`
	Comments []Comment `json:"comments"`
	Ratings []Rating `json:"ratings"`
	Media Media

}