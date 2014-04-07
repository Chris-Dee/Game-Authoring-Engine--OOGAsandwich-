package gameplayer;

import java.io.IOException;

import data.InvalidDataFileException;

public class GamePlayerRunner {
	public static void main(String[]args) throws IOException, InvalidDataFileException, ClassNotFoundException {
		new GamePlayerGUI(new MarioGame());
	}
}