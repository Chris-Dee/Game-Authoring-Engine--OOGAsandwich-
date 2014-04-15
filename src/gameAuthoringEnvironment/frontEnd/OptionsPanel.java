package gameAuthoringEnvironment.frontEnd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class OptionsPanel extends JMenuBar {
	

	private VAEview myViewer;

	/**
	 * User options panel for tasks such as saving, loading, etc.
	 * 
	 * @param view
	 *            Main VAEview that this class exists in
	 */
	public OptionsPanel(VAEview view) {
		myViewer = view;
		initialize();
	}

	public void initialize() {
		JMenu file = new JMenu("File");
		JMenuItem playItem = new JMenuItem("Play");
		playItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					myViewer.playGame();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		JMenuItem saveItem = new JMenuItem("Save to text file");

		saveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					myViewer.saveToTextFile();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		JMenuItem loadItem = new JMenuItem("Load in text file");

		loadItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				myViewer.loadFromTextFile();
			}

		});

		file.add(playItem);
		file.add(saveItem);
		file.add(loadItem);

		add(file);
		setVisible(true);
	}
}
