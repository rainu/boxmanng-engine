package de.rainu.boxmanng.exception;

public class WorldAccessException extends RuntimeException {

	public WorldAccessException() {
	}

	public WorldAccessException(String message) {
		super(message);
	}

	public WorldAccessException(Throwable cause) {
		super(cause);
	}

	public WorldAccessException(String message, Throwable cause) {
		super(message, cause);
	}

}
