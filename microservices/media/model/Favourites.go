package model

import "github.com/google/uuid"

type Favourites struct {
	ID             uuid.UUID `json:"id"`
	ProfileID      uuid.UUID `json:"profile-id"`
	Post           []Post    `json:"post" gorm:"foreignKey:ID"`
	CollectionName string    `json:"collection-name"`
}
