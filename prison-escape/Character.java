import java.util.HashMap;
import java.util.Set;
import java.util.Random;
/**
 * This class is part of the "Prison Escape" application. 
 * "Prison Escape" is a simple, text based game.
 * 
 * A character is a non-playable character that is part of the game's level.
 * Characters are able to move by themselves and they will move to different rooms
 * throughout the game. Each character has a unique set of rooms that they can go to.
 * (E.g. a prisoner can only go to certain rooms while a guard can enter all rooms)
 *
 * @author Thom Treebus
 * @version 2020.12.02
 */
public class Character
{
    private String name;
    private String description;
    private HashMap<String, Room> locations; //stores all rooms where a character can be
    private Room currentLocation;
    
    /**
     * Constructor for objects of class Character
     */
    public Character(String name, String description)
    {
        this.name = name;
        this.description = description;
        locations = new HashMap<>();
    }
    
    /**
     * Define a room for this character to be in
     * @param roomName the name of the room
     * @param room the room
     */
    public void setLocation(String roomName, Room room)
    {
        locations.put(roomName, room);
    }
    
    /**
     * Get the name of a character
     * @return the character's name
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * @return the character's description
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * Get the location that a character is in.
     * Since a character can move around the map, the location will be randomly selected from the HashMap of locations
     * @return the room the character is currently in
     */
    public Room getLocation()
    {
        return currentLocation;
    }
    
    /**
     * Change the location of a character by selecting a random room from the hashmap of possible rooms that they can be in.
     * @return a new random location for the character to be in.
     */
    public Room changeLocation()
    {
        Random rand = new Random();
        //put the values of the locations HashMap in an array
        Object[] rooms = locations.values().toArray();
        Object randomLocation = rooms[rand.nextInt(rooms.length)];
        //select a random location from the hashmap of possible locations that a character can be in.
        currentLocation = (Room) rooms[rand.nextInt(rooms.length)];    
        return currentLocation;
    }
    
    /**
     * @return a string that lists all the rooms that a character can be in
     */
     public String getLocationString()
     {
        String returnString = "Is in one of the following locations: ";
        Set<String> keys = locations.keySet();
        for(String location : keys) {
            returnString += " " + location;
        }
        return returnString;
    }
    
    /**
     * Get a short description about a character that contains their name and description.
     */
    public String getShortDescription()
    {
        return "\rName: " + name + "\rDescription: " + description;
    }
    
    /**
     * Get a long description of a character that contains their name, description, and list of rooms that they can be in.
     */
    public String getLongDescription()
    {
        return "\rName: " + name + "\rDescription: " + description + "\r" + getLocationString();
    }
    
    /**
     * Check if a character is in a specified room.
     * @param room the room we want to check if the character is in or not.
     * @return true if the character is in the specified room, false if they are not.
     */
     public boolean inRoom(Room room)
     {
        boolean inRoom = false;
            if(getLocation() == room){
                inRoom = true;
            }
        return inRoom;
    }

}
