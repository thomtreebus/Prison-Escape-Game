import java.util.ArrayList;
/**
 * This class is part of the "Prison Escape" application. 
 * "Prison Escape" is a simple, text based game.
 * Class inventory - the player's inventory in the game.
 * 
 * Any items that are picked up by the player will be stored in their inventory.
 * The inventory has a maximum capacity of 10 kilograms. The player can't pick up items
 * when their inventory is full.
 *
 * @author Thom Treebus
 * @version 2020.12.02
 */
public class Inventory
{
    private ArrayList<Item> inventory; //stores items that the player is carrying.
    private int maxCapacity; //maximum value for the sum of all items in a player's inventory.
    public Inventory(){
        inventory = new ArrayList<>();
        maxCapacity = 10;
    }
    
    /**
     * Add an item to the player's inventory
     * @param item The item that will be added to the inventory
     */
    public void addItem(Item item)
    {
        inventory.add(item);
    }
    
    /**
     * Drop/remove an item from the player's inventory
     * @param item The item that the player will drop 
     */
    public void removeItem(Item item)
    {
        inventory.remove(item);
    }
    
    /**
     * Return an Item when searching for it by name.
     * @param itemName the name of the item that we want to find
     */
    public Item getItem(String itemName)
    {
        for(Item i: inventory){
            if(i.getName().equals(itemName)){
                return i;
            }
        }
        return null;
    }
    
    /**
     * Check to see if an item is in the player's inventory based on the item's name
     * @param itemName name of the item
     */
    public boolean inInventory(Item item)
    {  
        for(int i = 0; i<inventory.size(); i++){
            if(inventory.get(i) == item){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Get the total weight of all the items in the player's inventory
     * @return total combined weight of all items in the inventory.
     */
    public int getTotalWeight()
    {
        int inventoryWeight = 0;
        
            for(int i = 0; i<inventory.size(); i++){
                inventoryWeight = inventoryWeight + inventory.get(i).getWeight();
            }
        
        return inventoryWeight;
    }
    
    /**
     * Get the number of items in the player's inventory
     * @return number of items that are in the inventory
     */
    public int getSize()
    {
        int size = inventory.size();
        return size;
    }
    
    /**
     * Show items (and their weight) in the player's inventory and also show how much capacity is being used up.
     */
    public void showInventory()
    {
        if(inventory.size() == 0){
            System.out.println("your inventory is currently empty");
        }else{
            System.out.println("Items in your inventory: ");
            
            for(int i = 0; i<inventory.size(); i++){
                System.out.println(inventory.get(i).getName() + " (" + inventory.get(i).getWeight() + ")");
            }
            System.out.println("Total capacity used: " + getTotalWeight() + "/" + getMaxCapacity());
        }
        
        
    }
    
    /**
     * Return the maximum total weight the player can carry in their inventory
     */
    public int getMaxCapacity()
    {
        return maxCapacity;
    }
}
