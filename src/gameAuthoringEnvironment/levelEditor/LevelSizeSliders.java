package gameAuthoringEnvironment.levelEditor;

import java.util.List;
import java.util.ArrayList;

import gameAuthoringEnvironment.frontEnd.LevelPanel;
import gameEngine.Level;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jgame.JGPoint;


public class LevelSizeSliders extends JPanel {
	private static final int[] RANGE_FOR_HEIGHT_SLIDER = new int[] { 1, 20 };
	private static final int[] RANGE_FOR_WIDTH_SLIDER = new int[] { 1, 20 };
	Level myLevel;
	private JSlider height;
	private JSlider width;
	LevelEditor myLevelEditor;

	/**
	 * Component that makes sliding adjustment bars. Can be used to adjust integers.
	 * 
	 * @param level
	 * 			Level being edited
	 * @param levelEditor
	 * 			LevelEditor that the object exists in
	 */
	public LevelSizeSliders(Level level, LevelEditor levelEditor) {
		myLevel = level;
		myLevelEditor = levelEditor;
		makeSizePanel(this);
	}

	public void makeSizePanel(JPanel homePanel) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		height = makeSizeSlider("Height", RANGE_FOR_HEIGHT_SLIDER, panel);
		width = makeSizeSlider("Width", RANGE_FOR_WIDTH_SLIDER, panel);
		homePanel.add(panel);
		setSliderPositions(myLevel.getLevelSize());
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
		slider.setFocusable(false);
		return slider;
	}

	public void setSliderPositions(JGPoint size) {
		width.setValue(size.x);
		height.setValue(size.y);
	}

	public class SliderListener implements ChangeListener {
		public SliderListener() {
		}

		public void stateChanged(ChangeEvent arg0) {
			myLevel.changeLevelSize(new JGPoint(width.getValue(), height
					.getValue()));
			myLevelEditor.setPFSize(width.getValue(), height.getValue());
		}
	}
}
