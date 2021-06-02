package model

import (
	"github.com/google/uuid"
)

type Post struct {
	ID       uuid.UUID `json:"id"`
	Location string    `json:"location"`
	//Tag         []Taggs   `json:"tag" gorm:"foreignKey:ID"`
	Description string `json:"description"`
	//Comments       []Comment `json:"comments" gorm:"foreignKey:ID"`
	MediaID uuid.UUID
	//Ratings []Rating `json:"ratings" gorm:"foreignKey:ID"`
	//Med         Media     `json:"med" gorm:"foreignKey:ID"`

}
type Taggs struct {
	ID     uuid.UUID `json:"id"`
	Tag    string    `json:"tag"`
	PostID uuid.UUID
}
