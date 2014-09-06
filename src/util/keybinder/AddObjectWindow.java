package util.keybinder;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class AddObjectWindow extends JFrame{
	
	private TabManager parent;
	private static final String INSTRUCTIONS = "Type in the name of the new object to be added"
			+ " and click OK";
	
	/**
	 * Constructor for an AddObjectWindow, which is used as an
	 * interface for the user to easily define the name of a new object
	 * that they wish to bind keys for.
	 * @param parentFrame The TabManager window that calls the AddObjectWindow class constructor
	 */
	public AddObjectWindow(TabManager parentFrame) {
		parent = parentFrame;
		initialize();
	}

	/**
	 * Initializes the creation of the window GUI by adding the instruction
	 * label, the text field that the user will enter text into,
	 * and the buttons that will be used to confirm or cancel the input
	 */
	private void initialize() {
		setTitle("New Object Definition");
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		JPanel newShell = new JPanel();
		newShell.setLayout(new BoxLayout(newShell, BoxLayout.Y_AXIS));
		newShell.add(createInfoLabel());
		Component input = createUserInput();
		newShell.add(input);
		newShell.add(createButtons(input));
		add(newShell);
		setVisible(true);
		pack();
		setLocationRelativeTo(null);
		input.setFocusable(true);	
	}

	/**
	 * Creates the information label to be added to the GUI,
	 * which gives the user instructions on how to use the window.
	 * @return The created info label
	 */
	private Component createInfoLabel() {
		JLabel infoLabel = new JLabel(INSTRUCTIONS);
		infoLabel.setAlignmentX(CENTER_ALIGNMENT);
		return infoLabel;
	}

	/**
	 * Creates the user input text field to be added to the GUI,
	 * which gives the user the ability to enter in a new name
	 * for an object.
	 * @return The created JTextField
	 */
	private Component createUserInput() {
		JTextField userInput = new JTextField();
		return userInput;
	}

	/**
	 * The shell method that creates the button panel to hold
	 * the ok and cancel buttons that the window will need
	 * @param input The input field, to extract the entered text
	 * @return The button panel that is created
	 */
	private Component createButtons(final Component input) {
		final AddObjectWindow thisWindow = this;
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		
		buttonPanel.add(makeOkButton(thisWindow, input));
		buttonPanel.add(makeCancelButton(thisWindow));

		return buttonPanel;
	}

	/**
	 * Makes the cancel button, which is to be added to the button panel
	 * @param thisWindow The window that will be closed by the cancel button
	 * @return The created cancel button
	 */
	private Component makeCancelButton(final AddObjectWindow thisWindow) {
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				thisWindow.dispose();	
			}
			
		});
		return cancelButton;
	}

	/**
	 * Makes the ok button, which is to be added to the button panel
	 * @param thisWindow The window that will be closed after the successful
	 * input of a new object name
	 * @param input The input text field that will be used to create a new
	 * object after the ok button has been clicked
	 * @return The created OK button
	 */
	private Component makeOkButton(final AddObjectWindow thisWindow, final Component input) {
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				parent.addObjectTab(((JTextField) input).getText());
				thisWindow.dispose();
				
			}
			
		});
		return okButton;
	}

}
