package gameplayer;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import gameEngine.Game;

public class GamePlayerGUI extends JFrame{
	
	public GamePlayerGUI(Game loadedGame){
		setLayout(new BorderLayout());
		setJMenuBar(new GamePlayerOptionsBar());
		add(new GamePlayerGame(loadedGame),BorderLayout.CENTER);
		
		pack();
		setVisible(true);
	}

}
