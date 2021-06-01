package model

type NotificationType string
const(
	NEW_FOLLOWING = iota
	MESSAGE
	RATING
	COMMENT
)
