import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
/**
 *  This class is the main class of the "Prison Escape" application. 
 *  "Prison Escape" is a very simple, text based adventure game. The player's
 *  goal is to escape the prison. The player can walk around, interact with characters,
 *  pick up items, and maybe even have a drink at the prison pub!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, items, and characters. 
 *  It creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Thom Treebus
 * @version 2020.12.02
 */

public class Game 
{
    private Parser parser;

    private Player player; //create a player
    private LevelBuilder level; //create the level
    private Item item;
    private ArrayList<Item> items = new ArrayList<>(); //array list of all the items in the prison
    private ArrayList<Room> rooms = new ArrayList<>(); //array list of all rooms in the prison.
    private ArrayList<Character> characters = new ArrayList<>(); //array list of all characters in the game
 
    private int bonesCount = 0;
    /**
     * Create the game and initialise its internal map and the player.
     */
    public Game() 
    {
        createPlayer();
        createLevel();
        player.setLocation(level.getRoom("cell")); //set the location of the player to cell (this is where they start the game)
        level = new LevelBuilder();
        parser = new Parser();
        
    }
    
    /**
     * Create the player
     */
    private void createPlayer(){
        player = new Player();    
    }
    
    /**
     * Create the main game level
     */
    private void createLevel(){
        level = new LevelBuilder();
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to Strand Prison!");
        System.out.println("You have been wrongfully convicted of a crime and are now serving a 5 year prison sentence");
        System.out.println("");
        System.out.println("Hint: Try talking to the other inmates for help!");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printRoomDescription(player.getLocation());
        
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if(commandWord.equals("go")) {
            goRoom(command);
        }else if(commandWord.equals("back")){
            goBack();
        }else if(commandWord.equals("take")){
            takeItem(command);
        }else if(commandWord.equals("drop")){
            dropItem(command);
        }else if(commandWord.equals("give")){
            giveItem(command);
        }else if(commandWord.equals("inventory")){
            player.showInventory();
        }else if(commandWord.equals("drink")){
            teleportRoom();
        }else if(commandWord.equals("inspect")){
            inspectItem(command);
        }else if(commandWord.equals("interact")){
            interact(command);
        }else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        
        // else command not recognised.
        return wantToQuit;
    }   
    
