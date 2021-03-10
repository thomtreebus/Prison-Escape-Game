import java.util.Set;
import java.util.HashMap;

/**
 * Class Item - an item in prison escape adventure game
 * 
 * This class is part of the "Prison Escape" application. 
 * "Prison Escape" is a very simple, text based adventure game. 
 * 
 * An "Item" represents an item that can be found in the game. It has a
 * name, description, weight, and location. 
 * Items can be picked up and dropped by the player.
 *
 * @author Thom Treebus
 * @version 2020.12.02
 */
public class Item
{
    private String name;
    private String description;
    private int weight;
    private Room location;
    private Item item;    
    /**
     * Create an item described "description". Initially, it has
     * no description. "description" is something like "a key" or
     * "a piece of metal"
     * The weight of the item refers to how much it weight in kilograms. E.g. "5"
     * @param name The item's name
     * @param description The item's description
     * @param weight The item's weight
     * @param location The room where the item is located.
     * 
     */
    public Item(String name, String description, int weight, Room location)
    {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.location = location;
    }
    
    /**
     * @return an item's name
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * @return an item's description
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * @return an item's weight
     */
    public int getWeight()
    {
        return weight;
    }
    
    /**
     * @return the room an item is in
     */
    public Room getLocation()
    {
        return location;
    }
    
    /**
     * change the location of an item that already exists
     */
    public void setLocation(Room location)
    {
        this.location = location;
    }

}
