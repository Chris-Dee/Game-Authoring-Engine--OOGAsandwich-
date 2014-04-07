package gameplayer;

import java.io.IOException;

import data.InvalidDataFileException;
import jgame.JGPoint;

public class GamePlayerRunner {
	/*
	 * Eventually the following statistics should be defined by 
	 * Game Author.
	 */
	
	public static void main(String[]args) throws IOException, InvalidDataFileException {
		new GamePlayerGUI(new GravityTestGame());
	}
}