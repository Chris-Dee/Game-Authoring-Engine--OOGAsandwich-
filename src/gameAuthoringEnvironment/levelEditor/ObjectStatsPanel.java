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
	
	private String objectName = "Player";
	private String movementName = "Pace";
	
	private Integer movementSpeed = 5;
	private int movementDuration = 5;
	
	private int gravityMag = 0;
	private LevelEditor myEditor;
	
	
	/**
	 * Panel that will display the stats for the object that is being 
	 * added to the level in the level editor environment
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
		setVisible(true);
	}
	
	public String getObjectName() {
		return objectName;
	}
	
	public String getMovementName() {
		return movementName;
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
		
	}
	
	private void createSliders() {
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
				myEditor.setGravity(gravityMag);
			}
		});
		
		final JSlider gravityMagnitude = new JSlider(0,10);
		JLabel gravityLabel = new JLabel("Gravity Magnitude");
		movementLength.setLabelTable(gravityMagnitude.createStandardLabels(1, 0));
		movementLength.setPaintLabels(true);
		movementLength.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent event) {
				gravityMag = gravityMagnitude.getValue();
			}
		});
		
		add(speedLabel);
		add(speed);

		
		add(movementLabel);
		add(movementLength);
		
		add(gravityLabel);
		add(gravityMagnitude);
		
	}

/*import gameEngine.Level;

import javax.swing.JPanel;

public class ObjectStatsPanel extends JPanel {
	LevelEditor myLevelEdit;
public ObjectStatsPanel(LevelEditor l){
	myLevelEdit=l;
	initialize();
}
public void initialize(){
	makeMainPanel();
}
public void makeMainPanel(){}
>>>>>>> 4266c77e617ebdf4cb2558463699dfdb9851edf7*/
}
