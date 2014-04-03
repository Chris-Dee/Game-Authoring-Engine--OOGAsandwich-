package LevelEditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import FrontEnd.LevelPanelComponent;
import FrontEnd.OptionsPanel;
import FrontEnd.PresetsBar;

public class LevelEditorWindow extends JFrame {
	private LevelPanelComponent currentLevel;
	private LevelEditor levelEdit;
public LevelEditorWindow(LevelPanelComponent level){
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
        	 levelEdit.destroy();
            currentLevel.setActive(true);
             e.getWindow().dispose();
         }
     });
}

	

private void makeMainPanel(){
	JPanel mainPanel=(JPanel) getContentPane();
	levelEdit=new LevelEditor();
	mainPanel.add(levelEdit,BorderLayout.CENTER);
	
	mainPanel.add(new PresetsBar(),BorderLayout.EAST);
	mainPanel.add(new OptionsPanel(),BorderLayout.NORTH);
	pack();
}
}
