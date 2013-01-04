package de.rainu.boxmanng.exception;

public class WorldBuildException extends RuntimeException {

	public WorldBuildException() {
	}

	public WorldBuildException(String message) {
		super(message);
	}

	public WorldBuildException(Throwable cause) {
		super(cause);
	}

	public WorldBuildException(String message, Throwable cause) {
		super(message, cause);
	}

}
