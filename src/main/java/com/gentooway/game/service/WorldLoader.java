package com.gentooway.game.service;

import com.gentooway.game.model.Creature;
import com.gentooway.game.model.Room;
import com.gentooway.game.model.World;

import java.util.ArrayList;
import java.util.List;

/**
 * World state loader.
 */
public class WorldLoader {

    public static World loadWorld() {
        World world = new World();

        // todo load rooms from a file
        world.setRooms(getRooms());
        return world;
    }

    private static List<Room> getRooms() {
        List<Room> rooms = new ArrayList<>();

        Room room = new Room();
        room.setWelcomeMessage("Now you are in the initial room");
        rooms.add(room);

        Room leftRoom = new Room();
        leftRoom.setRight(room);
        leftRoom.setWelcomeMessage("Now you are in the left room");
        rooms.add(leftRoom);

        Room rightRoom = new Room();
        rightRoom.setLeft(room);
        rightRoom.setWelcomeMessage("Now you are in the right room");
        rooms.add(rightRoom);

        Room upRoom = new Room();
        upRoom.setDown(room);
        upRoom.setWelcomeMessage("Now you are in the up room");
        Creature creature = new Creature();
        creature.setName("test");
        creature.setHealth(30);
        creature.setAttack(10);
        upRoom.getCreatures().add(creature);
        rooms.add(upRoom);

        Room downRoom = new Room();
        downRoom.setUp(room);
        downRoom.setWelcomeMessage("Now you are in the down room");
        rooms.add(downRoom);

        room.setLeft(leftRoom);
        room.setRight(rightRoom);
        room.setUp(upRoom);
        room.setDown(downRoom);

        return rooms;
    }
}
