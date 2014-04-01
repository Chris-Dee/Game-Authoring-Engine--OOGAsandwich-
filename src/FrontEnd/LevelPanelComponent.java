package FrontEnd;

import java.awt.Button;
import java.awt.Color;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class LevelPanelComponent extends JPanel {
	LevelPanel levelPanel;
	public static final Color ACTIVE_COLOR=Color.RED;
	public static final Color HOVER_COLOR=Color.GREEN;
	public static final Color NORMAL_COLOR=Color.BLUE;
	private boolean isActive=false;
public LevelPanelComponent(Color c, String name, LevelPanel l) {
	super();
	levelPanel=l;
	setBackground(c);
	//Level level;
TitledBorder title=BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),name);
setBorder(title);

setSize(200,500/5);
setPreferredSize(getSize());
setFocusable(true);
createMouseActions(this);
}
public String toString(){
	return "wwoooo";
	
}

private void createMouseActions(LevelPanelComponent level){
	final LevelPanelComponent levels=level;
	addMouseListener(new MouseAdapter(){
		@Override
		public void mousePressed(MouseEvent e){
		}
		
		@Override
		public void mouseReleased(MouseEvent e){
			
		}
		
		@Override
		public void mouseEntered(MouseEvent e){
			if(getBackground()!=ACTIVE_COLOR)
			setBackground(HOVER_COLOR);
			revalidate();
			repaint();
		}
		
		@Override
		public void mouseExited(MouseEvent e){
			if(getBackground()!=ACTIVE_COLOR)
			setBackground(NORMAL_COLOR);
			revalidate();
			repaint();
		}
		
		@Override
		public void mouseClicked(MouseEvent e){
			levelPanel.resetBackgrounds();
			setBackground(ACTIVE_COLOR);
			isActive=true;
			revalidate();
			repaint();
			if(e.getClickCount()==2){
				setBackground(ACTIVE_COLOR);
				isActive=false;
				LevelEditorWindow j=new LevelEditorWindow(levels);
				j.setVisible(true);
			}
		}
		
	});
}
public void setActive(boolean b){
	isActive=b;
}
public boolean isActive(){
	return isActive;
}
}
