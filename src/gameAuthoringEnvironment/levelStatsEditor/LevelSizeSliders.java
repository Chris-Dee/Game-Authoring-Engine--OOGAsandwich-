package gameAuthoringEnvironment.levelStatsEditor;

import java.util.ArrayList;

import gameAuthoringEnvironment.frontEnd.LevelPanel;
import gameEngine.Level;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class LevelSizeSliders extends JPanel {
	Level level;
	JSlider height;
	JSlider width;
public LevelSizeSliders(Level l){
	level=l;
	makeSizePanel(this);
}
public void makeSizePanel(JPanel homePanel){
	JPanel panel=new JPanel();
	panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
	height=makeSizeSlider("Height",new int[]{10,200}, panel);
	width=makeSizeSlider("Width",new int[]{10,200},panel);
	homePanel.add(panel);
}
public JSlider makeSizeSlider(/*ChangeListener c,*/ String title, int[] range, JPanel homePanel){
	JPanel sliderPanel=new JPanel();
	sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));
	JLabel label=new JLabel(title);
	sliderPanel.add(label);
	JSlider slider=new JSlider(range[0], range[1]);
	slider.addChangeListener(new SliderListener());
	sliderPanel.add(slider);
	homePanel.add(sliderPanel);
	return slider;
}
public class SliderListener implements ChangeListener {
	
	//height slider is 1, width slider is 0;
	public SliderListener(){
	}
	@Override
	public void stateChanged(ChangeEvent arg0) {
		ArrayList<Integer> size=new ArrayList<Integer>();
		size.add(height.getValue());
		size.add(width.getValue());
		level.changeLevelSize(size);
	}
}
}
