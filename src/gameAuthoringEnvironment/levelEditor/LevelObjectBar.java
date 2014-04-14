package gameAuthoringEnvironment.levelEditor;

import java.awt.Dimension;

import gameEngine.Level;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jgame.JGPoint;

/**
 * 
 * 
 * 
 */
public class LevelObjectBar extends JPanel {
	
	private Level myLevel;
	private LevelEditor myLevelEditor;
	private JSlider myWidthSlider;
	private JSlider myHeightSlider;
	/**
	 * Panel that displays all parameters that can be modified about the current
	 * level.
	 * 
	 * @param level
	 * 			Level being edited
	 * @param levelEditor
	 * 			LevelEditor that this object exists in
	 */
	public LevelObjectBar(Level level, LevelEditor levelEditor) {
		myLevel = level;
		myLevelEditor = levelEditor;
		this.add(addLevelSliders());
	}


	private JPanel addLevelSliders(){
		JPanel sliderPanel = new JPanel();
		sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));
		
		int[] sliderRange = {0,20};
		//this.add(new SliderObject("Width", 6, sliderRange));
		SliderObject widthSlider = new SliderObject("Width",6,sliderRange);
		myWidthSlider = widthSlider.getSlider();
		myWidthSlider.setPaintLabels(false);
		myWidthSlider.setMinimum(1);
		widthSlider.addChangeListener(new LevelSizeListener());
		
		SliderObject heightSlider = new SliderObject("Height",2,sliderRange);
		myHeightSlider = heightSlider.getSlider();
		myHeightSlider.setPaintLabels(false);
		myHeightSlider.setMinimum(1);
		heightSlider.addChangeListener(new LevelSizeListener());
		
		sliderPanel.add(widthSlider);
		sliderPanel.add(heightSlider);
		
		return sliderPanel;
	}
	
	public class LevelSizeListener implements ChangeListener {
		public LevelSizeListener() {
		}

		public void stateChanged(ChangeEvent arg0) {
			myLevel.changeLevelSize(new JGPoint(myWidthSlider.getValue(), myHeightSlider
					.getValue()));
			myLevelEditor.setPFSize(myWidthSlider.getValue(), myHeightSlider.getValue());
		}
	}
}