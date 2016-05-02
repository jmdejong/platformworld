package platformer;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.Observer;
import java.util.Observable;

class Player extends Controllable implements Observer{
	
	private Sprite sprite = new Sprite(0x0000ff,0,0,1,2);
	
	public Player(double x, double y){
		place(x,y);
	}
	
	public Player(){
		place(0,0);
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	
	public void update(Observable caller, Object data){
		if (data == null)
			return;
		InputController inputData = (InputController) data;
		
		double xMovement = (inputData.isDown(68) ? 0.1 : 0.0) - (inputData.isDown(65) ? 0.1 : 0.0);
		double yMovement = (inputData.isDown(83) ? 0.1 : 0.0) - (inputData.isDown(87) ? 0.1 : 0.0);
		move(xMovement,yMovement);
		//System.out.println(inputData.isDown(65));
	}
	
// 	// volgens de lecture slides kunnen we de keylisteners beter in een subclass van Player zetten
// 	public void keyPressed(KeyEvent e){
// 		switch (e.getKeyChar()){
// 			case 'a':
// 				move(-0.1,0);
// 				break;
// 			case 'w':
// 				move(0,-0.1);
// 				break;
// 			case 'd':
// 				move(0.1,0);
// 				break;
// 			case 's':
// 				move(0,0.1);
// 				break;
// 			default:
// 				return;
// 		}
// 	}
// 	public void keyTyped(KeyEvent e){
// 		
// 	}
// 	public void keyReleased(KeyEvent e){
// 		
// 	}
}
