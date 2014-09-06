package gameauthoringenvironment.frontend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class OptionsPanel extends JMenuBar {

	private static final String LOAD_IN_JSON_FILE_TEXT = "Load in JSON file";
	private static final String SAVE_TO_JSON_FILE_TEXT = "Save to JSON file";
	private static final String PLAY_GAME_TEXT = "Play";
	private static final String FILE_TEXT_OPTION = "File";
	private VAEview myViewer;

	/**
	 * User options panel for tasks such as saving, loading, etc.. This will be
	 * used as a JMenuBar at the top of the JFrame of the VAEview.
	 * 
	 * @param view
	 *            Main VAEview that this class exists in
	 */
	public OptionsPanel(VAEview view) {
		myViewer = view;
		initialize();
	}

	private void initialize() {
		JMenu file = new JMenu(FILE_TEXT_OPTION);
		JMenuItem playItem = new JMenuItem(PLAY_GAME_TEXT); // play the current
															// game
		playItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					myViewer.playGame();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		JMenuItem saveItem = new JMenuItem(SAVE_TO_JSON_FILE_TEXT);
		saveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					myViewer.saveToJSONFile();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		JMenuItem loadItem = new JMenuItem(LOAD_IN_JSON_FILE_TEXT);
		loadItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myViewer.loadFromJSONFile();
			}
		});

		file.add(playItem);
		file.add(saveItem);
		file.add(loadItem);
		add(file);
	}
}