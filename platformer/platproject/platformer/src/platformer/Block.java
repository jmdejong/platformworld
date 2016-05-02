package platformer;

class Block extends Placable {
	
	private int xOffset = 0, yOffset = 0, width = 1, height = 1;
	private Sprite sprite = new Sprite(0x00aa00,0,0,1,1);
	
	public Block(double x, double y){
		place(x, y);
	}
	
	public Block(){
		this(0,0);
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	
// 	public void draw(Graphics g){
// 		g.setColor(color);
// 		g.fillRect(x*32-xOffset,y*32-yOffset,width,height);
// 	}
}
