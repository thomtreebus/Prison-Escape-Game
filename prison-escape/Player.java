import java.util.Stack;
/**
 * This class is part of the "Prison Escape" application. 
 * "Prison Escape" is a simple, text based game.
 *
 *This class stores information about the player in the game.
 *There are methods that handle the player's location on the map
 * as well as methods that handle the player's inventory.
 *
 * @author Thom Treebus
 * @version 2020.12.02
 */
public class Player
{
    private Room currentLocation;
    Stack<Room> previousRooms;
    private Inventory inventory = new Inventory();
    
    /**
     * Constructor for objects of class Player
     */
    public Player()
    {
        inventory = new Inventory(); //create the player's inventory
        previousRooms = new Stack<>(); //initialise the stack of previous rooms that the player has been in
    }
    
    /**
     * Get the player's location
     */
    public Room getLocation()
    {
        return currentLocation;
    }
    
    /**
     * Change the player's location to a specified one.
     * @param room the new location of the player
     */
    public void setLocation(Room room)
    {
        currentLocation = room;
    }
    
    /**
     * Add a room to the Stack of rooms that the player has previously been in.
     * @param room the room that the player was previously in
     */
    public void addPreviousLocation(Room room)
    {
        previousRooms.push(room);
    }
    
    /**
     * Get the player's previous location.
     */
    public Room getPreviousLocation()
    {
        //return the player's previous location (element at the top of the stack of previous locations)
        return previousRooms.pop();
    }
    
    /**
     * Add an item to the player's inventory
     * @param item the item that gets added to the player's inventory
     */
    public void addItemToInventory(Item item){
        inventory.addItem(item);
    }
    
    /**
     * Remove an item from the player's inventory
     * @param item the item to remove from the player's inventory
     */
    public void removeItemFromInventory(Item item)
    {
        inventory.removeItem(item);
    }
    
    /**
     * @return the size of the player's inventory
     */
    public int inventorySize()
    {
        return inventory.getSize();
    }
    
    /**
     * @return the total weight of all items in the player's inventory
     */
    public int getInventoryWeight()
    {
        return inventory.getTotalWeight();
    }
    
    /**
     * @return the maximum total weight of items that the player can carry in their inventory
     */
    public int getMaxInventoryCapacity()
    {
        return inventory.getMaxCapacity();
    }
    
    /**
     * @return an item from the player's inventory
     * @param itemName the name of the item in the player's inventory that needs to be returned
     */
    public Item getItemFromInventory(String itemName)
    {
        return inventory.getItem(itemName);
    }
    
    /**
     * True if the item is in the player's inventory, false if the item is not in their invenotry
     * @param item check if this item is in the player's inventory
     */
    public boolean inInventory(Item item)
    {
        return inventory.inInventory(item);
    }
    
    /**
     * Show the items that are in the player's inventory
     */
    public void showInventory()
    {
        inventory.showInventory();
    }
}
