package model

import "github.com/google/uuid"

type Followers struct {
	ID uuid.UUID `json:"id"`
	Profile Profile
	Followers []Profile `json:"followers"`
	Following []Profile `json:"following"`

}
