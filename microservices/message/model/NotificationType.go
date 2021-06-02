package model

type NotificationType int

const (
	NEW_FOLLOWING NotificationType = iota
	MESSAGE
	RATING
	COMMENT
)
