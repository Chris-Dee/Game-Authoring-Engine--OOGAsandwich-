package gameauthoringenvironment.leveleditor;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import gameengine.Level;
import gameengine.eventactions.GameEventAction;
import gameengine.gameevents.GameEvent;
import gameengine.gameevents.ParameterDesc;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import util.keybinder.BindingRunner;
import jgame.JGPoint;

public class LevelStatsBar extends JPanel {

	private static final String TIMER_DEFAULT_TEXT = "Timer (set to 0 for no timer)";
	private static final Dimension PREFERRED_LEVEL_SLIDERS_SIZE = new Dimension(
			150, 30);
	private static final String[] EVENT_DEFAULT_ARRAY_SIZE = new String[100];
	private static final int GRAVITY_SLIDER_DEFAULT_VALUE = 0;
	private static final int[] GRAVITY_SLIDER_RANGE = new int[] { 0, 10 };
	private static final int GRAVITY_MAGNITUDE_SCALING_FACTOR = 10;
	private static final int ACTION_ARRAY_SIZE = 100;

	private static final int SPINNER_MINIMUM = 0;
	private static final int SPINNER_STARTING_VALUE = 0;
	private static final int SPINNER_INCREMENT = 1;
	private static final int SPINNER_MAXIMUM = 1000;
	private static final String ACTION_STRING = "action";
	private static final Dimension INPUT_BOX_PREFERRED_SIZE = new Dimension(
			250, 30);
	private static final String CHOOSE_ACTION_BUTTON_TEXT = "Choose an Action";
	private static final String ADD_EVENT_BUTTON_TEXT = "ADD EVENT";
	private static final String EVENT_STRING = "event";
	private static final String EVENTS_RESOURCES = "events";
	private static final String GAME_AUTHORING_ENVIRONMENT_RESOURCE_PACKAGE = "gameAuthoringEnvironment.levelEditor.";
	private static final String EVENT_ACTIONS_RESOURCES = "eventactions";
	private static final String GRAVITY_MAGNITUDE_SLIDER_NAME = "Gravity Magnitude";
	private static final String CREATE_EVENT_BUTTON_TEXT = "Create an Event";
	private static final String EVENT_POPUP_QUESTION = "What would you like to call this event?";
	private static final String ADD_DEFAULT_EVENTS_BUTTON_TEXT = "Add Default Events";
	private static final String EVENTS = "Events";
	private static final String SET = "set";
	private static final String HEIGHT_SLIDER_NAME = "Height";
	private static final String WIDTH_SLIDER_NAME = "Width";
	private static final String KEYBIND_BUTTON_TEXT = "Bind Keys";
	private static final String DELETE_EVENT_BUTTON_TEXT = "DELETE EVENT";

	private static final int[] SLIDER_RANGE = { 2, 20 };
	private static final String[] EVENT_TYPES = { "Mario" };

	private String eventName;
	private String chosenEvent;
	private String[] arrayForEvent;
	private String[] arrayForAction;
	private LevelEditor myLevelEditor;
	private JSlider myWidthSlider;
	private JSlider myHeightSlider;
	private SliderObject myGravityMagnitudeSlider;
	private JSpinner myTimerSpinner;
	private ResourceBundle myResourcesForEvents;
	private ResourceBundle myResourcesForEventActions;
	private JPanel buttonModule;
	private JComboBox eventOptions;
	private JComboBox currentEvents;
	private JComboBox actionOptions;
	private JComboBox defaultEvents;
	private JButton chooseAction;
	private JButton createEvent;
	private JButton deleteEventButton;
	private JButton addEventButton;
	private JButton keyBindButton;
	private JButton addDefaultEventsButton;
	private Map<String, String> myEventsMap = new HashMap<String, String>();
	private Map<String, String> myEventActionsMap = new HashMap<String, String>();
	private Map<String, GameEvent> myNameToGameEventMap = new HashMap<String, GameEvent>();

	/**
	 * Panel that displays all parameters that can be modified about the current
	 * level. Included are things such as movement type, general type of object,
	 * gravity, etc..
	 * 
	 * @param level
	 *            Level being edited
	 * @param levelEditor
	 *            LevelEditor that this object exists in
	 */
	public LevelStatsBar(LevelEditor levelEditor) {
		myLevelEditor = levelEditor;

		myResourcesForEvents = ResourceBundle
				.getBundle(GAME_AUTHORING_ENVIRONMENT_RESOURCE_PACKAGE
						+ EVENTS_RESOURCES);
		myResourcesForEventActions = ResourceBundle
				.getBundle(GAME_AUTHORING_ENVIRONMENT_RESOURCE_PACKAGE
						+ EVENT_ACTIONS_RESOURCES);
		createEventActionsMap();
		createEventsMap();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		createEverything();
		myLevelEditor.setLevelObjectBar(this);
		initializeEventsMap(myLevelEditor.getLevel().getEvents());
		setGravSliderPosition((int) (myLevelEditor.getLevel().getGravityVal() * GRAVITY_MAGNITUDE_SCALING_FACTOR));
	}

