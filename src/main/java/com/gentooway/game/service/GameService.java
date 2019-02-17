package com.gentooway.game.service;

import com.gentooway.game.model.Character;
import com.gentooway.game.model.Room;
import com.gentooway.game.model.World;

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

    public void moveUp() {
        Character character = world.getCharacter();
        Room currentRoom = character.getCurrentRoom();

        Room roomUp = currentRoom.getUp();
        setCurrentRoomOrWriteErrorMessage(character, roomUp);
    }

    public void moveDown() {
        Character character = world.getCharacter();
        Room currentRoom = character.getCurrentRoom();

        Room roomDown = currentRoom.getDown();
        setCurrentRoomOrWriteErrorMessage(character, roomDown);
    }

    public void moveRight() {
        Character character = world.getCharacter();
        Room currentRoom = character.getCurrentRoom();

        Room roomRight = currentRoom.getRight();
        setCurrentRoomOrWriteErrorMessage(character, roomRight);
    }

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

    public void save() {
        // todo
    }

    public void load() {
        // todo
    }
}
