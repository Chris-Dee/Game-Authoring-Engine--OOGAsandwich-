package gameengine.gameevents;

public class InvalidEventException extends Exception {
	public InvalidEventException() {}
	public InvalidEventException(String message) {
		super(message);
	}
}
