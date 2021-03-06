package vi51.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import vi51.environment.Environment;
import vi51.environment.EnvironmentEvent;

public class SpawnButtonB extends JButton implements MouseListener{

	private static final long serialVersionUID = 1L;
	Environment environment;

	public SpawnButtonB (String text, Environment environment){
		super(text);
		this.environment = environment;
		addMouseListener(this);
	}
	
	 public void mouseClicked(MouseEvent event) {  
		EnvironmentEvent e = new EnvironmentEvent("button","SpawnB");
		environment.fireEnvironmentChanged(e);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}