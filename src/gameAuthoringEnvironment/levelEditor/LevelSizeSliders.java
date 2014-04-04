package gameAuthoringEnvironment.levelEditor;

import java.util.List;
import java.util.ArrayList;

import gameAuthoringEnvironment.frontEnd.LevelPanel;
import gameEngine.Level;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jgame.JGPoint;

/**
 * Component that makes sliding adjustment bars. Can be used to adjust integers.
 * 
 * 
 */
public class LevelSizeSliders extends JPanel {
	Level level;
	private JSlider height;
	private JSlider width;
	LevelEditor levelEdit;

	public LevelSizeSliders(Level l, LevelEditor editor) {
		level = l;
		levelEdit = editor;
		makeSizePanel(this);
	}

	public void makeSizePanel(JPanel homePanel) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		height = makeSizeSlider("Width", new int[] { 1, 20 }, panel);
		width = makeSizeSlider("Height", new int[] { 1, 20 }, panel);
		homePanel.add(panel);
		setSliderPositions(level.getLevelSize());
	}

	public JSlider makeSizeSlider(String title, int[] range, JPanel homePanel) {
		JPanel sliderPanel = new JPanel();
		sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));
		JLabel label = new JLabel(title);
		sliderPanel.add(label);
		JSlider slider = new JSlider(range[0], range[1]);
		slider.addChangeListener(new SliderListener());
		sliderPanel.add(slider);
		homePanel.add(sliderPanel);
		return slider;
	}

	public void setSliderPositions(JGPoint size) {
		height.setValue(size.x);
		width.setValue(size.y);
	}

	public class SliderListener implements ChangeListener {
		public SliderListener() {
		}

		@Override
		public void stateChanged(ChangeEvent arg0) {
			level.changeLevelSize(new JGPoint(width.getValue(), height
					.getValue()));
			levelEdit.setPFSize(height.getValue(), width.getValue());
		}
	}
}
