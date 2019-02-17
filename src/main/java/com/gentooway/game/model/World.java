package com.gentooway.game.model;

import com.gentooway.game.model.enums.WorldState;

import java.io.Serializable;
import java.util.List;

import static com.gentooway.game.model.enums.WorldState.START_MENU;

/**
 * Class for current world state
 */
public class World implements Serializable {

    private List<Room> rooms;
    private Character character;
    private WorldState state = START_MENU;

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public WorldState getState() {
        return state;
    }

    public void setState(WorldState state) {
        this.state = state;
    }
}
