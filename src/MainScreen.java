import gameauthoringenvironment.frontend.VAEview;
import gameengine.Game;
import gameplayer.GamePlayerFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainScreen extends JFrame {

	private static final String CREATE_NEW_GAME_BUTTON_TEXT = "Create a new game";
	private static final String DEFAULT_USER_DIRECTORY = "user.dir";
	private static final String PLAY_GAME_BUTTON_TEXT = "Play Game";
	private static final String BAD_FILE_ERROR_NAME = "Bad File Error";
	private static final String INVALID_GAME_CHOICE_ERROR_MESSAGE = "Invalid game file, please choose a different one";
	private static final String DESCRIPTION = "Please select whether you want to play or create a game";
	private static final String TITLE = "OOGASandwich Main Menu";
	private static final String FONT = "Serif";
	private static final int TITLE_FONT_SIZE = 32;
	private static final int DESCRIPTION_FONT_SIZE = 26;
	private static final Dimension TEXT_DIMENSION = new Dimension(750, 200);

	private JButton playGameButton;
	private JButton createGameButton;

	public static void main(String[] args) {
		MainScreen m = new MainScreen();
	}

	public MainScreen() {
		initialize();
	}

	private void initialize() {
		setTitle("OOGASandwich Main Screen");
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setVisible(true);
		createButtons();
		createMainPanel();
	}

	private void createMainPanel() {
		JPanel panel = (JPanel) this.getContentPane();
		panel.setLayout(new BorderLayout());

		panel.add(textPanel(), BorderLayout.NORTH);
		panel.add(buttonPanel(), BorderLayout.SOUTH);

		pack();
		setLocationRelativeTo(null);
	}

	private JPanel textPanel() {
		JPanel text = new JPanel(new BorderLayout());

		JLabel title = new JLabel(TITLE, JLabel.CENTER);
		title.setFont(new Font(FONT, Font.PLAIN, TITLE_FONT_SIZE));

		JLabel description = new JLabel(DESCRIPTION, JLabel.CENTER);
		description.setFont(new Font(FONT, Font.PLAIN, DESCRIPTION_FONT_SIZE));

		text.add(title, BorderLayout.NORTH);
		text.add(description, BorderLayout.SOUTH);

		text.setPreferredSize(TEXT_DIMENSION);

		return text;
	}

	private JPanel buttonPanel() {
		JPanel buttons = new JPanel();

		buttons.add(playGameButton, BorderLayout.NORTH);
		buttons.add(createGameButton, BorderLayout.SOUTH);

		return buttons;
	}

	private void createButtons() {
		playGameButton = new JButton(PLAY_GAME_BUTTON_TEXT);
		playGameButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System
						.getProperty(DEFAULT_USER_DIRECTORY)));
				String selectedFile = "";
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					selectedFile = fileChooser.getSelectedFile().getName();
				}
				try {
					new GamePlayerFrame(new Game(selectedFile));
					dispose();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null,
							INVALID_GAME_CHOICE_ERROR_MESSAGE,
							BAD_FILE_ERROR_NAME, JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}

		});

		createGameButton = new JButton(CREATE_NEW_GAME_BUTTON_TEXT);
		createGameButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				new VAEview();
				dispose();
			}

		});
	}
}
