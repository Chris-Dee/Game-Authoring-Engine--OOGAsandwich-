package gameAuthoringEnvironment.levelStatsEditor;

import javax.swing.BoxLayout;
import javax.swing.*;
import javax.swing.event.ChangeListener;

public class LevelSizeSliders extends JPanel {
public LevelSizeSliders(/*Level level*/){
	makeSizePanel(this);
}
public void makeSizePanel(JPanel homePanel){
	makeSizeSlider("Height",new int[]{10,200}, homePanel);
	makeSizeSlider("Width",new int[]{10,200},homePanel);
}
public void makeSizeSlider(/*ChangeListener c,*/ String title, int[] range, JPanel homePanel){
	JPanel sliderPanel=new JPanel();
	sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));
	JLabel label=new JLabel(title);
	sliderPanel.add(label);
	JSlider slider=new JSlider(range[0], range[1]);
	sliderPanel.add(slider);
	homePanel.add(sliderPanel);
}
}
