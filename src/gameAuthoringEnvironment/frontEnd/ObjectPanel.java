package gameAuthoringEnvironment.frontEnd;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * Panel that holds all object creating functions, such as adding levels and
 * objects to the game.
 * 
 */

public class ObjectPanel extends JPanel {
	LevelPanel levels;

	public ObjectPanel(LevelPanel l) {
		levels = l;
		makeMainFrame();
		setBackground(VAEview.backgroundColor);
	}

	private void makeMainFrame() {
		makeLevelButton(this);
	}

	private void makeLevelButton(JPanel panel) {
		// TODO add properties file
		JPanel buttonModule = new JPanel();
		buttonModule.setBackground(VAEview.backgroundColor);
		buttonModule.setLayout(new BoxLayout(buttonModule, BoxLayout.Y_AXIS));
		final JTextField levelName = new JTextField(0);
		buttonModule.add(levelName);
		JButton level = new JButton("Add Level");
		level.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				levels.addLevel(levelName.getText());
			}
		});
		buttonModule.add(level);
		panel.add(buttonModule);
	}

}
