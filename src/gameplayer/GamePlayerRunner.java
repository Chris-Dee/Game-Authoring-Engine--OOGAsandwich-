package gameplayer;

import jgame.JGPoint;

public class GamePlayerRunner {
	/*
	 * Eventually the following statistics should be defined by 
	 * Game Author.
	 */
	private static JGPoint gameScreenSize = new JGPoint(640,480);
	
	public static void main(String[]args) {
		new GUIFrame(gameScreenSize);
	}
}
