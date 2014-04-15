package gameplayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class GamePlayerOptionsBar extends JMenuBar{
	
	/**
	 * Makes JMenuBar at top of the game player.
	 */
	public GamePlayerOptionsBar(){
		initialize();
	}
	
	private void initialize(){
		JMenu file = new JMenu("File");
		JMenuItem startItem = new JMenuItem("Play game");
		startItem.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Implement action
				
			}
			
		});
		
		file.add(startItem);
		
		add(file);
		setVisible(true);
	}

}
