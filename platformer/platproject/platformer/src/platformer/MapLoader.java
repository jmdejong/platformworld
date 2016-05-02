package platformer;

import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

class MapLoader {
	
	private BufferedImage img = null;
	private int width, height;
	
	public MapLoader(String fileName){
		
		load(fileName);
		//System.out.println(width+" "+height);
	}
	
	public void load(String fileName){
		
		try {
			img = ImageIO.read(new File(fileName));
			// Should we also close the file?
			width = img.getWidth();
			height = img.getHeight();
		} catch (IOException e) {
			System.err.println("Error, couldn't load file '"+fileName+"': "+e);
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
	
// 	public static void main(String[] args){
// 		
// 	}
}
