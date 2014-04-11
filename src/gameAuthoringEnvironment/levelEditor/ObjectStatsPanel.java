package gameAuthoringEnvironment.levelEditor;

import gameAuthoringEnvironment.levelEditor.LevelSizeSliders.SliderListener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
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

	private String objectName = "Player";
	private String movementName = "User-Controlled";

	private Integer movementSpeed = 0;
	private int movementDuration = 0;
	private int myCollisionID = 0;
	private int gravityMag = 0;
	private LevelEditor myEditor;
	private boolean isFloating = false;
	private boolean isCamera = false;

	/**
	 * Panel that will display the stats for the object that is being added to
	 * the level in the level editor environment
	 */
	public ObjectStatsPanel(ObjectEditorContainer container, LevelEditor editor) {
		panelSize = new Dimension(PANEL_WIDTH, container.HEIGHT);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		initialize();
		myEditor = editor;
		myEditor.setObjectStatsPanel(this);
	}

	private void initialize() {
		this.setPreferredSize(panelSize);
		createComboBoxes();
		createSliders();
		createCheckBoxes();
		setVisible(true);
	}

	public String getObjectName() {
		return objectName;
	}

	public String getMovementName() {
		return movementName;
	}
	
	public boolean getFloating() {
		return isFloating;
	}
	
	public int getCollisionID() {
		return myCollisionID;
	}

	public int getMovementSpeed() {
		return movementSpeed;
	}

	public int getMovementDuration() {
		return movementDuration;
	}

	public int getGravityMagnitude() {
		return gravityMag;
	}
	private void createCheckBoxes(){
		JPanel panel=new JPanel();
		final JCheckBox floater=new JCheckBox("Check if floating");
		//JCheckBox floater=new JCheckBox("Make gravity ");
		panel.add(floater);
		floater.setFocusable(false);
		floater.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isFloating = floater.isSelected();
			}
		});
		final JCheckBox cameraBox=new JCheckBox("Camera Toggle");
		cameraBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO code to make the camera follow that object
			}
		});
		panel.add(cameraBox);
		cameraBox.setFocusable(false);
		add(panel);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void createComboBoxes() {
		// Object Types will define things like how collisions are handled, and input
		// Will also determine what other options are available for user to define
		String[] objectTypes = {"Player", "Platform", "Enemy", "Goal", "Scenery"};
		final List<String> objectTypesList = Arrays.asList(objectTypes);
		JLabel type = new JLabel("Object Type");
		JComboBox objectType = new JComboBox(objectTypes);
		objectType.setFocusable(false);
		objectType.setPreferredSize(COMBO_SIZE);
		objectType.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				objectName = event.getItem().toString();
				myCollisionID = (int) Math.pow(2, objectTypesList.indexOf(objectName));
			}
		});
		
		// Movement type will only be available for Enemy and (in a limited sense)
		// moving platform.  Aggressive is a maybe, it would be cool to have
		// some 'smarter' AI that tracks down player (if there is time, of course)
		String[] movementTypes = {"User-Controlled", "Pace", "Vertical", "Random", "Jumping", "Aggressive", "Stationary"};
		JLabel movement = new JLabel("Movement Pattern");
		JComboBox movementType = new JComboBox(movementTypes);
		movementType.setFocusable(false);
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

	}

	private void createSliders() {
		initializeSlider("Movement Speed", movementSpeed, false);
		initializeSlider("Movement Duration", movementDuration, false);
		initializeSlider("Gravity Magnitude", gravityMag, true);
		
		/*final JSlider speed = new JSlider(0, 10);
		JLabel speedLabel = new JLabel("Movement Speed");
		speed.setLabelTable(speed.createStandardLabels(1, 0));
		speed.setPaintLabels(true);
		speed.setValue(0);
		speed.setFocusable(false);
		speed.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent event) {
				movementSpeed = speed.getValue();
			}
		});*/

		/*final JSlider movementLength = new JSlider(0, 10);
		JLabel movementLabel = new JLabel("Movement Duration");
		movementLength.setLabelTable(movementLength.createStandardLabels(1, 0));
		movementLength.setPaintLabels(true);
		movementLength.setValue(0);
		movementLength.setFocusable(false);
		movementLength.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent event) {
				movementDuration = movementLength.getValue();
			}
		});*/

		/*final JSlider gravityMagnitude = new JSlider(0, 10);
		JLabel gravityLabel = new JLabel("Gravity Magnitude");
		gravityMagnitude.setLabelTable(gravityMagnitude.createStandardLabels(1,
				0));
		gravityMagnitude.setPaintLabels(true);
		gravityMagnitude.setFocusable(false);
		gravityMagnitude.setValue(0);
		gravityMagnitude.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent event) {
				gravityMag = gravityMagnitude.getValue();
				myEditor.setGravity(gravityMag);
			}
		});*/

		/*add(speedLabel);
		add(speed);

		add(movementLabel);
		add(movementLength);

		add(gravityLabel);
		add(gravityMagnitude);*/

	}
	
	private void initializeSlider(String name, final int value, final boolean isGravity) {
		final JSlider slider = new JSlider(0,10);
		JLabel label = new JLabel(name);
		slider.setLabelTable(slider.createStandardLabels(1,0));
		slider.setPaintLabels(true);
		slider.setValue(0);
		slider.setFocusable(false);
		if(name.equals("Movement Speed")) {
			slider.addChangeListener(createSpeedListener(slider));
		}
		if(name.equals("Movement Duration")) {
			slider.addChangeListener(createDurationListener(slider));
		}
		if(name.equals("Gravity Magnitude")) {
			slider.addChangeListener(createGravityListener(slider));
		}
		
		add(label);
		add(slider);
	}
	
	private ChangeListener createGravityListener(final JSlider slider) {
		return new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				gravityMag = slider.getValue();
				myEditor.setGravity(gravityMag);
			}
		};
	}
	
	private ChangeListener createSpeedListener(final JSlider slider) {
		return new ChangeListener() {

			public void stateChanged(ChangeEvent event) {
				movementSpeed = slider.getValue();
			}
		};
	}
	
	private ChangeListener createDurationListener(final JSlider slider) {
		return new ChangeListener() {

			public void stateChanged(ChangeEvent event) {
				movementDuration = slider.getValue();
			}
		};
	}
}
