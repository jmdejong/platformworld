import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

/* 
 * The MapLoader loads an image file.
 * It will can then return the color and transparency value of a pixel as an integer
 * 
 */

class MapLoader {
	
	private BufferedImage img = null;
	private int width, height;
	
	public MapLoader(String fileName){
		
		load(fileName);
	}
	
	public void load(String fileName){
		
		try {
			img = ImageIO.read(new File(fileName));
			width = img.getWidth();
			height = img.getHeight();
		} catch (FileNotFoundException e){
			System.err.println("Unable to open file: '"+fileName+"': "+e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public int get(int x, int y){
		return img.getRGB(x,y);
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void print(){
		String output = "";
		for (int y=0; y<height; y++){
			for (int x=0; x<width; x++){
				output += Integer.toHexString(get(x,y))+" ";
			}
			output += "\n";
		}
		System.out.println(output);
	}
	
}