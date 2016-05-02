import java.util.Observable;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

/*
 * The InputController is used to keep track which keyboard keys are pressed and down.
 * The isDown method returns whether the key with the given key code is down at the moment,
 * and the isPressed method returns whether the key with the given key code has been pressed
 * since the last time that isPressed was called for that key.
 * 
 */

class InputController extends Observable implements KeyListener{
	private boolean[] down = new boolean[255];
	private boolean[] pressed = new boolean[255];
	
	public void keyPressed(KeyEvent e){
		down[e.getKeyCode()]=true;
		pressed[e.getKeyCode()]=true;
	}
	public void keyTyped(KeyEvent e){
		
	}
	public void keyReleased(KeyEvent e){
		down[e.getKeyCode()]=false;
	}
	
	public boolean isDown(int keyCode){
		return down[keyCode];
	}
	
	public boolean isPressed(int keyCode){
		boolean p = pressed[keyCode];
		pressed[keyCode] = false;
		return p;
	}
}