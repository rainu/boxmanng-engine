package de.rainu.boxmanng.exception;

public class RuleException extends GameException {

	public RuleException() {
	}

	public RuleException(String message) {
		super(message);
	}

	public RuleException(Throwable cause) {
		super(cause);
	}

	public RuleException(String message, Throwable cause) {
		super(message, cause);
	}

}
