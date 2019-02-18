package com.gentooway.game.service;

import com.gentooway.game.model.Character;
import com.gentooway.game.model.Room;
import com.gentooway.game.model.World;

import java.io.*;
import java.util.List;

import static com.gentooway.game.model.enums.WorldState.IN_GAME;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Service for game to world interactions.
 */
public class GameService {

    private World world;

    public GameService(World world) {
        this.world = world;
    }

    /**
     * Creating a new game (a new character).
     */
    public void createNewGame() {
        List<Room> rooms = world.getRooms();

        if (isNull(rooms) || rooms.isEmpty()) {
            throw new RuntimeException("Your game world in empty! " +
                    "Please, make sure that you are using a correct game config file.");
        }

        Character character = new Character();
        character.setCurrentRoom(rooms.get(0));
        world.setCharacter(character);

        world.setState(IN_GAME);

        System.out.println("New character was created! Welcome to the game!");
    }

    /**
     * Move up.
     */
    public void moveUp() {
        Character character = world.getCharacter();
        Room currentRoom = character.getCurrentRoom();

        Room roomUp = currentRoom.getUp();
        setCurrentRoomOrWriteErrorMessage(character, roomUp);
    }

    /**
     * Move down.
     */
    public void moveDown() {
        Character character = world.getCharacter();
        Room currentRoom = character.getCurrentRoom();

        Room roomDown = currentRoom.getDown();
        setCurrentRoomOrWriteErrorMessage(character, roomDown);
    }

    /**
     * Move right.
     */
    public void moveRight() {
        Character character = world.getCharacter();
        Room currentRoom = character.getCurrentRoom();

        Room roomRight = currentRoom.getRight();
        setCurrentRoomOrWriteErrorMessage(character, roomRight);
    }

    /**
     * Move left.
     */
    public void moveLeft() {
        Character character = world.getCharacter();
        Room currentRoom = character.getCurrentRoom();

        Room roomLeft = currentRoom.getLeft();
        setCurrentRoomOrWriteErrorMessage(character, roomLeft);
    }

    private void setCurrentRoomOrWriteErrorMessage(Character character, Room room) {
        if (nonNull(room)) {
            character.setCurrentRoom(room);
            System.out.println(room.getWelcomeMessage());
        } else {
            System.out.println("You cannot go this way! There is no door!");
        }
    }

    /**
     * Save the current world state.
     */
    public void save() {
        try (FileOutputStream file = new FileOutputStream("world_save");
             ObjectOutputStream out = new ObjectOutputStream(file)) {

            out.writeObject(world);

            System.out.println("Game has been saved!");

        } catch (IOException e) {
            System.out.println("Got an error while saving the world state");
            e.printStackTrace();
        }
    }

    /**
     * Load the last saved world state.
     */
    public void load() {
        try (FileInputStream file = new FileInputStream("world_save");
             ObjectInputStream in = new ObjectInputStream(file)) {

            this.world = (World) in.readObject();

            System.out.println("Game has been loaded!");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Got an error while loading the world state");
            e.printStackTrace();
        }
    }

    /**
     * Print a character stats.
     */
    public void showStats() {
        System.out.println(world.getCharacter());
    }

    World getWorld() {
        return world;
    }
}
