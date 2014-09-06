package gameauthoringenvironment.leveleditor;

import gameauthoringenvironment.frontend.PanelFactory;
import gameengine.GameObject;
import gameengine.Level;
import gameengine.UninstantiatedGameObject;

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
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

//import xboxkeybinder.BindingRunner;
import util.keybinder.*;

@SuppressWarnings("serial")
public class ObjectStatsPanel extends JPanel {
	private static final Dimension HOME_PANEL_DIMENSION = new Dimension(350,
			900);
	private static final Dimension OBJECT_TYPE_DIMENSION = new Dimension(250,
			30);
	private static final int COLLISIONID_TEXTBOX_WIDTH = 7;
	private static final int OBJECT_NAME_TEXTBOX_WIDTH = 15;
	private static final int PANEL_WIDTH = 250;
	private static final String DEFAULT_COLLISION_ID_TEXT = "Collision: ";
	private static final String DEFAULT_OBJECT_ID_TEXT = "ID: ";
	private static final String DEFAULT_OBJECT_NAME_TEXT = "Name: ";
	private static final String SELECTED_OBJECT_NAME_CHANGE_BUTTON = "Change Object Name";
	private static final String DISABLE_GRAVITY_FOR_SELECTED_OBJECT_CHECK_BOX = "Check if object floats";
	private static final String OBJECT_TYPE_LABEL = "Object Type";
	private static final String MOVEMENT_PATTERN_LABEL = "Movement Pattern";
	private static final String STATIONARY_TEXT = "Stationary";
	private static final String ADD_OBJECT_TYPE_BUTTON = "Add Object Type";
	private static final String MOVEMENT_SPEED_SLIDER_NAME = "Movement/jump Speed";
	private static final String MOVEMENT_DURATION_SLIDER_NAME = "Movement Duration (U.C. speed)";
	private static final String BIND_TO_SELECTED_OBJECT_BUTTON_TEXT = "Bind To Selected Object";
	private static final String HIT_POINTS_FOR_OBJECT_TEXT = "Hit Points for Object";
	private static final Dimension COMBO_SIZE = new Dimension(PANEL_WIDTH, 30);
	private static final Dimension OBJECT_MOVEMENT_PANEL_SIZE = new Dimension(
			1000, 200);
	private static final Dimension CHECKBOX_PANEL_SIZE = new Dimension(1000, 50);
	private static final Dimension SLIDER_PANEL_SIZE = OBJECT_MOVEMENT_PANEL_SIZE;

	private static final int[] SLIDER_RANGE = { 0, 10 };
	private final static String[] DEFAULT_OBJECT_TYPES = { "Player",
			"Platform", "Enemy", "Goal", "Scenery", "Coin", "GunPowerUp",
			"InvincibilityPowerUp", "SizePowerUp", "SpeedPowerUp" };
	private static final String[] MOVEMENT_TYPES = { "UserControlled", "Pace",
			"VerticalPace", "ChasePlayerHorizontal", "ChasePlayerFlying",
			"Jumping", "Stationary" };

	private int myCollisionID = 0;
	private boolean speedEnable = false;
	private Map<String, Integer> myCollisionIDMap = new HashMap<String, Integer>();
	private JButton binder;
	private LevelEditor myLevelEditor;
	private ObjectToolbar imageButtons;
	private Dimension panelSize;
	private JPanel homePanel;
	private SliderObject mySpeedSlider;
	private SliderObject myDurationSlider;
	private SliderObject myHitPointsSlider;
	private JComboBox objectType;
	private JComboBox movementType;
	private JTextField objectIDBox;
	private JTextField collisionIDBox;
	private JTextField objectNameBox;
	private JCheckBox floaterBox;
	private JCheckBox cameraBox;

	/**
	 * Panel that will display the stats for the object that is being added to
	 * the level in the level editor environment.
	 */