    // implementations of user commands:
    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You have to escape the prison.");
        System.out.println("Don't get caught by the guards!");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }
    
    /**
     * Print out the full description of a room that includes:
     * all the items that are located in the room,
     * all the characters that are located in the room.
     * @param room specify which room to get the description for.
     */
    private void printRoomDescription(Room room)
    {
        //show the player what room they are in
        System.out.println(player.getLocation().getLongDescription());
        //show what items and characters are in the room
        level.getItemsInRoom(room);
        level.getCharactersInRoom(room);
    } 
    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }
        
        //store the rooom that the player wants to enter
        String direction = command.getSecondWord();
        Room nextRoom = player.getLocation().getExit(direction);

        if (nextRoom == null) {
            //if the room does not exist, there is an error
            System.out.println("There is no door!");
        }else {
              //check if the player is entering the final secretRoom
              if(direction.equals("secretRoom") && (level.getRoom("secretRoom").isLocked() == false)){
                  //player wins the game, game ends
                  System.out.println("You used the key you found in the manual to unlock the secret room!");
                  System.out.println("Inside the secret room you see a ladder that leads to a hatch");
                  System.out.println("You climb up and open the hatch to discover that you have reached the roof of the prison");
                  System.out.println("Congratulations! You have escaped prison and can enjoy your freedom once again!");
                  System.exit(0); //exit the game
              }else{
                  //check if the room the player wants to enter is locked.
                  if(nextRoom.isLocked() == false){
                      player.addPreviousLocation(player.getLocation()); //add the room the player is currently in to the stack previousRooms (from the Player class)
                      player.setLocation(nextRoom); //set the player's location to the room they want to go to
                      printRoomDescription(nextRoom);
                  }else{
                        System.out.print("That room is locked! You need a key to enter.");
                  }
              }
        }
               
    }  

    /**
     * Go back to the previous room.
     */
    private void goBack()
    {
        //Get the previous location the player was in and make that their current location.
        Room room = player.getPreviousLocation();
        player.setLocation(room);
        printRoomDescription(room);
    }
    
    /**
     * Pick up an item and place it in the player's inventory.
     * If the item doesn't exist, or is located in a different room, print an error.
     * @param command the user has to enter which item they want to pick up after entering the 'take' commandWord.
     */
    private void takeItem(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know which item to take...
            System.out.println("Take what item?");
            return;
        }
        //store the name of the item that the player wants to pick up
        String itemName = command.getSecondWord();
        
        //check if the item the player wants to pick up exists       
        if(level.itemExists(itemName)){
            String itemLocation = level.getItem(itemName).getLocation().getRoomName();
            String playerLocation = player.getLocation().getRoomName();
            
            //check if the item is located in the same room as the player
            if(itemLocation.equals(playerLocation)){
                //store the item
                Item item = level.getItem(itemName);
                //Check if the player's inventory has enough space in order to pick up the item
                if(!(player.getInventoryWeight() + item.getWeight() >+ player.getMaxInventoryCapacity())){
                    player.addItemToInventory(item);
                    level.removeItem(item); //remove the item from the level so that it won't appear in the room any more.
                    player.showInventory();
                }else{
                    System.out.println("Not enough room in your inventory! You can drop some items in order to free up space.");
                }
                
                //check if the user picked up the manual (which contains the key to the secret room)
                if(item.getName().equals("manual")){
                    //unlock the secretRoom
                    level.unlockRoom(level.getRoom("secretRoom"));
                }
            }
        }else{
            System.out.print("That item either doesn't exist or is not located in this room!");
        } 
    }
    
    /**
     * Drop an item in the room that the player is currently in.
     * The item will no longer be in their inventory.
     */
    private void dropItem(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know which item to drop...
            System.out.println("Drop which item?");
            return;
        }
        
        //check if the item that the player wants to drop is in their inventory
        String itemName = command.getSecondWord();
        Item item = player.getItemFromInventory(itemName);
        if(player.inInventory(item)){
            //change the item's location to the room that the player is currently in
            item.setLocation(player.getLocation());
            level.addItem(item);
            //remove the item from the player's inventory
            player.removeItemFromInventory(item);
            System.out.println(item.getName() + " is no longer in your inventory!");
        }else{
                System.out.println("That item is not in your inventory!");
        }
    }
    
    /**
     * Inspect an item that is in the player's inventory.
     * Inspecting an item will reveal more information about it.
     */
    private void inspectItem(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know which item to inspect...
            System.out.println("Inspect which item?");
            return;
        }
        String itemName = command.getSecondWord();
        Item inspectItem = player.getItemFromInventory(itemName);
        
        //check if the item is in the player's inventory, then output the item's description
        if(player.inInventory(inspectItem)){
            System.out.println("You inspected the item and discovered this: \r" + inspectItem.getDescription());
        }else{
            System.out.println(itemName + " is not in your inventory!");
        }  
    }

    /**
     * Give an item to one of the characters 
     */
    private void giveItem(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second or third word, we don't know which item to give and who to give it to...
            System.out.println("Please specify which item you wish to give.");
            return;
        }else if(!command.hasThirdWord()){
            System.out.println("Please specify who you want to give " + command.getSecondWord() + " to.");
        }
        
        //select the item the player wants to give
        String itemName = command.getSecondWord();
        Item item = player.getItemFromInventory(itemName);
        
        //select the character the player wants to give the item to
        String characterName = command.getThirdWord();
        Character character = level.getCharacter(characterName);
        
        //Exchange the item
        itemExchange(character, item);
        
        //remove the item that the player is giving from their inventory
        player.removeItemFromInventory(item);  
    }
    
    /**
     * Dialogue for when the player gives an item to a character
     * @param character the character who the player is giving the item to
     * @param item the item that the player is giving away
     */
    private void itemExchange(Character character, Item item)
    {
        //check if the player is giving bones the box (part of the game story)
        if(character.getName().equals("bones") && item.getName().equals("box")){
            System.out.println("Bones: Great you found the bag! If you want to escape, here's what you need to do:");
            System.out.println("Go to the library and make sure the guard is not there,");
            System.out.println("if he is in the library, go somewhere else and wait for him to leave.");
            System.out.println("Then take one of the books and inside you will find a key that leads to a hidden room");
            System.out.println("You'll have to find the room yourself");
            
        }else{
            System.out.println("You gave: " + item.getName() + " to " + character.getName());
        }
    }
    
    /**
     * The player will have a few drinks and wake up in a random room in the prison.
     */
    private void teleportRoom()
    {
        player.addPreviousLocation(level.getRoom("pub"));
        Room teleportRoom = level.teleporterRoom();
        player.setLocation(teleportRoom);
        System.out.print("You had one too many drinks and woke up in the with a headache and no memories of what happened last night! \r");
        printRoomDescription(teleportRoom);
    }
    
    /**
     * Method is called when the player enters the "interact" command.
     * The player will talk to one of the characters
     * @param character the character the player entered to talk to
     */
    private void interact(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know which character to talk to...
            System.out.println("Please specify who you would like to talk to");
            return;
        }
        //Get the Character that the player wants to interact with.
        String characterName = command.getSecondWord();
        Character character = level.getCharacter(characterName);
        
        //check if the character is in the same room as the player, otherwise print an error.
        if(character.getLocation().getRoomName().equals(player.getLocation().getRoomName())){   
            //print out a response based on which character the player is talking to.
            if(character.getName().equals("bones")){      
                if(bonesCount == 0){
                    System.out.println("Bones: You want to escape? I can help you with that.");
                    System.out.println("    But first I need you to do something for me.");
                    System.out.println("    Go to the loading area outside the cafeteria,");
                    System.out.println("    and bring me the small brown box.");
                    bonesCount++;
                }else{
                    System.out.println("Bones: What do you want? Go get me my box and then i'll talk");
                }
            }else if(character.getName().equals("dave")){
                System.out.println("Dave: Hey, you looking for a way out of here? You should go talk to Bones.");
            }else if(character.getName().equals("guard")){
                System.out.println("Guard: What do you want? Just keep minding your business.");
            }else if(character.getName().equals("librarian")){
                System.out.println("Librarian: Welcome to the library! Feel free to check out one of our books!");
            }
        }else{
            System.out.println("Sorry that character is not in this room or doesn't exist.");
        }
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
