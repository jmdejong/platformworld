import java.util.Set;
import java.util.HashSet;
/*
 * A CollisionManager object keeps track of which objects are at a location.
 * 
 * The set method stores the object in a set on every square that the object (partially) covers.
 * The remove method removes the object from that area. It is important that the location and size of
 * the object don't change between setting this object on the field and removing it.
 * 
 * The get method returns a set of object that could in the specified area.
 * This set may contain objects that are not exactly in the area, but if an object is in the area, it will be in the set
 * This way, for exact collision checking not all the objects in the map have to be checked, but only the objects that are near
 * 
 */
class CollisionManager {
	private int width, height;
	
	private Set<Placable>[][] field;
	
	public CollisionManager(int w, int h){
		width = w;
		height = h;
		field = new Set[width][height];
		for (int i=0; i<width; i++){
			for (int j=0; j<height; j++){
				field[i][j] = new HashSet<Placable>();
			}
		}
	}
	
	public void set(Placable o){
		for (int i = Math.max((int) o.getX(), 0); i < Math.min(Math.ceil(o.getX()+o.getWidth()),width); i++){
			for (int j = Math.max((int) o.getY(), 0); j < Math.min(Math.ceil(o.getY()+o.getHeight()),height); j++){
				field[i][j].add(o);
			}
		}
	}
	
	public void remove(Placable o){
		for (int i = Math.max((int) o.getX(), 0); i < Math.min(Math.ceil(o.getX()+o.getWidth()),width); i++){
			for (int j = Math.max((int) o.getY(), 0); j < Math.min(Math.ceil(o.getY()+o.getHeight()),height); j++){
				field[i][j].remove(o);
			}
		}
	}
	
	public Set<Placable> get(double x, double y, double w, double h){
		Set<Placable> objects = new HashSet<>();
		for (int i = Math.max((int) x, 0); i < Math.min(Math.ceil(x+w),width); i++){
			for (int j = Math.max((int) y, 0); j < Math.min(Math.ceil(y+h),height); j++){
				objects.addAll(field[i][j]);
			}
		}
		return objects;
	}
	
	
}