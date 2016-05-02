package platformer;

//not actually a sprite, just called like that because it does what a sprite should do: hold some data for the drawing
//mayby we should change it later into a real sprite
class Sprite {
	
	private double xmin, ymin, width, height;
	private int color;
	
	public Sprite(int color, double xmin, double ymin, double width, double height){
		this.color=color;
		this.xmin=xmin;
		this.ymin=ymin;
		this.width=width;
		this.height=height;
	}
	
	public double getXmin(){
		return xmin;
	}
	public double getYmin(){
		return ymin;
	}
	
	public double getWidth(){
		return width;
	}
	public double getHeight(){
		return height;
	}
	
	public int getColor(){
		return color;
	}
}