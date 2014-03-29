package FrontEnd;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class LevelPanelComponent extends JPanel {
public LevelPanelComponent(Color c, String name) {
	super();
	setBackground(c);
	add(new JLabel(name));
}
public String toString(){
	return "wwoooo";
	
}
}
