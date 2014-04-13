package gameEngine;

public class InvalidEventActionException extends Exception {
	public InvalidEventActionException(){}
	public InvalidEventActionException(String message){
		super(message);
	}
}
