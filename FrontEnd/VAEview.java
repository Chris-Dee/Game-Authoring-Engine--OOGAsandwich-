package FrontEnd;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.ScrollPane;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VAEview extends JFrame {
public VAEview(){
	initialize();
}
	public static void main(String[] args) {
	VAEview v=new VAEview();
	v.setMainPanel();
	}

	public void initialize(){
		setTitle("OOGASandwich Game Editor");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setVisible(true);
		setLayout(new BorderLayout());
	}
	public void setMainPanel(){
		JPanel mainPanel=(JPanel) this.getContentPane();
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		JPanel editPanel=new JPanel(new FlowLayout());
		ScrollPane scroller=new ScrollPane();
		scroller.setSize(400,600);
		LevelPanel l=new LevelPanel();
		scroller.add(new LevelPanel());
		editPanel.add(scroller,BorderLayout.WEST);
		editPanel.add(new ObjectPanel(l),BorderLayout.EAST);
		//mainPanel.add(new OptionsPanel(),BorderLayout.NORTH);
		mainPanel.add(editPanel);
		pack();
	}
}
