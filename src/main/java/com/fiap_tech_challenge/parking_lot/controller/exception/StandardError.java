package com.fiap_tech_challenge.parking_lot.controller.exception;

import java.time.Instant;

public class StandardError {
	private Instant timestamp;
	private Integer status;
	private String error;
	private String message;
	private String path;

	public StandardError(){}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "ProductStandardError{" +
				"timestamp=" + timestamp +
				", status=" + status +
				", error='" + error + '\'' +
				", message='" + message + '\'' +
				", path='" + path + '\'' +
				'}';
	}
}
