package de.rainu.boxmanng.exception;

public class NotValidException extends GameException {

	public NotValidException() {
	}

	public NotValidException(String message) {
		super(message);
	}

	public NotValidException(Throwable cause) {
		super(cause);
	}

	public NotValidException(String message, Throwable cause) {
		super(message, cause);
	}

}
