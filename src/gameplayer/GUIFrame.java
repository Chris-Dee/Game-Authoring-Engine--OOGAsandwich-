package gameplayer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import jgame.JGPoint;
public class GUIFrame extends JFrame {
public GUIFrame(JGPoint size){
	super();
	JPanel mainFrame=(JPanel)getContentPane();
	mainFrame.add(new GamePlayerGUI(size));
	setVisible(true);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setSize(640,480);
	
	
}

}
