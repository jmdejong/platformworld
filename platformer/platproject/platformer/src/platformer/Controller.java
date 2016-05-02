package platformer;

import java.util.Observable;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class InputController extends Observable implements KeyListener{
	private boolean[] down = new boolean[255];
	
	public void keyPressed(KeyEvent e){
		down[e.getKeyCode()]=true;
		//System.out.println(e.getKeyCode());
	}
	public void keyTyped(KeyEvent e){
		
	}
	public void keyReleased(KeyEvent e){
		down[e.getKeyCode()]=false;
	}
	
	public boolean isDown(int keyCode){
		return down[keyCode];
	}
}