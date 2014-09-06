package util.keybinder;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class KeybindUtility extends JPanel {
	private static final String WAIT_STATUS = "    Click Set to Bind, Click Clear to Delete Binding    ";
	private static final String CHANGE_STATUS = "    Press the Key You Wish to Bind to Action    ";
	private static final String BOUND_STATUS = "   Key Already Bound.  Please Press Another Key  ";

	private JLabel statusLabel = new JLabel(WAIT_STATUS);
	private List<String> commandList;
	private TabManager parentFrame;
	protected String gameObject;
	private Map<Component, JLabel[]> componentBindingList = new HashMap<Component, JLabel[]>(); // JLabel[commandLabel, bindingLabel ]
	private ActionListener setListener;
	private ActionListener clearListener;

	/**
	 * A utility class to easily swap meaningful key inputs for the editor as
	 * well as for the game being created.
	 */
	public KeybindUtility(String object, List<String> commands,
			TabManager manager) {
		gameObject = object;
		commandList = commands;
		parentFrame = manager;
		initialize();
	}

	/**
	 * Creates the GUI for the Keybind Utility to let the user easily map the
	 * keyboard inputs
	 */
	private void initialize() {
		JComponent shell = new JPanel();
		shell.setLayout(new BoxLayout(shell, BoxLayout.Y_AXIS));
		createBindingPanels(shell);
		add(shell);
		setVisible(true);

		InputMap im = (InputMap) UIManager.get("Button.focusInputMap");
		im.put(KeyStroke.getKeyStroke("pressed SPACE"), "none");
		im.put(KeyStroke.getKeyStroke("released SPACE"), "none");

	}

	/**
	 * Generates a constantly present key listener that will update a KeyEvent
	 * buffer variable with the most recently released key.
	 */
	private void generateButtonListener(final JLabel label,
			final String command, final JButton button) {
		button.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
				// Do nothing - must be present for interface
			}

			public void keyReleased(KeyEvent e) {
				boolean alreadyBound = false;

				for (Integer event : parentFrame.keyBindings.keySet()) {
					if (event == e.getKeyCode()) {
						alreadyBound = true;
					}
				}
				if (alreadyBound) {
					statusLabel.setText(BOUND_STATUS);
				} else {
					parentFrame.keyBindings.put(e.getKeyCode(), command + ","
							+ gameObject);
					label.setText(KeyEvent.getKeyText(e.getKeyCode())
							+ "        ");
					statusLabel.setText(WAIT_STATUS);
					alreadyBound = false;
				}
			}

			public void keyTyped(KeyEvent e) {
				// Do nothing - must be present for interface
			}
		});

	}

	/**
	 * Generates the GUI panels for the key-binding interface. This GUI is
	 * composed of individual panels that all hold the labels for the associated
	 * command, keybinding, and set and clear buttons to set a new binding or
	 * clear an existing one.
	 * 
	 * @param framePanel
	 *            The panel that will hold all of the individual component
	 *            panels
	 */
	private void createBindingPanels(final JComponent framePanel) {
		for (final String command : commandList) {
			JPanel componentPanel = new JPanel();
			componentPanel.setLayout(new GridLayout(1, 4, 10, 10));
			JLabel commandLabel = new JLabel(command);
			JLabel binding = createBinding(gameObject, command);
			createListeners(binding, command);
			JButton setButton = new JButton("Set");
			generateButtonListener(binding, command, setButton);
			setButton.addActionListener(setListener);
			JButton clearButton = new JButton("Clear");
			clearButton.addActionListener(clearListener);
			componentPanel.add(commandLabel);
			componentPanel.add(binding);
			componentPanel.add(setButton);
			componentPanel.add(clearButton);
			componentBindingList.put(componentPanel, new JLabel[] {commandLabel, binding});
			framePanel.add(componentPanel);
		}
		statusLabel.setAlignmentX(CENTER_ALIGNMENT);
		framePanel.add(statusLabel);

	}

	/**
	 * Creates a binding label for a particular object panel component
	 * @param object
	 * 				The name of the object the binding is being created for
	 * @param command
	 * 				The command that is being bound to 
	 * @return
	 * 				The created binding label
	 */
	public JLabel createBinding(String object, String command) {
		JLabel bindingLabel = new JLabel();
		boolean bound = false;
		for (Integer key : parentFrame.keyBindings.keySet()) {
			if (parentFrame.keyBindings.get(key).equals(command + "," + object)) {
				bindingLabel.setText(KeyEvent.getKeyText(key));
				bound = true;
			}
		}
		if (!bound) {
			bindingLabel.setText("<>");
		}
		return bindingLabel;
	}
	
	/**
	 * Creates the action listeners that are used for a particular panel component
	 * @param binding
	 * 				The binding label that was created for a component
	 * @param command
	 * 				The command associated with the current panel component
	 */
	private void createListeners(final JLabel binding,
			final String command) {
		setListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				prepareSet(command);
				statusLabel.setText(CHANGE_STATUS);
			}

		};
		clearListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Integer keyToRemove = -1;
				binding.setText("<>");
				for (Integer key : parentFrame.keyBindings.keySet()) {
					if (parentFrame.keyBindings.get(key).startsWith(command)) {
						keyToRemove = key;
					}
				}
				removeKey(keyToRemove);
				statusLabel.setText(WAIT_STATUS);
			}

		};
	}

	/**
	 * Clears out the previous binding from the binding map when a new command is to be set
	 * @param command
	 * 				Defines the particular command that will be bound to
	 */
	protected void prepareSet(String command) {
		Integer keyToRemove = -1;
		for (Integer key : parentFrame.keyBindings.keySet()) {
			if (parentFrame.keyBindings.get(key).equals(
					command + "," + gameObject)) {
				keyToRemove = key;
			}
		}
		removeKey(keyToRemove);

	}
	
	/**
	 * Clears all of the bindings from the particular tab by iterating through all of the components present
	 */
	public void clearAll() {
		for(Component panel : componentBindingList.keySet()) {
			componentBindingList.get(panel)[1].setText("<>");
		}
	}

	/**
	 * Removes a particular item from the keybindings map
	 * @param keyToRemove
	 * 				The defined key that is currently bound and is to be removed
	 */
	private void removeKey(Integer keyToRemove) {
		if (keyToRemove != -1) {
			parentFrame.keyBindings.remove(keyToRemove);
		}
	}

}
