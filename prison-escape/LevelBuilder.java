import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
/**
 * This class is part of the "Prison Escape" application. 
 * "Prison Escape" is a very simple, text based adventure game.
 * 
 * The LevelBuilder class represents the main level of the game. It creates
 * all items, rooms, and characters and stores them in seperate ArrayLists.
 * The methods in this class are used to interact with the entities in the level.
 * 
 * @author Thom Treebus
 * @version 2020.12.02
 */
public class LevelBuilder
{
    //Initialize Rooms, Items, Characters
    private Room cell, corridor, mainHall, guardRoom, cafeteria, library, yard, commonRoom, showers, loadingArea, pub, secretRoom;
    private Item book, underwear, glass , knife, plate, sandwich, basketball, dumbell, rock, magazine, remote, soap, box, dictionary, manual, autobiography;
    private Character bones, guard, librarian, dave;
    //Initialize ArrayList that stores all items/rooms/characters in the level
    private ArrayList<Item> items = new ArrayList<>(); 
    private ArrayList<Room> rooms = new ArrayList<>(); 
    private ArrayList<Character> characters = new ArrayList<>();
    
    private Player player = new Player();
    private Room room;
    private Character character;
    
    /**
     * Create the internal map/level of the prison escape game.
     */
    public LevelBuilder()
    {
        createRooms();
        createItems();
        createCharacters();
    }
    
    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        // create the rooms
        cell = new Room("cell", "in your cell", false);
            rooms.add(cell);
        corridor = new Room("corridor", "in the corridor that connects all the cells", false);
            rooms.add(corridor);
        mainHall = new Room("mainHall", "in the main hall of the prison", false);
            rooms.add(mainHall);
        guardRoom = new Room("guardRoom", "in the guard room! You shouldn't be here so better leave quickly", true);
            rooms.add(guardRoom);
        cafeteria = new Room("cafeteria", "in the cafeteria", false);
            rooms.add(cafeteria);
        library = new Room("library", "in the library", false);
            rooms.add(library);
        yard = new Room("yard", "in the outside yard", false);
            rooms.add(yard);
        commonRoom = new Room("commonRoom", "in the common room", false);
            rooms.add(commonRoom);
        showers = new Room("shower", "in the showers", false);
            rooms.add(showers);
        loadingArea = new Room("loadingArea", "in the prison loading/unloading area", false);
            rooms.add(loadingArea);
        pub = new Room("pub", "in the prison pub. Enter command drink if you would like to have a drink!", false);
            rooms.add(pub);
        secretRoom = new Room("secretRoom", "in the secret room with the rooftop hatch!", true);
            rooms.add(secretRoom);
            
