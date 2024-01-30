package com.fiap_tech_challenge.parking_lot.controller.exception;

public class ControllerNotFoundException extends RuntimeException{

	public ControllerNotFoundException(String message){
		super(message);
	}
}
