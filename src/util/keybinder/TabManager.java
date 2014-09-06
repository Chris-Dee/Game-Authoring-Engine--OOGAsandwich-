package util.keybinder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private BindingExecutor executor = new BindingExecutor(
			"util.keybinder.BoundFunctions", "gameplayer.GamePlayerEngine");
	private ActionListener saveListener;
	private ActionListener addListener;
	private ActionListener clearAllListener;

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
				focus = createTab(shell, focusObject, objectCommands);
			}
			tabs.setSelectedComponent(focus);
		}
		shell.add(createButtonPanel());
		add(shell);
		setVisible(true);
		pack();
		setLocationRelativeTo(null);

	}

	/**
	 * Creates the window that will hold the tabs and button panels
	 * @return
	 * 			The shell panel that will hold the components for the window, and give content to the window
	 */
	private JPanel createWindow() {
		setTitle("Define User Input");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setFocusable(true);
		JPanel shell = new JPanel();
		shell.setLayout(new BoxLayout(shell, BoxLayout.Y_AXIS));
		return shell;
	}

	/**
	 * Handles the preloading of a Bindings file into the tab manager.  If there is an empty
	 * file, there is still the loadAndInitiate method, that handles the constant actions
	 * of transferring any information into the keyBindings map, and creating the standard
	 * universal controls panel
	 * @param shell
	 * 				The shell panel that will hold all of the tabs created
	 * @param focusObject
	 * 				The name of the specific object that was requested to have a binding given to it
	 * @return
	 * 				The tab that holds the object that is to be focused on
	 */
	private KeybindUtility preload(JPanel shell, String focusObject) {
		KeybindUtility focusTab = null;
		List<String> preloadObjects = new ArrayList<String>();
		loadAndInitiate(shell);
		for (Integer key : keyBindings.keySet()) {
			String[] splitString = keyBindings.get(key).split(",");
			KeybindUtility createdTab = null;
			if (!preloadObjects.contains(splitString[1])) {
				preloadObjects.add(splitString[1]);
				if (!splitString[1].equals("Universal")) {
					createdTab = createTab(shell, splitString[1], objectCommands);
				}
				if (focusObject != null
						&& createdTab.gameObject.equals(focusObject)) {
					focusTab = createdTab;
				}
			}
		}
		return focusTab;
	}
	/**
	 * Handles constant actions needed every time a file is preloaded
	 * @param shell
	 */
	@SuppressWarnings("static-access")
	private void loadAndInitiate(JPanel shell) {
		keyBindings.putAll(executor.loadMap());
		createTab(shell, "Universal", universalCommands);
	}
	/**
	 * Creates a particular tab from the KeybindUtility class to be added to the tab manager
	 * @param shell
	 * 				The shell panel that will hold the tab
	 * @param objectName
	 * 				The object that the tab is being created for
	 * @param commandList
	 * 				The list of commands that will be associated with the tab and object
	 * @return
	 * 				The tab that was created
	 */
	private KeybindUtility createTab(JPanel shell, String objectName, List<String> commandList) {
		KeybindUtility newTab = new KeybindUtility(objectName, commandList, this);
		allTabs.add(newTab);
		tabs.addTab(objectName, newTab);
		shell.add(tabs);
		return newTab;
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
		createListeners(this);
		buttonPanel.add(createButton("Save", LEFT_ALIGNMENT, saveListener));
		buttonPanel.add(createButton("Add Object", CENTER_ALIGNMENT, addListener));
		buttonPanel.add(createButton("Clear All", RIGHT_ALIGNMENT, clearAllListener));
		return buttonPanel;
	}
	
	/**
	 * Creates the listeners that will be added to buttons that are to be created for the button panel
	 * @param manager
	 * 				The TabManager parent frame that will hold the button panel
	 */
	private void createListeners(final TabManager manager) {
		saveListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveMap();
			}
		};
		addListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new AddObjectWindow(manager);
			}
		};
		clearAllListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				keyBindings.clear();
				for (int i = 0; i < allTabs.size(); i++) {
					allTabs.get(i).clearAll();
				}
			}
		};
	}
	
	/**
	 * Creates a button based on the input parameters to be added to the button panel
	 * @param buttonName
	 * 				The name that will be displayed on the button
	 * @param alignment
	 * 				Where the button should be aligned on the BoxLayout
	 * @param listener
	 * 				What listener will be attributed to the button
	 * @return
	 * 				The button that was created
	 */
	private JButton createButton(String buttonName, float alignment, ActionListener listener) {
		JButton button = new JButton(buttonName);
		button.setAlignmentX(alignment);
		button.addActionListener(listener);
		return button;
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
	 * This method allows the back-end to access the binding map created by the
	 * user
	 * 
	 * @return The keybinding map
	 */
	private void saveMap() {
		Properties properties = new Properties();
		FileOutputStream outstream;
		try {
			outstream = new FileOutputStream("src/util/keybinder/Bindings.txt");
			for (Map.Entry<Integer, String> entry : keyBindings.entrySet()) {
				properties.put(entry.getKey().toString(), entry.getValue());
			}
			properties.store(outstream, "Key Bindings");
		} catch (IOException e) {
			System.out.println("IOException");
		}

	}
}
