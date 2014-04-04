package gameAuthoringEnvironment.levelStatsEditor;

import gameAuthoringEnvironment.frontEnd.LevelPanel;
import gameAuthoringEnvironment.frontEnd.PanelFactory;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
//ComboBox to choose backgrounds
//Backgrounds and names are stored in  LevelPanelComponents
//Can update this slightly when level objects are introduced
//High level--uses Map to store names to show up in combo box vs. filepaths
//uses one static method to update the state of the ComboBox dynamically.

//Adding new images is having problems because JGame is being a little bitch.

public class BackgroundChooser extends JPanel {
	LevelPanel levels;
	private final String delimiter=" ";
	private final String initialSelection="White";
	private final String defaultFile="src/gameAuthoringEnvironment/levelStatsEditor/Resources/InitBG";
	Map<String,String> backgroundMap=new TreeMap<String,String>();
	private String uploadedFile;
	private JTextField bgName;
	//TODO we may want to not amek this static. Then again...there will only ever be one
	private static JComboBox model;
	public BackgroundChooser(LevelPanel l) throws FileNotFoundException{
		super();
		levels=l;
		backgroundMap=initializeImageMap(new File(defaultFile));
		makeBackgroundChooser(this);
		makeFileChooserButton(this);
		}
	private Map<String,String> initializeImageMap(File file) throws FileNotFoundException{
		Map<String,String> bgMap=new TreeMap<String, String>();
		Scanner s=new Scanner(file);
		while(s.hasNext()){
			String[] str=s.nextLine().split(delimiter);
			bgMap.put(str[0],str[1]);
		}
		return bgMap;
	}
	public void makeBackgroundChooser(JPanel homePanel){
		homePanel.setLayout(new BoxLayout(homePanel,BoxLayout.Y_AXIS));
		model=new JComboBox(backgroundMap.keySet().toArray());
		setSelectionToDefault();
		model.addItemListener(new ItemChangeListener());
		homePanel.add(new JLabel("Select Background Image"));
		homePanel.add(model);
		homePanel.add(PanelFactory.makeVerticalSpacerPanel(15));

	}
	public void setSelectionToDefault(){
		model.setSelectedItem(initialSelection);
	}
	public void makeFileChooserButton(JPanel homePanel){
		JPanel editorPanel=new JPanel();
		homePanel.add(new JLabel("Add New Background Image"));
		editorPanel.setLayout(new BoxLayout(editorPanel,BoxLayout.X_AXIS));
		editorPanel.add(new JLabel("BG name"));
		bgName=new JTextField(0);
		editorPanel.add(bgName);
		homePanel.add(editorPanel);
		JButton uploadImage=new JButton("upload");
		uploadImage.addActionListener(new FileChoose(this));
		editorPanel.add(uploadImage);
		JButton addImage=new JButton("Add Image");
		addImage.addActionListener(new AddImage(this));
		homePanel.add(addImage);
	}
	public void setBackgroundToSelected(){
		String newBG=backgroundMap.get(model.getSelectedItem());
		levels.findActivePanel().changeDefaultBackground(newBG, (String)model.getSelectedItem());
	}
	public static void setSelectedToBackground(String backgroundName){
		model.setSelectedItem(backgroundName);
	}
	public class FileChoose implements ActionListener {
		JPanel home;

		public FileChoose(JPanel homePanel){
			home=homePanel;
		}
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser=new JFileChooser();
			int returnVal = fileChooser.showOpenDialog(home);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String file = fileChooser.getSelectedFile().getAbsolutePath();
				uploadedFile=file;
				try {
					// return the file path 
				} catch (Exception ex) {
					//TODO deal with exception
				}
			}  
		}
	}
	public class AddImage implements ActionListener{
		JPanel home;
		public AddImage(JPanel homePanel){
			home=homePanel;
		}
		public void actionPerformed(ActionEvent e) {
			backgroundMap.put(bgName.getText(),uploadedFile);
			System.out.println(backgroundMap);
			model.addItem(bgName.getText());
		}

	}
	class ItemChangeListener implements ItemListener{
	    public void itemStateChanged(ItemEvent event) {
	       if (event.getStateChange() == ItemEvent.SELECTED) {
	          setBackgroundToSelected();
	          // do something with object
	       }
	    }       
	}
}
