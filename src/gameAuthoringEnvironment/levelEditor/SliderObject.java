package gameAuthoringEnvironment.levelEditor;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

public class SliderObject extends JPanel {

	private String myLabel;
	// private int myValue;
	private JSlider mySlider;

	/**
	 * Creates a slider object. This can be used to adjust values on the fly
	 * through a user input. In order to add a change listener, user can invoke
	 * the normal .addChangeListener method
	 * 
	 * @param label
	 *            Label that will appear above the slider
	 * @param startingValue
	 *            Initial value of the slider
	 * @param range
	 *            Range that the slider will work between
	 */
	public SliderObject(String label, int startingValue, int[] range) {
		this.add(new JLabel(label));
		// myValue = startingValue;
		mySlider = new JSlider(range[0], range[1]);
		setValue(startingValue);
		initialize(this);
	}

	private JPanel initialize(JPanel homePanel) {
		homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.Y_AXIS));
		homePanel.add(sliderPanel());
		return homePanel;
	}

	private JPanel sliderPanel() {
		JPanel panel = new JPanel();

		mySlider.setLabelTable(mySlider.createStandardLabels(1, 0));
		mySlider.setPaintLabels(true);
		mySlider.setFocusable(false);
		panel.add(mySlider);

		return panel;
	}

	/**
	 * Returns current value of the slider
	 * 
	 * @return Value of the slider
	 */

	public int getValue() {
		return mySlider.getValue();
	}

	/**
	 * Sets the value of the slider
	 * 
	 * @param value
	 *            Value of the slider
	 */

	public void setValue(int value) {
		mySlider.setValue(value);
	}

	/**
	 * Add change listener to the slider of this object
	 * 
	 * @param listener
	 *            ChangeListener to be added
	 */

	public void addChangeListener(ChangeListener listener) {
		mySlider.addChangeListener(listener);
	}

	/**
	 * Sets whether or not the slider box is enabled
	 * 
	 * @param isEnabled
	 *            Is the slider enabled
	 */

	public void setEnabled(boolean isEnabled) {
		mySlider.setEnabled(isEnabled);
	}

	/**
	 * Sets whether or not the tick marks appear
	 * 
	 * @param isEnabled
	 *            Are paint marks enabled
	 */

	public void setPaintLabels(boolean isEnabled) {
		mySlider.setPaintLabels(isEnabled);
	}

	/**
	 * Gets the slider in this SliderObjects
	 * 
	 * @return Slider of this slider object
	 */

	public JSlider getSlider() {
		return mySlider;
	}
}