	/**
	 * Adds user defined events (if applicable) to the events map.
	 * 
	 * @param events
	 *            List of events to be added if not already in map.
	 */
	public void initializeEventsMap(List<GameEvent> events) {
		for (GameEvent event : events) {
			myNameToGameEventMap.put(event.getName(), event);
		}
		createEverything();
	}

	/**
	 * Sets the gravity slider to the appropriate value.
	 * 
	 * @param value
	 *            The value for the slider to be set to.
	 */
	private void setGravSliderPosition(int value) {
		myGravityMagnitudeSlider.setValue(value);
	}

	private void createEverything() {
		removeAll();
		add(addLevelSlidersAndKeyBind());
		createGravitySlider();
		createTimeSlider();
		createDefaultEventsButton();
		createEventButton();
		validate();
	}

	private void createEventsMap() {
		for (String event : myResourcesForEvents.keySet()) {
			myEventsMap.put(event, myResourcesForEvents.getString(event));
		}
	}

	private void createGravitySlider() {
		myGravityMagnitudeSlider = new SliderObject(
				GRAVITY_MAGNITUDE_SLIDER_NAME, GRAVITY_SLIDER_DEFAULT_VALUE,
				GRAVITY_SLIDER_RANGE);
		myGravityMagnitudeSlider.addChangeListener(new GravityListener());
		add(myGravityMagnitudeSlider);
	}

	private void createTimeSlider() {
		SpinnerModel model = new SpinnerNumberModel(SPINNER_STARTING_VALUE,
				SPINNER_MINIMUM, SPINNER_MAXIMUM, SPINNER_INCREMENT);
		myTimerSpinner = addLabeledSpinner(myGravityMagnitudeSlider,
				TIMER_DEFAULT_TEXT, model);
		myTimerSpinner.setFocusable(false);
		myTimerSpinner.addChangeListener(new TimerListener());
		myTimerSpinner.setValue(myLevelEditor.getLevel().getCurrentTime());
	}

	private void createEventActionsMap() {
		for (String eventAction : myResourcesForEventActions.keySet()) {
			myEventActionsMap.put(eventAction,
					myResourcesForEventActions.getString(eventAction));
		}
	}

	private String[] createStringArrayFromMap(Map<String, String> map) {
		String[] toReturn = new String[map.size()];
		int i = 0;
		for (String str : map.keySet()) {
			toReturn[i] = str;
			i++;
		}
		return toReturn;
	}

	private void createEventButton() {
		buttonModule = new JPanel();
		buttonModule.setLayout(new BoxLayout(buttonModule, BoxLayout.Y_AXIS));
		String[] options = createStringArrayFromMap(myEventsMap);
		eventOptions = new JComboBox(options);
		eventOptions.setSelectedIndex(0);
		eventOptions.setFocusable(false);
		eventOptions.setPreferredSize(INPUT_BOX_PREFERRED_SIZE);
		setCurrentEvents();
		buttonModule.add(eventOptions);
		createEvent = new JButton(CREATE_EVENT_BUTTON_TEXT);
		createEvent.addActionListener(new CreateEventListener());
		createEvent.setFocusable(false);
		buttonModule.add(createEvent);
		add(buttonModule);
	}

