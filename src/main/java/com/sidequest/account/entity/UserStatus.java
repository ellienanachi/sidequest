package com.sidequest.account.entity;

public enum UserStatus {
	PENDING,   // registered, student ID not verified yet
	ACTIVE,
	SUSPENDED, // temporary, set by an admin
	BANNED
}
