import java.awt.Color;
import java.awt.Graphics;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

/* 
 * A Sprite object can either be an image that is drawn somwhere on the screen, or a colored rectangle.
 * Which of the two depents on the first argument to the constructor: if it is an integer, a color
 * will be made from this integer, and if it is a string, the file with that name is used as image.
 */

class Sprite {
	
	private double xmin, ymin, width, height;
	private Color color;
	private BufferedImage img;
	private boolean hasImg = false;
	
	public Sprite(int color, double xmin, double ymin, double width, double height){
		// in this case it will just be a colored rectangle
		this.color=new Color(color);
		this.xmin=xmin;
		this.ymin=ymin;
		this.width=width;
		this.height=height;
	}
	
	public Sprite(String fileName,double xmin, double ymin, double width, double height){
		// in this case, an image will actually be drawn
		this.xmin = xmin;
		this.ymin = ymin;
		this.width = width;
		this.height = height;
		
		try {
			img = ImageIO.read(new File(fileName));
			width = img.getWidth();
			height = img.getHeight();
		} catch (IOException e) {
			System.err.println("Error, couldn't load file '"+fileName+"': "+e);
		}
		
		hasImg = true;
		
		
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
	
	public boolean image(){
		return hasImg;
	}
	
	public Color getColor(){
		return color;
	}
	
	public BufferedImage getImage(){
		return img;
	}
	
}