	private void addFields() {
		createEvent.setEnabled(false);
		eventName = JOptionPane.showInputDialog(EVENT_POPUP_QUESTION);
		if (eventName != null) {
			try {
				GameEvent thisEvent = (GameEvent) Class.forName(
						myEventsMap.get(chosenEvent)).newInstance();
				final List<ParameterDesc> parameters = thisEvent
						.getParameters();
				arrayForEvent = EVENT_DEFAULT_ARRAY_SIZE;
				Arrays.fill(arrayForEvent, "0");
				createUserInputFields(parameters, EVENT_STRING);
				createActions();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void createDefaultEventsButton() {
		String[] typesOfEvents = EVENT_TYPES;
		defaultEvents = new JComboBox(typesOfEvents);
		defaultEvents.setFocusable(false);
		defaultEvents.setSelectedIndex(0);
		addDefaultEventsButton = new JButton(ADD_DEFAULT_EVENTS_BUTTON_TEXT);
		addDefaultEventsButton.setFocusable(false);
		addDefaultEventsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Method m = myLevelEditor.getClass().getDeclaredMethod(
							SET + (String) defaultEvents.getSelectedItem()
									+ EVENTS);
					m.invoke(myLevelEditor);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		myGravityMagnitudeSlider.add(defaultEvents);
		myGravityMagnitudeSlider.add(addDefaultEventsButton);
	}

	private void createAddEventButton() {
		chooseAction.setEnabled(false);
		addEventButton = new JButton(ADD_EVENT_BUTTON_TEXT);
		addEventButton.setFocusable(false);
		buttonModule.add(addEventButton);
		addEventButton.addActionListener(new AddListener());
		validate();
	}

	private void createActions() {
		actionOptions = new JComboBox(
				createStringArrayFromMap(myEventActionsMap));
		actionOptions.setSelectedIndex(0);
		actionOptions.setFocusable(false);
		actionOptions.setPreferredSize(INPUT_BOX_PREFERRED_SIZE);
		chooseAction = new JButton(CHOOSE_ACTION_BUTTON_TEXT);
		chooseAction.addActionListener(new ChooseActionListener());
		chooseAction.setFocusable(false);
		buttonModule.add(actionOptions);
		buttonModule.add(chooseAction);
		validate();
	}

	private void createComboBoxForStringInput(ParameterDesc par,
			final int spot, final String whichArray) {
		String[] allowedInputs = par.description().split(" ");
		final JComboBox inputBox = new JComboBox(allowedInputs);
		inputBox.setSelectedIndex(0);
		arrayAdditionFromInputBox(spot, whichArray, inputBox);
		inputBox.setFocusable(false);
		inputBox.setPreferredSize(INPUT_BOX_PREFERRED_SIZE);
		inputBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				arrayAdditionFromInputBox(spot, whichArray, inputBox);
			}
		});
		buttonModule.add(inputBox);
	}

	private void arrayAdditionFromInputBox(final int spot,
			final String whichArray, final JComboBox inputBox) {
		if (whichArray.equals(ACTION_STRING))
			arrayForAction[spot] = (String) inputBox.getSelectedItem();
		if (whichArray.equals(EVENT_STRING))
			arrayForEvent[spot] = (String) inputBox.getSelectedItem();
	}

