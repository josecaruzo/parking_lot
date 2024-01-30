package com.fiap_tech_challenge.parking_lot.controller.exception;

public class DatabaseException extends RuntimeException{
	public DatabaseException(String msg){
		super(msg);
	}
}
