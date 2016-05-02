package platformer;

abstract class Placable{
	
	private double x, y;
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public void place(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public void move(double x, double y){
		this.x += x;
		this.y += y;
	}
	
	public abstract Sprite getSprite();
	
}