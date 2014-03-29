package FrontEnd;

import java.awt.Color;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class LevelPanelComponent extends JPanel {
	LevelPanel levelPanel;
	private boolean isActive=false;
public LevelPanelComponent(Color c, String name, LevelPanel l) {
	super();
	levelPanel=l;
	setBackground(c);
TitledBorder title=BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),name);
setBorder(title);

setSize(200,500/5);
setPreferredSize(getSize());
setFocusable(true);
createMouseStuff();
}
public String toString(){
	return "wwoooo";
	
}

private void createMouseStuff(){
	addMouseListener(new MouseAdapter(){
		@Override
		public void mousePressed(MouseEvent e){
		}
		
		@Override
		public void mouseReleased(MouseEvent e){
			
		}
		
		@Override
		public void mouseEntered(MouseEvent e){
			if(getBackground()!=Color.RED)
			setBackground(Color.GREEN);
			revalidate();
			repaint();
		}
		
		@Override
		public void mouseExited(MouseEvent e){
			if(getBackground()!=Color.RED)
			setBackground(Color.BLUE);
			revalidate();
			repaint();
		}
		
		@Override
		public void mouseClicked(MouseEvent e){
			levelPanel.resetBackgrounds();
			setBackground(Color.RED);
			isActive=true;
			revalidate();
			repaint();
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