        // initialise room exits,
        cell.setExit("corridor",corridor);
        corridor.setExit("mainHall",mainHall);
        corridor.setExit("showers", showers);
        corridor.setExit("guardRoom", guardRoom);
        showers.setExit("corridor", corridor);
        mainHall.setExit("library", library);
        mainHall.setExit("cafeteria", cafeteria);
        mainHall.setExit("yard", yard);
        mainHall.setExit("guardRoom", guardRoom);
        mainHall.setExit("pub", pub);
        mainHall.setExit("commonRoom", commonRoom);
        mainHall.setExit("corridor", corridor);
        library.setExit("mainHall", mainHall);
        commonRoom.setExit("mainHall", mainHall);
        yard.setExit("mainHall", mainHall);
        pub.setExit("mainHall", mainHall);
        pub.setExit("cafeteria", cafeteria);
        cafeteria.setExit("mainHall", mainHall);
        cafeteria.setExit("loadingArea", loadingArea);
        cafeteria.setExit("pub", pub);
        loadingArea.setExit("cafeteria", cafeteria);
        loadingArea.setExit("secretRoom", secretRoom);
        guardRoom.setExit("corridor", corridor);
        guardRoom.setExit("mainHall",mainHall);    
    }

    /**
     * Create all the items
     */
    private void createItems(){
        //initialise all the items, give them names, descriptions, weights, and locations
        book = new Item("book", "Harry Potter and the Deathly Hallows by J.K. Rowling", 5, cell);
        knife = new Item("knife", "a knife used to eat food, perhaps it will become usefull when things get... violent", 2, cafeteria);
        plate = new Item("plate", "a plate used to eat food", 4, cafeteria);
        sandwich = new Item("sandwich", "an old half eaten peanut butter sandwich", 1, cafeteria);
        basketball = new Item("basketball", "a basketball that is partially inflated", 3, yard);
        dumbell = new Item("dumbell", "a dumbell used to exercise", 12, yard);
        rock = new Item("rock", "a sharp rock, perhaps it will become usefull when things get... violent", 5, yard);
        magazine = new Item("magazine", "a 2008 edition of the 'Time' magazine", 1, commonRoom);
        remote = new Item("remote", "a tv remote control", 1, commonRoom);
        soap = new Item("soap", "a pink bar of soap, smells like flowers", 1, showers);
        box = new Item("box", "a box. You look inside the box and find  a small black bag. Find Bones and bring him the box!", 10, loadingArea);
        glass = new Item("glass", "a pint glass", 2, pub);
        underwear = new Item("underwear", "a pair of dirty underwear", 1, cell);
        dictionary = new Item("dictionary", "a dictionary", 3, library);
        manual = new Item("manual", "an old manual, you flip through the pages and find a hidden cutout containing a key to the secret room!", 3, library);
        autobiography = new Item("autobiography", "an autobiography about the famous compuer scientist Michael Kölling", 3, library);

        //add the items to the ArrayList items
        items.add(book);
        items.add(knife);
        items.add(plate);
        items.add(sandwich);
        items.add(basketball);
        items.add(dumbell);
        items.add(rock);
        items.add(magazine);
        items.add(remote);
        items.add(soap);
        items.add(box);
        items.add(glass);
        items.add(underwear);
        items.add(dictionary);
        items.add(manual);
        items.add(autobiography);
    }
    
    /**
     * Create all the characters and specify what locations on the map they can be in
     */
    private void createCharacters()
    {
        bones = new Character("bones", "a prison inmate"); //initialise the characters name and description
            bones.setLocation("cafeteria", cafeteria);  //add all the locations where the character can be.
            bones.setLocation("yard", yard);
            bones.setLocation("commonRoom", commonRoom); 
        guard = new Character("guard", "a prison guard");
            guard.setLocation("guardRoom", guardRoom);
            guard.setLocation("yard", yard);
            guard.setLocation("commonRoom",commonRoom);
            guard.setLocation("corridor",corridor);
            guard.setLocation("cafeteria",cafeteria);
            guard.setLocation("mainHall",mainHall);
        librarian = new Character("librarian", "the prison librarian");
            librarian.setLocation("library", library);
        dave = new Character("dave", "a prison inmate");
            dave.setLocation("cafeteria", cafeteria); 
            dave.setLocation("yard", yard);
            dave.setLocation("commonRoom", commonRoom);
            dave.setLocation("showers", showers);
            
        characters.add(bones);
        characters.add(guard);
        characters.add(librarian);
        characters.add(dave);
            
    }
    
    //Methods related to rooms:
   
    /**
     * Find a room from the ArrayList of rooms and return it.
     * @param name the name of the room that needs to be returned
     * @return a room object with the same name as the input
     */
    public Room getRoom(String name)
    {
       for(int i = 0; i<rooms.size();i++){
           Room room = rooms.get(i);
           if(room.getRoomName().equals(name))
            return rooms.get(i);
        }
        return null;
    }
    
    /**
     * 'unlock' a room 
     * @param room the room that needs to be unlocked
     */
    public void unlockRoom(Room room)
    {
        room.unlock();
    }
    
    /**
     * Print a string that lists all the items in a room.
     * @param room get all the items that are in this room
     */
    public void getItemsInRoom(Room room)
    {
        String itemString = "";
        int itemCount = 0;
        
        for(Item i : items){
            Room location = i.getLocation();
            //check if location is the same as the room the player specified
            if(location.getRoomName().equals(room.getRoomName())){
                itemString = itemString + "\r-----------------------------------------"+
                                          "\r\nItem: " + i.getName()+
                                          "\n Weight: " + i.getWeight();
                itemCount++;
            }
        }
        //display how many items are in the room and what they are.
        if (itemCount == 0){
            System.out.println("There are no items in this room!");
        }else if (itemCount == 1) {
            System.out.println("There is 1 item in this room " + itemString+ "\r-----------------------------------------");
        }else{
            System.out.print("There are "+ itemCount +" items in this room: "+ itemString + "\r-----------------------------------------");
        }
    }
    
    /**
    * Print a string that lists all the characters in a room.
    * @param room get all the characters that are in this room
    */
    public void getCharactersInRoom(Room room)
    {
        String characterString = "\nCharacters in this room: ";
        int cCount = 0;
        for(Character c : characters){
            c.changeLocation();
            if(c.getLocation().getRoomName().equals(room.getRoomName())){
                characterString = characterString + c.getName() + "   ";
                cCount++;
            }
        }
        if (cCount >= 1){
            System.out.print(characterString);
        }
    }
    
    /**
     * Method that is called when the player enters the 'pub'
     * The player gets teleported to a random room in the prison.
     */
    public Room teleporterRoom()
    {
        //generate a random number between 0 and 11 (there are 12 rooms in the prison)
        Random rand = new Random();
        int n = rand.nextInt(11); 
        //select a room from the ArrayList rooms at index n (the random number).
        Room teleportRoom = rooms.get(n);
        
        return teleportRoom;
    }
    
    //Methods relating to items:
    /**
     * @param itemName the name of the item that needs to be returned
     * @return an item from the list of items
     */
    public Item getItem(String itemName)
    {
       for(Item i : items){
           if(i.getName().equals(itemName)){
               return i;
            }
        }
       return null;
    }
    
    /**
     * Remove an item from the ArrayList of items
     * @param item the item that is removed from the list
     */
    public void removeItem(Item item)
    {
        items.remove(item);
    }
    
    /**
     * Add an item to the ArrayList of items
     * @param item the item that is added to the list
     */
    public void addItem(Item item)
    {
        items.add(item);
    }
    
    /**
     * Returns true if the item exists, false if it doesn't
     * @param itemName name of the item that needs to be checked
     */
    public boolean itemExists(String itemName)
    {
        for(Item i : items){
           if(i.getName().equals(itemName)){
               return true;
            }
        }
       return false;
    }

    //Methods relating to characters
    /**
     * Return a Character object with a specified name
     * @param characterName the name of the character that needs to be returned
     * @return a character from the ArrayList of characters
     */
    public Character getCharacter(String characterName){
        for(Character c : characters){
            if(c.getName().equals(characterName)){
                return c;
            }
        }
        return null;
    }
}
