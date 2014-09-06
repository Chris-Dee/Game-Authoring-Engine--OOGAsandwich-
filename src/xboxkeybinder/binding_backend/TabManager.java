package xboxkeybinder.binding_backend;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class TabManager extends JFrame {

	private List<String> universalCommands;
	private List<String> objectCommands;
	private JTabbedPane tabs = new JTabbedPane();
	private List<KeybindUtility> allTabs = new ArrayList<KeybindUtility>();
	protected Map<Integer, String> keyBindings = new HashMap<Integer, String>();

	/**
	 * The constructor for the TabManager window, which is the shell window for
	 * all of the object tabs that will require key bindings. Serves as the
	 * interface between individual object control and the game playing
	 * environment.
	 * 
	 * @param universalList
	 *            A list of universal commands to be bound, which affect the
	 *            full environment
	 * @param commandList
	 *            A list of commands that will be applied to each new object
	 *            that is created by the interface
	 */
	public TabManager(List<String> universalList, List<String> commandList,
			String focusObject) {
		universalCommands = universalList;
		objectCommands = commandList;

		initialize(focusObject);

	}

	/**
	 * Creates the GUI that the user will use to set and clear bindings for all
	 * of the objects that will be created by the interface.
	 */
	private void initialize(String focusObject) {
		JPanel shell = createWindow();
		KeybindUtility focus = preload(shell, focusObject);

		if (focusObject != null) {
			if (focus == null) {
				focus = createObjectTab(shell, focusObject);
			}
			tabs.setSelectedComponent(focus);
		}

		shell.add(createButtonPanel());
		add(shell);
		setVisible(true);

		pack();
		setLocationRelativeTo(null);

	}

	private KeybindUtility createUniversalTab(JPanel shell) {
		KeybindUtility newTab = new KeybindUtility("Universal",
				universalCommands, this);
		allTabs.add(newTab);
		tabs.addTab("Universal", newTab);
		shell.add(tabs);
		return newTab;
	}

	private KeybindUtility createObjectTab(JPanel shell, String objectName) {
		KeybindUtility newTab = new KeybindUtility(objectName, objectCommands,
				this);
		allTabs.add(newTab);
		tabs.addTab(objectName, newTab);
		shell.add(tabs);
		return newTab;

	}

	private KeybindUtility preload(JPanel shell, String focusObject) {
		KeybindUtility focusTab = null;
		Map<Integer, String> boundCommands = BindingExecutor.loadMap();
		keyBindings.putAll(boundCommands);
		
		boolean foundUniversal = false;
		List<String> preloadObjects = new ArrayList<String>();
		for (Integer key : keyBindings.keySet()) {
			
			if (keyBindings.get(key).endsWith("Universal")
					&& foundUniversal == false) {
				foundUniversal = true;
				System.out.println(key);
				preloadObjects.add("Universal");
				createUniversalTab(shell);
			} else {
				String[] splitString = keyBindings.get(key).split(",");
				if (!preloadObjects.contains(splitString[1])) {
					preloadObjects.add(splitString[1]);
					KeybindUtility createdTab = createObjectTab(shell,
							splitString[1]);
					if (focusObject != null
							&& createdTab.gameObject.equals(focusObject)) {
						System.out.println("Setting focus");
						focusTab = createdTab;
					}
				}
			}
		}
		
		if(!foundUniversal) {
			createUniversalTab(shell);
		}

		return focusTab;
	}

	private JPanel createWindow() {
		setTitle("Define User Input");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setFocusable(true);
		JPanel shell = new JPanel();
		shell.setLayout(new BoxLayout(shell, BoxLayout.Y_AXIS));
		return shell;

	}

	/**
	 * Creates the button panel that will store the save and add buttons, which
	 * will either save the bindings to a text file, or add a new object to the
	 * tabbed environment that will allow the user to bind commands to a new
	 * object.
	 * 
	 * @return The created button panel
	 */
	private JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(createSaveButton());
		buttonPanel.add(createAddButton());
		buttonPanel.add(createClearAllButton());
		return buttonPanel;
	}

	private Component createClearAllButton() {
		JButton clearAllButton = new JButton("Clear All");
		clearAllButton.setAlignmentX(RIGHT_ALIGNMENT);
		createClearAllListener(clearAllButton, this);
		return clearAllButton;
	}

	private void createClearAllListener(JButton clearAllButton,
			TabManager tabManager) {
		clearAllButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				keyBindings.clear();
				for(int i = 0; i < allTabs.size(); i++) {
					allTabs.get(i).clearAll();
				}
				
			}
			
		});

		
	}

	/**
	 * Creates the add object button, which is used to call the AddObjectWindow
	 * and allow a user to define a new object via object name.
	 * 
	 * @return The created add button
	 */
	private Component createAddButton() {
		JButton addButton = new JButton("Add Object");
		addButton.setAlignmentX(CENTER_ALIGNMENT);
		createAddListener(addButton, this);
		return addButton;
	}

	/**
	 * Creates the action listener for the add button, which is what calls the
	 * AddObjectWindow
	 * 
	 * @param addButton
	 *            The button the listener is being attached to
	 * @param manager
	 *            The manager window, which serves as the parent frame for the
	 *            new AddObjectWindow
	 */
	private void createAddListener(JButton addButton, final TabManager manager) {
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new AddObjectWindow(manager);

			}

		});

	}

	/**
	 * Takes in user input and adds a new tabbed pane to the TabManager that is
	 * attached to the object represented by that input.
	 * 
	 * @param text
	 *            The name of the object that is being created
	 */
	public void addObjectTab(String text) {
		KeybindUtility newTab = new KeybindUtility(text, objectCommands, this);
		allTabs.add(newTab);
		tabs.addTab(text, newTab);
		pack();
	}

	/**
	 * Creates the save button, the user interface object that is used to signal
	 * the completion of binding and prepares the program to save the entered
	 * information
	 * 
	 * @return The created save button
	 */
	private JButton createSaveButton() {
		JButton saveButton = new JButton("Save");
		saveButton.setAlignmentX(LEFT_ALIGNMENT);
		createSaveListener(saveButton);
		return saveButton;

	}

	/**
	 * Creates the listener for the save button, which triggers the saveMap
	 * method to write the keyBindings map to a text file
	 * 
	 * @param saveButton
	 *            The button the listener is being attached to
	 */
	private void createSaveListener(JButton saveButton) {
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveMap();

			}

		});
	}

	/**
	 * This method allows the back-end to access the binding map created by the
	 * user
	 * 
	 * @return The keybinding map
	 */
	private void saveMap() {
		System.out.println("saving");

		Properties properties = new Properties();
		FileOutputStream outstream;
		try {
			outstream = new FileOutputStream("src/xboxkeybinder/Bindings.txt");
			for (Map.Entry<Integer, String> entry : keyBindings.entrySet()) {
				properties.put(entry.getKey().toString(), entry.getValue());
			}
			properties.store(outstream, "Key Bindings");
		} catch (IOException e) {
			System.out.println("IOException");
		}

	}
}
