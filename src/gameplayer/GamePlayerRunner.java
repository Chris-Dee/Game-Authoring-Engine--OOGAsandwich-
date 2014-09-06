package gameplayer;

import gameengine.Game;
import gameplayer.examplegames.MarioGame;

import java.io.IOException;

import data.InvalidDataFileException;

public class GamePlayerRunner {
	public static void main(String[]args) throws IOException, InvalidDataFileException, ClassNotFoundException {
		new GamePlayerFrame(new MarioGame());//new MarioGame());//new MarioGame());
	}
}