	private void createSpinner(ParameterDesc par, final int spot,
			final String whichArray) {
		SpinnerModel model = new SpinnerNumberModel(SPINNER_STARTING_VALUE,
				SPINNER_MINIMUM, SPINNER_MAXIMUM, SPINNER_INCREMENT);
		final JSpinner spinner = addLabeledSpinner(buttonModule, par.name()
				+ " " + par.description(), model);
		spinner.setFocusable(false);
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (whichArray.equals(ACTION_STRING))
					arrayForAction[spot] = Integer.toString((Integer) spinner
							.getValue());
				else if (whichArray.equals(EVENT_STRING))
					arrayForEvent[spot] = Integer.toString((Integer) spinner
							.getValue());
			}
		});
		buttonModule.add(spinner);
	}

	private void createCheckBoxesForBooleans(ParameterDesc parameter,
			final int spot, final String whichArray) {
		String[] toCreate = parameter.description().split(" ");
		final JCheckBox[] checkBoxes = new JCheckBox[toCreate.length];
		int j = 0;
		for (String desc : toCreate) {
			final int position = j;
			checkBoxes[j] = new JCheckBox(desc);
			checkBoxes[j].setFocusable(false);
			checkBoxes[j].addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					if (whichArray.equals(EVENT_STRING))
						arrayForEvent[spot + position] = Boolean
								.toString(checkBoxes[position].isSelected());
					if (whichArray.equals(ACTION_STRING))
						arrayForAction[spot + position] = Boolean
								.toString(checkBoxes[position].isSelected());
				}
			});
			buttonModule.add(checkBoxes[j]);
			j++;
		}
	}

	private void setCurrentEvents() {
		String[] events = createEventsArray();
		if (events.length != 0) {
			currentEvents = new JComboBox(events);
			currentEvents.setSelectedIndex(0);
			currentEvents.setFocusable(false);
			currentEvents.setPreferredSize(INPUT_BOX_PREFERRED_SIZE);
			buttonModule.add(currentEvents);
			createDeleteEventButton();
		}
	}

	private void createDeleteEventButton() {
		deleteEventButton = new JButton(DELETE_EVENT_BUTTON_TEXT);
		deleteEventButton.addActionListener(new DeleteEventListener());
		deleteEventButton.setFocusable(false);
		buttonModule.add(deleteEventButton);
	}

	private String[] createEventsArray() {
		String[] events = new String[myNameToGameEventMap.size()];
		int i = 0;
		for (String eventName : myNameToGameEventMap.keySet()) {
			events[i] = eventName;
			i++;
		}
		return events;
	}

	private JSpinner addLabeledSpinner(Container c, String label,
			SpinnerModel model) {
		JLabel lab = new JLabel(label);
		c.add(lab);
		JSpinner spinner = new JSpinner(model);
		lab.setLabelFor(spinner);
		c.add(spinner);
		return spinner;
	}

	private JPanel addLevelSlidersAndKeyBind() {
		JPanel sliderPanel = new JPanel();
		keyBindButton = new JButton(KEYBIND_BUTTON_TEXT);
		keyBindButton.setFocusable(false);
		sliderPanel.add(keyBindButton);
		keyBindButton.addActionListener(new KeyBindListener());
		sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));

		SliderObject widthSlider = new SliderObject(WIDTH_SLIDER_NAME,
				myLevelEditor.getLevel().getLevelSize().y, SLIDER_RANGE);
		myWidthSlider = widthSlider.getSlider();
		myWidthSlider.setPaintLabels(false);
		widthSlider.addChangeListener(new LevelSizeListener());

		SliderObject heightSlider = new SliderObject(HEIGHT_SLIDER_NAME,
				myLevelEditor.getLevel().getLevelSize().x, SLIDER_RANGE);
		myHeightSlider = heightSlider.getSlider();
		myHeightSlider.setPaintLabels(false);
		heightSlider.addChangeListener(new LevelSizeListener());

		sliderPanel.add(widthSlider);
		sliderPanel.add(heightSlider);
		sliderPanel.setPreferredSize(PREFERRED_LEVEL_SLIDERS_SIZE);

		return sliderPanel;
	}

	private void createUserInputFields(List<ParameterDesc> parametersForAction,
			String whichType) {
		int i = 0;
		for (ParameterDesc par : parametersForAction) {
			final int spot = i;
			if (par.type().equals(Integer.class)) {
				createSpinner(par, spot, whichType);
			}
			if (par.type().equals(String.class)) {
				createComboBoxForStringInput(par, spot, whichType);
			}
			if (par.type().equals(Boolean.class)) {
				createCheckBoxesForBooleans(par, spot, whichType);
			}
			i++;
		}
		validate();
	}

	private class AddListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				// Creates the event
				GameEvent thisEvent = (GameEvent) Class
						.forName(myEventsMap.get(chosenEvent))
						.getConstructor(List.class, String.class)
						.newInstance(
								new ArrayList<String>(
										Arrays.asList(arrayForEvent)),
								eventName);
				// Creates the action
				GameEventAction thisAction = (GameEventAction) Class
						.forName(
								myEventActionsMap.get((String) actionOptions
										.getSelectedItem()))
						.getConstructor(List.class)
						.newInstance(
								new ArrayList<String>(Arrays
										.asList(arrayForAction)));
				thisEvent.addAction(thisAction);
				myLevelEditor.getLevel().addEvent(thisEvent);
				myNameToGameEventMap.put(eventName, thisEvent);
				createEverything();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	private class DeleteEventListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String selected = (String) currentEvents.getSelectedItem();
			myLevelEditor.getLevel().removeEvent(
					myNameToGameEventMap.get(selected));
			myNameToGameEventMap.remove(selected);
			createEverything();
		}
	}

	private class CreateEventListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			chosenEvent = (String) eventOptions.getSelectedItem();
			addFields();
		}
	}

	private class ChooseActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			GameEventAction action;
			try {
				action = (GameEventAction) Class.forName(
						myEventActionsMap.get((String) actionOptions
								.getSelectedItem())).newInstance();
				List<ParameterDesc> parametersForAction = action
						.getParameters();
				arrayForAction = new String[ACTION_ARRAY_SIZE];
				Arrays.fill(arrayForAction, "0");
				createUserInputFields(parametersForAction, ACTION_STRING);
				createAddEventButton();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	private class GravityListener implements ChangeListener {
		public void stateChanged(ChangeEvent event) {
			myLevelEditor.setGravity(myGravityMagnitudeSlider.getValue());
		};
	}

	private class TimerListener implements ChangeListener {
		public void stateChanged(ChangeEvent arg0) {
			myLevelEditor.getLevel().setCurrentTime(
					(int) myTimerSpinner.getValue());
		}
	}

	private class LevelSizeListener implements ChangeListener {
		public void stateChanged(ChangeEvent arg0) {
			myLevelEditor.getLevel().changeLevelSize(
					new JGPoint(myWidthSlider.getValue(), myHeightSlider
							.getValue()));
			myLevelEditor.setPFSize(myWidthSlider.getValue(),
					myHeightSlider.getValue());
		}
	}

	private class KeyBindListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			new BindingRunner(null);
		}
	}

}