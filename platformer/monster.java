import java.util.Observer;
import java.util.Observable;

/* 
 * A monster is a movable object, and has therefore all pyisics working on it.
 * A monster does 8 damage to colliding objects that can be damaged
 * It's AI can be described as:
 *    move in the chosen direction (in the beginning left by default)
 *    if there is a block or a gap in front: turn around
 */

class Monster extends Movable {
	
	private boolean direction = false;
	private Sprite sprite = new Sprite("images/monster.png",0,0,1,1);
	
	public Monster(Game game){
		
		setSize(1,1);
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public boolean left(){
		return !direction;
	}
	public boolean right(){
		return direction;
	}
	public boolean jump(){
		return false;
	}
	public boolean fly(){
		return false;
	}
	
	public boolean isSolid(){
		return false;
	}
	
	public void update(Observable caller, Object data){
		if (data == null)
			return;
		
		updateCollisions();
		
		updatePhysics();
		if (NextToSolidObject(direction ? 0.1 : -0.1,0.0) || !NextToSolidObject(direction ? 0.5 : -0.5,0.2)){ // turn around if it is next to a block or gap
			direction = !direction;
		}
	}
	
	private boolean NextToSolidObject(double x, double y){
		move(x,y);
		boolean collision = solidCollision()!=null;
		move(-x,-y);
		return collision;
	}
	
	public int damage(){
		return 8;
	}
	
	public double getWalkForce(){
		return 0.08;
	}
	public double getJumpForce(){
		return 0.0;
	}
	public double getGroundFriction(){
		return 0.6;
	}
	public double getAirFrictionX(){
		return 0.9;
	}
	public double getAirFrictionY(){
		return 0.988;
	}
	public double getGravity(){
		return 0.016;
	}
	
}