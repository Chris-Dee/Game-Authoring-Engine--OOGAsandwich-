package gameAuthoringEnvironment.levelEditor;



import gameAuthoringEnvironment.levelEditor.LevelSizeSliders.SliderListener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


@SuppressWarnings("serial")
public class ObjectStatsPanel extends JPanel {
	private Dimension panelSize;
	private JLabel spacer;
	private static final int PANEL_WIDTH = 250;
	private static final Dimension SPACER_SIZE = new Dimension(PANEL_WIDTH, 50);
	private static final Dimension COMBO_SIZE = new Dimension(PANEL_WIDTH, 30);
	
	public String objectName = "";
	public String movementName = "";
	
	public Integer movementSpeed = 5;
	public int movementDuration = 5;
	
	
	/**
	 * Panel that will display the stats for the object that is being 
	 * added to the level in the level editor environment
	 */
	public ObjectStatsPanel(ObjectEditorContainer container) {
		panelSize = new Dimension(PANEL_WIDTH, container.HEIGHT);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		initialize();
	}
	
	private void initialize() {
		this.setPreferredSize(panelSize);
		createSpacer();
		createComboBoxes();
		setVisible(true);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void createComboBoxes() {
		// Object Types will define things like how collisions are handled, and input
		// Will also determine what other options are available for user to define
		String[] objectTypes = {"Player","Enemy", "Block", "Moving Platform", "Goal", "Scenery"};
		JLabel type = new JLabel("Object Type");
		JComboBox objectType = new JComboBox(objectTypes);
		objectType.setPreferredSize(COMBO_SIZE);
		objectType.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent event) {
				objectName = event.getItem().toString();
			}
		});
		
		// Movement type will only be available for Enemy and (in a limited sense)
		// moving platform.  Aggressive is a maybe, it would be cool to have
		// some 'smarter' AI that tracks down player (if there is time, of course)
		String[] movementTypes = {"Pace", "Vertical", "Random", "Jumping", "Aggressive"};
		JLabel movement = new JLabel("Movement Pattern");
		JComboBox movementType = new JComboBox(movementTypes);
		movementType.setPreferredSize(COMBO_SIZE);
		movementType.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent event) {
				movementName = event.getItem().toString();
			}
			
		});
		

		add(type);
		add(objectType);

		add(movement);
		add(movementType);
		

		final JSlider speed = new JSlider(0,10);
		JLabel speedLabel = new JLabel("Movement Speed");
		speed.setLabelTable(speed.createStandardLabels(1, 0));
		speed.setPaintLabels(true);
		speed.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent event) {
				movementSpeed = speed.getValue();
			}	
		});
		

		final JSlider movementLength = new JSlider(0,10);
		JLabel movementLabel = new JLabel("Movement Duration");
		movementLength.setLabelTable(movementLength.createStandardLabels(1, 0));
		movementLength.setPaintLabels(true);
		movementLength.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent event) {
				movementDuration = movementLength.getValue();
			}
		});
		
		add(speedLabel);
		add(speed);

		
		add(movementLabel);
		add(movementLength);

		
		
	}
	
	/*public void makeSizePanel(JPanel homePanel) {
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
	}*/
	
	private void createSpacer() {
		spacer = new JLabel("");
		spacer.setPreferredSize(SPACER_SIZE);
		spacer.setVisible(true);
	}

}
