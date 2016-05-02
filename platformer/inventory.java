import java.util.ArrayList;

/*
 * The inventory can store Items.
 * There is a maximum capacity of Items in the inventory.
 * Items of the same type are not stackable
 * 
 * The add method tries to put an item in the inventory, and returns whether this was possible.
 * It is not possible if the item is null, or if the inventory is full
 */

class Inventory {
	ArrayList<Item> items = new ArrayList<>();
	int maxSize;
	
	public Inventory(int size){
		
		maxSize = size;
	}
	
	public boolean add(Item item){
		if (items.size()<maxSize && item != null){
			items.add(item);
			return true;
		}
		return false;
	}
	
	public Item remove(Item item){
		items.remove(item);
		return item;
	}
	
	public Item remove(int index){
		return items.remove(index);
	}
	
	public Item remove(String itemType){
		for (Item item : items){
			if (item.getType() == itemType)
				items.remove(item);
				return item;
		}
		return null;
	}
	
	public Item get(int index){
		return items.get(index);
	}
	
	public boolean contains(Item item){
		return items.contains(item);
	}
	
	public boolean contains(String itemType){
		for (Item item : items){
			if (item.getType() == itemType)
				return true;
		}
		return false;
	}
	
	
}