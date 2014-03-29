package FrontEnd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class ObjectPanel extends JPanel {
	LevelPanel levels;
public ObjectPanel(LevelPanel l){
	levels=l;
	makeMainFrame();
}
private void makeMainFrame(){
	makeLevelButton(this);
}
private void makeLevelButton(JPanel panel){
	// TODO add properties file
	JButton level=new JButton("Add Level");
	level.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e)
		{   
			levels.addLevel("LEVEL!!!");
		}
	});   
	panel.add(level);
}

}