	public ObjectStatsPanel(ObjectEditorContainer container, LevelEditor editor) {
		homePanel = new JPanel();
		panelSize = new Dimension(PANEL_WIDTH, container.HEIGHT);
		setLayout(new BorderLayout());
		homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.Y_AXIS));
		initializeCollisionIDs();

		myLevelEditor = editor;
		myLevelEditor.setObjectStatsPanel(this);
		loadInCorrectColIDMap(myLevelEditor.getLevel().getObjects());
		initialize();
		add(new ObjectToolbar(myLevelEditor), BorderLayout.WEST);
		add(homePanel, BorderLayout.EAST);
	}

	/**
	 * Sets the stats of the ObjectStats object to the values found in this
	 * panel.
	 * 
	 * @param objectStats
	 *            ObjectStats object to be changed.
	 */
	public void setStats(ObjectStats objectStats) {
		if (objectStats != null) {
			mySpeedSlider.setValue(objectStats.mySpeed);
			myDurationSlider.setValue(objectStats.myDuration);
			objectType.setSelectedItem(objectStats.myColType);
			movementType.setSelectedItem(objectStats.myMovementPattern);
			floaterBox.setSelected(objectStats.isFloating);
			cameraBox.setSelected(objectStats.isCameraFollow);
			myHitPointsSlider.setValue(objectStats.hitPoints);
		}
		setSliderEnable();
	}

	private void loadInCorrectColIDMap(List<UninstantiatedGameObject> objects) {
		for (UninstantiatedGameObject obj : objects) {
			if (!myCollisionIDMap.containsKey(obj.getName())
					&& obj.getColID() > myCollisionIDMap.keySet().size() - 1
					&& obj.getName() != null && obj != null) {
				myCollisionIDMap.put(obj.getName(), obj.getColID());
			}
		}
	}

	public ObjectStats exportStats() {
		return new ObjectStats((String) objectType.getSelectedItem(),
				myCollisionID, (String) movementType.getSelectedItem(),
				mySpeedSlider.getValue(), myDurationSlider.getValue(),
				cameraBox.isSelected(),
				myLevelEditor.getMover().getImageName(),
				floaterBox.isSelected(), myHitPointsSlider.getValue());
	}

	/**
	 * Gets the name of the selected item in the objectType drop-down menu.
	 * 
	 * @return The object name.
	 */
	public String getObjectName() {
		return (String) objectType.getSelectedItem();
	}

	public int getHitPoints() {
		return myHitPointsSlider.getValue();
	}

	/**
	 * Gets the name of the movement pattern selected.
	 * 
	 * @return The movement pattern name.
	 */

	public String getMovementName() {
		return (String) movementType.getSelectedItem();
	}

	/**
	 * Returns true if the floating box is selected.
	 * 
	 * @return True if the floating box is selected.S
	 */
	public boolean getFloating() {
		return floaterBox.isSelected();
	}

	public boolean getScreenFollow() {
		return cameraBox.isSelected();
	}

	/**
	 * Gets the collision ID based on what type of object is selected (used for
	 * defaults)
	 * 
	 * @return The collision ID
	 */
	public int getCollisionID() {
		return myCollisionID;
	}

	/**
	 * Gets the value in the speed slider.
	 * 
	 * @return The value of the speed slider.
	 */
	public int getMovementSpeed() {
		return mySpeedSlider.getValue();
	}

	/**
	 * Gets the value in the duration slider.
	 * 
	 * @return The value in the duration slider.
	 */
	public int getMovementDuration() {
		return myDurationSlider.getValue();
	}

	/**
	 * Sets the ID, ColID, and ObjectName text boxes based on input.
	 * 
	 * @param forObjectID
	 *            What the objectID text box should show.
	 * @param forCollisionID
	 *            What the CollisionID text box should show.
	 * @param forObjectName
	 *            What the ObjectName text box should show
	 */
	public void setIDAndNameTextBoxes(String forObjectID,
			String forCollisionID, String forObjectName) {
		if (forObjectID != null && forObjectID != null && objectIDBox != null
				&& collisionIDBox != null && forObjectName != null
				&& objectNameBox != null) {
			objectIDBox.setText(DEFAULT_OBJECT_ID_TEXT + forObjectID);
			collisionIDBox.setText(DEFAULT_COLLISION_ID_TEXT + forCollisionID);
			objectNameBox.setText(DEFAULT_OBJECT_NAME_TEXT + forObjectName);
		}
	}

	private void createCheckBoxes() {
		JPanel checkboxPanel = new JPanel();
		floaterBox = new JCheckBox(
				DISABLE_GRAVITY_FOR_SELECTED_OBJECT_CHECK_BOX);
		checkboxPanel.add(floaterBox);
		floaterBox.setFocusable(false);
		floaterBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myLevelEditor.getMover().getStats().isFloating = floaterBox
						.isSelected();
				for (GameObject g : myLevelEditor.getSelected()) {
					g.setIsFloating(floaterBox.isSelected());
				}
			}
		});
		cameraBox = new JCheckBox("Camera Toggle");
		cameraBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myLevelEditor.getMover().getStats().isCameraFollow = cameraBox
						.isSelected();
				for (GameObject g : myLevelEditor.getSelected()) {
					g.setIsScreenFollowing(cameraBox.isSelected());
				}
			}
		});
		checkboxPanel.add(cameraBox);
		cameraBox.setFocusable(false);
		checkboxPanel.setPreferredSize(CHECKBOX_PANEL_SIZE);
		checkboxPanel.setMaximumSize(CHECKBOX_PANEL_SIZE);
		homePanel.add(checkboxPanel);
	}

	private void createObjectNameBox(JPanel motherPanel) {
		JPanel vertPanel = new JPanel();
		vertPanel.setLayout(new BoxLayout(vertPanel, BoxLayout.Y_AXIS));
		JPanel buttonVert = new JPanel();
		buttonVert.setLayout(new BoxLayout(buttonVert, BoxLayout.Y_AXIS));
		objectNameBox = new JTextField(OBJECT_NAME_TEXTBOX_WIDTH);
		objectNameBox.setEditable(false);
		objectNameBox.setText(DEFAULT_OBJECT_NAME_TEXT);
		final JTextField nameChange = new JTextField();
		JButton button = new JButton(SELECTED_OBJECT_NAME_CHANGE_BUTTON);
		button.addActionListener(new ChangeNameListener(nameChange));

		vertPanel.add(objectNameBox);
		buttonVert.add(nameChange);
		buttonVert.add(button);
		motherPanel.add(vertPanel);
		motherPanel.add(buttonVert);
	}

	private void createObjectIDBox(JPanel homePanel) {
		objectIDBox = new JTextField(DEFAULT_OBJECT_ID_TEXT.length() - 1);
		objectIDBox.setEditable(false);
		objectIDBox.setText(DEFAULT_OBJECT_ID_TEXT);

		homePanel.add(objectIDBox);
	}

	private void createCollisionIDBox(JPanel homePanel) {
		collisionIDBox = new JTextField(COLLISIONID_TEXTBOX_WIDTH);
		collisionIDBox.setEditable(false);
		collisionIDBox.setText(DEFAULT_COLLISION_ID_TEXT);
		homePanel.add(collisionIDBox);
	}

	private void initializeCollisionIDs() {
		int i = 0;
		for (String type : DEFAULT_OBJECT_TYPES) {
			myCollisionIDMap.put(type, i);
			i++;
		}
	}

	private String[] createObjectTypesList() {
		String[] types = new String[myCollisionIDMap.size()];
		for (String type : myCollisionIDMap.keySet()) {
			types[myCollisionIDMap.get(type)] = type; // make index = colid
														// of object
		}
		return types;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void createComboBoxes() {
		// Object Types will define things like how collisions are handled, and
		// input
		// Will also determine what other options are available for user to
		// define

		JPanel objectAndMovementPanel = new JPanel();
		objectAndMovementPanel.setLayout(new BoxLayout(objectAndMovementPanel,
				BoxLayout.Y_AXIS));

		final List<String> objectTypesList = Arrays
				.asList(createObjectTypesList());
		JLabel type = new JLabel(OBJECT_TYPE_LABEL);
		objectType = new JComboBox(createObjectTypesList());
		objectType.setFocusable(false);
		objectType.setPreferredSize(COMBO_SIZE);
		objectType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myCollisionID = myCollisionIDMap.get(getObjectName());
				myLevelEditor.getMover().getStats().myColType = (String) objectType
						.getSelectedItem();
				for (GameObject g : myLevelEditor.getSelected()) {
					g.setCollID(myCollisionID);
				}
			}
		});

		// Movement type will only be available for Enemy and (in a limited
		// sense)
		// moving platform. Aggressive is a maybe, it would be cool to have
		// some 'smarter' AI that tracks down player (if there is time, of
		// course)

		JLabel movement = new JLabel(MOVEMENT_PATTERN_LABEL);
		movementType = new JComboBox(MOVEMENT_TYPES);
		movementType.setFocusable(false);
		movementType.setPreferredSize(COMBO_SIZE);
		movementType.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent event) {
				setSliderEnable();
				myLevelEditor.getMover().getStats().myMovementPattern = (String) movementType
						.getSelectedItem();
				for (GameObject g : myLevelEditor.getSelected()) {
					if (!movementType.getSelectedItem().equals(
							g.getMovement().getMovementPattern())) {
						g.setMovementPattern((String) movementType
								.getSelectedItem());
					}
				}
				// TODO change the movement pattern in the gameObject somehow
			}
		});

		objectAndMovementPanel.add(type);
		objectAndMovementPanel.add(objectType);

		objectAndMovementPanel.add(movement);
		objectAndMovementPanel.add(movementType);

		objectAndMovementPanel.setPreferredSize(OBJECT_MOVEMENT_PANEL_SIZE);
		objectAndMovementPanel.setMaximumSize(OBJECT_MOVEMENT_PANEL_SIZE);

		homePanel.add(objectAndMovementPanel);
	}

	private void setSliderEnable() {
		if (movementType.getSelectedItem().equals(STATIONARY_TEXT)) {
			mySpeedSlider.setEnabled(false);
			myDurationSlider.setEnabled(false);
		} else {
			mySpeedSlider.setEnabled(true);
			myDurationSlider.setEnabled(true);
		}
		myHitPointsSlider.setEnabled(true);
	}

	private void createBindButton(JPanel motherPanel) {
		JButton binder = new JButton(BIND_TO_SELECTED_OBJECT_BUTTON_TEXT);
		binder.addActionListener(new BindListener());
		motherPanel.add(binder);
	}

	private void initialize() {
		createUserSpecifiedCollisionsBoxes();
		createComboBoxes();
		createSliders();
		createCheckBoxes();
		JPanel horizPanel = new JPanel();
		JPanel vertPanel1 = new JPanel();
		vertPanel1.setLayout(new BoxLayout(vertPanel1, BoxLayout.Y_AXIS));
		JPanel vertPanel2 = new JPanel();
		vertPanel2.setLayout(new BoxLayout(vertPanel2, BoxLayout.Y_AXIS));
		createBindButton(vertPanel1);
		createObjectIDBox(vertPanel1);
		createCollisionIDBox(vertPanel1);
		createObjectNameBox(vertPanel2);
		horizPanel.add(vertPanel1);
		horizPanel.add(vertPanel2);
		homePanel.add(horizPanel);
		homePanel.setPreferredSize(HOME_PANEL_DIMENSION);
		homePanel.setMaximumSize(HOME_PANEL_DIMENSION);
		setVisible(true);
	}

	private void createUserSpecifiedCollisionsBoxes() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		final JTextField objectTypeName = new JTextField();
		buttonPanel.add(objectTypeName);
		objectTypeName.setPreferredSize(OBJECT_TYPE_DIMENSION);
		objectTypeName.setMaximumSize(OBJECT_TYPE_DIMENSION);
		JButton addObjectTypeButton = new JButton(ADD_OBJECT_TYPE_BUTTON);
		addObjectTypeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String st = objectTypeName.getText().replaceAll("\\s", "_");

				myCollisionIDMap.put(st, myCollisionIDMap.size());
				recreateObjectPanel();
				validate();

				objectTypeName.setText("");

			}
		});
		addObjectTypeButton.setFocusable(false);
		buttonPanel.add(addObjectTypeButton);

		homePanel.add(buttonPanel);
	}

	private void recreateObjectPanel() {
		homePanel.removeAll();
		initialize();
	}

	private String cutOffInts(String toCut) {
		return toCut.replaceAll("\\d*$", "");
	}

	private void createSliders() {
		JPanel sliderPanel = new JPanel();
		sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));
		mySpeedSlider = new SliderObject(MOVEMENT_SPEED_SLIDER_NAME, 0,
				SLIDER_RANGE);
		mySpeedSlider.addChangeListener(new SpeedListener());
		mySpeedSlider.setEnabled(false);

		myDurationSlider = new SliderObject(MOVEMENT_DURATION_SLIDER_NAME, 0,
				SLIDER_RANGE);
		myDurationSlider.addChangeListener(new DurationListener());
		myDurationSlider.setEnabled(false);

		myHitPointsSlider = new SliderObject(HIT_POINTS_FOR_OBJECT_TEXT, 1,
				SLIDER_RANGE);
		myHitPointsSlider.addChangeListener(new DurationListener());
		myHitPointsSlider.setEnabled(false);

		sliderPanel.add(mySpeedSlider);
		sliderPanel.add(myDurationSlider);
		sliderPanel.add(myHitPointsSlider);

		sliderPanel.setPreferredSize(SLIDER_PANEL_SIZE);
		sliderPanel.setMaximumSize(SLIDER_PANEL_SIZE);

		homePanel.add(sliderPanel);
	}

	public void setBinderEnable(boolean b) {
		binder.setEnabled(b);
	}

	private class SpeedListener implements ChangeListener {

		public void stateChanged(ChangeEvent event) {
			setSliderEnable();
			myLevelEditor.getMover().getStats().mySpeed = mySpeedSlider
					.getValue();
			if (speedEnable) {
				for (GameObject g : myLevelEditor.getSelected()) {
					g.setSpeed(getMovementSpeed());
				}
			}
		};
	}

	private class DurationListener implements ChangeListener {

		public void stateChanged(ChangeEvent event) {
			myLevelEditor.getMover().getStats().myDuration = myDurationSlider
					.getValue();
			for (GameObject g : myLevelEditor.getSelected()) {
				try {
					g.setDuration(getMovementDuration());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
	}

	private class ChangeNameListener implements ActionListener {
		private JTextField nameChange;

		public ChangeNameListener(JTextField change) {
			nameChange = change;
		}

		public void actionPerformed(ActionEvent arg0) {
			myLevelEditor.setObjectName(nameChange.getText());
			nameChange.setText("");
		}
	}

	private class BindListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			GameObject selected;

			if (myLevelEditor.getSelected().size() != 0) {
				selected = myLevelEditor.getSelected().get(0);
				new BindingRunner(cutOffInts(selected.getName()));
			}
		}
	}
}