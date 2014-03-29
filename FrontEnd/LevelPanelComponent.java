package FrontEnd;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class LevelPanelComponent extends JPanel {
public LevelPanelComponent(Color c, String name) {
	setBackground(c);
	add(new JLabel(name));
	initialize();
}
public void initialize(){
	setSize(600, 300);
	
}
public String toString(){
	return "wwoooo";
	
}
}
