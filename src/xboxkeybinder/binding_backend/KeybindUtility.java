package xboxkeybinder.binding_backend;

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

import ch.aplu.xboxcontroller.XboxController;
import ch.aplu.xboxcontroller.XboxControllerAdapter;
import java.awt.Component;
;@SuppressWarnings("serial")
public class KeybindUtility extends JPanel {
	private static final String WAIT_STATUS = "    Click Set to Bind, Click Clear to Delete Binding    ";
	private static final String CHANGE_STATUS = "    Press the Key You Wish to Bind to Action    ";
	private static final String BOUND_STATUS = "   Key Already Bound.  Please Press Another Key  ";

	private JLabel statusLabel = new JLabel(WAIT_STATUS);
	private List<String> commandList;
	private TabManager parentFrame;
	protected String gameObject;
	private Map<Component, JLabel[]> componentBindingList = new HashMap<Component, JLabel[]>(); // JLabel[commandLabel, bindingLabel ] 
	public Map<Integer, String> keyBindings = new HashMap<Integer, String>();
	public Map<JButton, String> buttonToCommandMap = new HashMap<JButton, String>();
	private Map<JButton, JLabel> buttonMap = new HashMap<JButton, JLabel>();
	private XboxController xc=new XboxController();
	private XboxBinderAdapter myAdapter;
	//protected Map<Integer, String> keyBindings = new HashMap<Integer, String>();

	/**
	 * A utility class to easily swap meaningful key inputs for the editor as
	 * well as for the game being created.
	 */
	public KeybindUtility(String object, List<String> commands, TabManager manager) {
		gameObject = object;
		commandList = commands;
		parentFrame = manager;
		initialize();
		 myAdapter=new XboxBinderAdapter();
		myAdapter.setKeys(this);
		xc.addXboxControllerListener(myAdapter);
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
		xc.setLeftThumbDeadZone(0.2);
		xc.setRightThumbDeadZone(0.2);
		InputMap im=(InputMap) UIManager.get("Button.focusInputMap");
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
				int key=e.getKeyCode();
				if (isBoundAlready(key)) {
					statusLabel.setText(BOUND_STATUS);
				} else {
					parentFrame.keyBindings.put(e.getKeyCode(), command+","+gameObject);
					label.setText(KeyEvent.getKeyText(e.getKeyCode())
							+ "        ");
					statusLabel.setText(WAIT_STATUS);
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
			componentPanel.setLayout(new BoxLayout(componentPanel,
					BoxLayout.X_AXIS));
			JLabel commandLabel = new JLabel(command + "    ");
			//JLabel binding = new JLabel("<>      ");
			JLabel binding = createBinding(gameObject,command);
			
			JButton setButton = new JButton("Set");
			buttonMap.put(setButton, binding);
			buttonToCommandMap.put(setButton, command);
			createSetListener(setButton, binding, command);
			JButton clearButton = new JButton("Clear");
			clearButton.setFocusable(false);
			createClearListener(clearButton, binding, command);
			componentPanel.add(commandLabel);
			componentPanel.add(binding);
			componentPanel.add(setButton);
			componentPanel.add(clearButton);
			JLabel[] mapArray = {commandLabel, binding};
			componentBindingList.put(componentPanel, mapArray);
			framePanel.add(componentPanel);
		}
		statusLabel.setAlignmentX(CENTER_ALIGNMENT);
		framePanel.add(statusLabel);
		
	}
	public JLabel createBinding(String object, String command) {
		JLabel bindingLabel = new JLabel();
		boolean bound = false;
		for (Integer key : parentFrame.keyBindings.keySet()) {
			if (parentFrame.keyBindings.get(key).equals(command + "," + object)) {
				bindingLabel.setText(KeyEvent.getKeyText(key) + "        ");
				bound = true;
			}
		}
		if (!bound) {
			bindingLabel.setText("<>        ");
		}
		return bindingLabel;
	}

	public void resetComponentText(String binding, String command) {
		for(Component part : componentBindingList.keySet()) {

			if(componentBindingList.get(part)[0].getText().startsWith(command)) {
				componentBindingList.get(part)[1].setText(binding+ "        ");
			}
		}
	}
	/**
	 * Creates a listener for a particular panel's CLEAR button
	 * 
	 * @param clearButton
	 * @param binding
	 *            The label that describes the bound key event
	 * @param command
	 *            The label that describes the method the panel controls
	 */
	private void createClearListener(JButton clearButton, final JLabel binding,
			final String command) {
		clearButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Integer keyToRemove = -1;
				binding.setText("<>      ");
				//System.out.println(command);
				for(Integer key : parentFrame.keyBindings.keySet()) {
					if(parentFrame.keyBindings.get(key).startsWith(command)) {
						keyToRemove = key;
					}
					//System.out.println(keyToRemove);
				}
				statusLabel.setText(WAIT_STATUS);
				removeKey(keyToRemove);
			}

		});

	}

	/**
	 * Creates a listener for a particular panel's CLEAR button
	 * 
	 * @param setButton
	 * @param binding
	 *            The label that describes the bound key event
	 * @param command
	 *            The label that describes the method the panel controls
	 */
	private void createSetListener(final JButton setButton,
			final JLabel binding, final String command) {
		generateButtonListener(binding, command, setButton);
		setButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				prepareSet(command);
				statusLabel.setText(CHANGE_STATUS);
			}

		});
	}
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
	public void clearAll() {
		for(Component panel : componentBindingList.keySet()) {
			componentBindingList.get(panel)[1].setText("<>"+ "        ");
		}
	}

	private void removeKey(Integer keyToRemove) {
		if (keyToRemove != -1) {
			parentFrame.keyBindings.remove(keyToRemove);
		}
	}
	public boolean isBoundAlready(int keyCode) {
		boolean alreadyBound = false;
		//System.out.println(keyBindings);
		for (Integer event : keyBindings.keySet()) {
			if (event == keyCode) {
				alreadyBound = true;
			}
		}
		return alreadyBound;
	}
	JButton findFocused(){
		for(JButton b:buttonMap.keySet()){
			if(b.isFocusOwner())
				return b;
		}
		return null;
		}
	public void setXboxBinding(boolean alreadyBound, int keyCode,String command){
		JLabel label=buttonMap.get(findFocused());
		if(label!=null)
		label.getText();
		if (alreadyBound) {
			statusLabel.setText("Key Already Bound");
			//button.setFocusable(false);
		} else {
			if(label!=null){
			parentFrame.keyBindings.put(keyCode, command+","+gameObject);
			keyBindings.put(keyCode, command+","+gameObject);
			label.setText(myAdapter.getKeyList().get(keyCode-XboxBinderAdapter.keyOffset)
					+ "        ");
			statusLabel.setText(WAIT_STATUS);
			
		}
			}

	}	
}

