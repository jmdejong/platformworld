import java.util.Set;
import java.util.HashSet;

/* 
 * Any object that inherits from Movable will be stopped by solid objects.
 * These objects also have a gravity and friction on both the x and y axis.
 * Furthermore, these objects can move left, right and jump
 * The methods for going left, going right, jumping and flying return whether the object wants to take these actions
 * The UpdatePhysics method will try to make this happen
 * Flying means jumping while in the air
 * 
 * To test where it can move collision-free, the object has some methods to get the colliding objects
 */

abstract class Movable extends Placable {
	
	
	private double xSpeed = 0;
	private double ySpeed = 0;
	private boolean onground = false;
	
	private Set<Placable> closeObjects = new HashSet<Placable>();
	
	public abstract double getWalkForce();
	public abstract double getJumpForce(); 
	public abstract double getGroundFriction();
	public abstract double getAirFrictionX(); // the horizontal air frictionhas not been used. Currently, the ground friction is used for this.
	public abstract double getAirFrictionY();
	public abstract double getGravity();
	
	public abstract boolean left();
	public abstract boolean right();
	public abstract boolean jump();
	public abstract boolean fly();
	
	
	public void updateCollisions(){
		closeObjects = getCollisionManager().get(getX()-1,getY()-1,getWidth()+2,getHeight()+2);
	}
	
	public Set<Placable> getCloseObjects(){
		return closeObjects;
	}
	
	public Placable solidCollision(){
		for (Placable o : closeObjects){
			if (this.collidesWith(o) && o.isSolid() && this != o){
				return o;
			}
		}
		return null;
	}
	
	public void updatePhysics(){
		
		xSpeed += (right() ? getWalkForce() : 0.0) - (left() ? getWalkForce() : 0.0);
		xSpeed *= getGroundFriction();
		
		move(xSpeed,0); // try to move on the x axis
		Placable o = solidCollision();
		if (o != null){ // if blocked, go to the closest free position
			if (xSpeed>0)
				place(o.getX() - getWidth(), getY());
			else 
				place(o.getX() + o.getWidth(), getY());
		}
		
		
		ySpeed += getGravity();
		ySpeed *= getAirFrictionY();
		ySpeed = ((jump() && onground) || fly()) ? -getJumpForce() : ySpeed;
		
		onground = false;
		
		move(0,ySpeed); // try to move on the y axis
		o = solidCollision();
		if (o != null){ // if blocked, go to the closest free position
			if (ySpeed>0){
				place(getX(), o.getY()-getHeight());
				onground = true;
			} else 
				place(getX(),o.getY()+o.getHeight());
			ySpeed = 0;
		}
		
		
	}
}