package de.rainu.boxmanng.exception;

public class GameException extends RuntimeException {

	public GameException() {
	}

	public GameException(String message) {
		super(message);
	}

	public GameException(Throwable cause) {
		super(cause);
	}

	public GameException(String message, Throwable cause) {
		super(message, cause);
	}

}
