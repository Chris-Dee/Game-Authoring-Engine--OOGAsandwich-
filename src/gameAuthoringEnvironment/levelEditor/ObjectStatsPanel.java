package gameAuthoringEnvironment.levelEditor;

import gameEngine.GameObject;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	// Map of object name to its parameters
	private String[] objectTypes = { "Player", "Platform", "Enemy", "Goal",
			"Scenery" };
	private JLabel spacer;
	private static final int PANEL_WIDTH = 250;
	private static final Dimension COMBO_SIZE = new Dimension(PANEL_WIDTH, 30);
	private ObjectToolbar imageButtons;

	private int myCollisionID = 0;
	private LevelEditor myEditor;
	private JPanel homePanel = new JPanel();
	private JSlider mySpeedSlider;
	private JSlider myDurationSlider;
	private JSlider myGravityMagnitudeSlider;
	private JComboBox objectType;
	private JComboBox movementType;
	JCheckBox floaterBox;
	JCheckBox cameraBox;

	/**
	 * Panel that will display the stats for the object that is being added to
	 * the level in the level editor environment
	 */
	public ObjectStatsPanel(ObjectEditorContainer container, LevelEditor editor) {
		panelSize = new Dimension(PANEL_WIDTH, container.HEIGHT);
		setLayout(new BorderLayout());
		homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.Y_AXIS));
		initialize();
		myEditor = editor;
		myEditor.setObjectStatsPanel(this);
		add(new ObjectToolbar(myEditor), BorderLayout.WEST);
		add(homePanel, BorderLayout.EAST);

	}

	private void initialize() {
		// this.setPreferredSize(panelSize);
		createComboBoxes();
		createSliders();
		createCheckBoxes();
		setVisible(true);
	}

	/*
	 * public ObjectStats exportStats() { return new ObjectStats(objectName,
	 * myCollisionID, movementName, movementSpeed, movementDuration, gravityMag,
	 * cameraBox.isSelected(), myEditor.getSelectedImageName(), isFloating); }
	 */

	public void setStats(ObjectStats objectStats) {

		if (objectStats != null) {
			// if (objectStats.mySpeed != null)
			mySpeedSlider.setValue(objectStats.mySpeed);
			// if (objectStats.myDuration != null)
			myDurationSlider.setValue(objectStats.myDuration);
			myGravityMagnitudeSlider.setValue(objectStats.myGravMag);
			objectType.setSelectedItem(objectStats.myColType);
			movementType.setSelectedItem(objectStats.myMovementPattern);
			floaterBox.setSelected(objectStats.isFloating);
			cameraBox.setSelected(objectStats.isCameraFollow);
			setSliderEnable();
		}
	}

	private void setSliderEnable() {
		if (movementType.getSelectedItem() == "User-Controlled"
				|| movementType.getSelectedItem() == "Stationary") {
			mySpeedSlider.setEnabled(false);
			myDurationSlider.setEnabled(false);
		} else {
			mySpeedSlider.setEnabled(true);
			myDurationSlider.setEnabled(true);
		}
	}

	public String getObjectName() {
		return (String) objectType.getSelectedItem();
	}

	public String getMovementName() {
		return (String) movementType.getSelectedItem();
	}

	public boolean getFloating() {
		return floaterBox.isSelected();
	}

	public int getCollisionID() {
		return myCollisionID;
	}

	public int getMovementSpeed() {
		return mySpeedSlider.getValue();
	}

	public int getMovementDuration() {
		return myDurationSlider.getValue();
	}

	public int getGravityMagnitude() {
		return myGravityMagnitudeSlider.getValue();
	}

	private void createCheckBoxes() {
		JPanel panel = new JPanel();
		floaterBox = new JCheckBox("Check if floating");
		panel.add(floaterBox);
		floaterBox.setFocusable(false);
		floaterBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (GameObject g : myEditor.getSelected()) {
					g.setIsFloating(floaterBox.isSelected());
				}
			}
		});
		cameraBox = new JCheckBox("Camera Toggle");
		cameraBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO code to make the camera follow that object
				for (GameObject g : myEditor.getSelected()) {
					g.setIsScreenFollowing(cameraBox.isSelected());
				}
			}
		});
		panel.add(cameraBox);
		cameraBox.setFocusable(false);
		homePanel.add(panel);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void createComboBoxes() {
		// Object Types will define things like how collisions are handled, and
		// input
		// Will also determine what other options are available for user to
		// define

		final List<String> objectTypesList = Arrays.asList(objectTypes);
		JLabel type = new JLabel("Object Type");
		objectType = new JComboBox(objectTypes);
		objectType.setFocusable(false);
		objectType.setPreferredSize(COMBO_SIZE);
		objectType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myCollisionID = (int) Math.pow(2,
						objectTypesList.indexOf(getObjectName()));
				for (GameObject g : myEditor.getSelected()) {
					g.setCollID(myCollisionID);
				}
			}
		});

		// Movement type will only be available for Enemy and (in a limited
		// sense)
		// moving platform. Aggressive is a maybe, it would be cool to have
		// some 'smarter' AI that tracks down player (if there is time, of
		// course)
		String[] movementTypes = { "User-Controlled", "Pace", "Vertical",
				"Random", "Jumping", "Aggressive", "Stationary" };
		JLabel movement = new JLabel("Movement Pattern");
		movementType = new JComboBox(movementTypes);
		movementType.setFocusable(false);
		movementType.setPreferredSize(COMBO_SIZE);
		movementType.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent event) {
				setSliderEnable();
			}
		});

		homePanel.add(type);
		homePanel.add(objectType);

		homePanel.add(movement);
		homePanel.add(movementType);

	}

	private void createSliders() {
		mySpeedSlider = initializeSlider("Movement Speed", 0);
		mySpeedSlider.setEnabled(false);
		myDurationSlider = initializeSlider("Movement Duration", 0);
		myDurationSlider.setEnabled(false);
		myGravityMagnitudeSlider = initializeSlider("Gravity Magnitude", 0);
	}

	private JSlider initializeSlider(String name, int value) {
		JSlider slider = new JSlider(0, 10);
		JLabel label = new JLabel(name);
		slider.setLabelTable(slider.createStandardLabels(1, 0));
		slider.setPaintLabels(true);
		slider.setValue(0);
		slider.setFocusable(false);
		if (name.equals("Movement Speed")) {
			slider.addChangeListener(createSpeedListener(slider));
		}
		if (name.equals("Movement Duration")) {
			slider.addChangeListener(createDurationListener(slider));
		}
		if (name.equals("Gravity Magnitude")) {
			slider.addChangeListener(createGravityListener(slider));
		}

		homePanel.add(label);
		homePanel.add(slider);
		return slider;
	}

	private ChangeListener createGravityListener(final JSlider slider) {
		return new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				myEditor.setGravity(getGravityMagnitude());
			}
		};
	}

	private ChangeListener createSpeedListener(final JSlider slider) {
		return new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				for (GameObject g : myEditor.getSelected()) {
					g.setSpeed(getMovementSpeed());
					// myEditor.findStatMap().get(g.getName()).mySpeed=movementSpeed;
				}
			}
		};
	}

	private ChangeListener createDurationListener(final JSlider slider) {
		return new ChangeListener() {

			public void stateChanged(ChangeEvent event) {
				for (GameObject g : myEditor.getSelected()) {
					// System.out.println(myEditor.findStatMap());//.myDuration=movementDuration;
					try {
						g.setDuration(getMovementDuration());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}
}