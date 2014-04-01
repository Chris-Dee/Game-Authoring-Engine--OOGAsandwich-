package FrontEnd;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class LevelEditorWindow extends JFrame {
	LevelPanelComponent currentLevel;
LevelEditorWindow(LevelPanelComponent level){
	currentLevel=level;
	initialize();
}

private void initialize() {
	setVisible(true);
	setDefaultCloseOperation(HIDE_ON_CLOSE);
	setTitle("Level Editor"/*+"level.title"*/);
	setLayout(new BorderLayout());
	setExitAction();
	makeMainPanel();
}
public void setExitAction(){
	 addWindowListener(new WindowAdapter()
     {
         @Override
         public void windowClosing(WindowEvent e)
         {
            currentLevel.setActive(true);
             e.getWindow().dispose();
         }
     });
}

	

private void makeMainPanel(){
	JPanel mainPanel=(JPanel) getContentPane();
	mainPanel.add(new LevelEditor(),BorderLayout.CENTER);
	mainPanel.add(new PresetsBar(),BorderLayout.EAST);
	mainPanel.add(new OptionsPanel(),BorderLayout.NORTH);
	pack();
}
}
