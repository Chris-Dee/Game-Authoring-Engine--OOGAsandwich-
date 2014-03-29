package FrontEnd;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import FrontEnd.LevelPanelComponent;

public class LevelPanel extends JPanel {
	private List<LevelPanelComponent> levelList=new ArrayList<LevelPanelComponent>();
public LevelPanel(){
	super();
	initialize();
}
public void initialize(){
	setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
	Dimension s=getToolkit().getScreenSize();
	setSize(s.width/2,s.height*4/5);
	setBackground(new Color(0,255,255));
	fillPanels();
}

public void makeLevelPanel(){
}
public void fillPanels(){
	this.removeAll();
	for(int i=0;i<levelList.size();i++){
		System.out.println(levelList);
		this.add(levelList.get(i));
	}
	for(int i=0;i<5-levelList.size();i++){
		this.add(new LevelPanelComponent(new Color(255,255,255),""+i));
	}
	this.revalidate();
	this.repaint();
}
public void addLevel(String name){
	levelList.add(new LevelPanelComponent(new Color(0,200,200),name));
	fillPanels();
}
}
