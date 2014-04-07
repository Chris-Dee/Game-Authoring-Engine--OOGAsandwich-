package gameAuthoringEnvironment.frontEnd;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ObjectPanel extends JPanel {
	private LevelPanel myLevelPanel;
	private static final String DEFAULT_RESOURCE_FILE_NAME = "resources.GameAuthoringEnvironment";
	private ResourceBundle myResources;
	private Color myBackgroundColor;

	/**
	 * Panel that holds all object creating functions, such as adding levels and
	 * objects to the game.
	 * 
	 * @param levelPanel
	 * 			LevelPanel of the GAE
	 */
	public ObjectPanel(LevelPanel levelPanel, Color backgroundColor) {
		myLevelPanel = levelPanel;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_FILE_NAME);
		makeMainFrame();
		myBackgroundColor = backgroundColor;
		setBackground(myBackgroundColor);
	}

	private void makeMainFrame() {
		makeLevelButton(this);
	}

	private void makeLevelButton(JPanel panel) {
		// TODO add properties file
		JPanel buttonModule = new JPanel();
		buttonModule.setBackground(myBackgroundColor);
		buttonModule.setLayout(new BoxLayout(buttonModule, BoxLayout.Y_AXIS));
		final JTextField levelName = new JTextField(0);
		buttonModule.add(levelName);
		JButton level = new JButton(myResources.getString("NewLevelButton"));
		level.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myLevelPanel.addLevel(levelName.getText());
				levelName.setText("");
			}
		});
		buttonModule.add(level);
		panel.add(buttonModule);
	}
}