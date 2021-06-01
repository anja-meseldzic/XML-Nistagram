package model

type Role int
const(
	USER Role = iota
	ADMIN
	AGENT
)
