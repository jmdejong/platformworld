import java.util.Observer;
import java.util.Observable;

/*
 * Every object that is placed in the map is a Placable.
 * A placable object has an x and y position, a width and height, a method telling whether it can interact, 
 * and if so, it should overwrite the interact method, a method telling whether it is solid.
 * 
 * All placable objects observe the step manager of the game object.
 */


abstract class Placable implements Observer{
	
	private double x, y;
	private double w, h;
	private CollisionManager collisionManager = null;

	
	public void putInMap(CollisionManager collisions){
		collisionManager = collisions;
		collisionManager.set(this);
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public double getWidth(){
		return w;
	}
	
	public double getHeight(){
		return h;
	}
	
	public void place(double x, double y){
		if (collisionManager != null)
			collisionManager.remove(this);
		this.x = x;
		this.y = y;
		if (collisionManager != null)
			collisionManager.set(this);
	}
	
	protected void setSize(double width, double height){
		if (collisionManager != null)
			collisionManager.remove(this);
		w = width;
		h = height;
		if (collisionManager != null)
			collisionManager.set(this);
	}
	
	public void removeFromMap(){
		collisionManager.remove(this);
	}
	
	public CollisionManager getCollisionManager(){
		return collisionManager;
	}
	
	public boolean collidesWith(Placable obj){
		double l1 = getX(), r1=getX()+getWidth(), t1 = getY(), b1 = getY()+getHeight();
		double l2 = obj.getX(), r2 = obj.getX()+obj.getWidth(), t2 = obj.getY(), b2 = obj.getY()+obj.getHeight();
		return !( l1>=r2 || l2>=r1 || t1>=b2 || t2>=b1 );
	}
	
	public abstract Sprite getSprite();
	
	public abstract boolean isSolid();
	
	public boolean interactable(){
		return false;
	}
	
	public void update(Observable caller, Object data){}
	
	public void interact(Actor obj){} 
	
	public int damage(){
		return 0;
	}
	
}