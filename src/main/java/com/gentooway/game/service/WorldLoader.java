package com.gentooway.game.service;

import com.gentooway.game.model.Creature;
import com.gentooway.game.model.Room;
import com.gentooway.game.model.World;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * World state loader.
 * <p>
 * File config.xml should be in the root of resources folder.
 */
public class WorldLoader {

    private static final String ID_TAG = "<id>";
    private static final String ID_CLOSE_TAG = "</id>";
    private static final String MESSAGE_TAG = "<message>";
    private static final String MESSAGE_CLOSE_TAG = "</message>";
    private static final String CONFIG_FILENAME = "config.xml";
    private static final String ROOM_TAG = "<room>";
    private static final String ROOM_CLOSE_TAG = "</room>";
    private static final String UP_TAG = "<up>";
    private static final String UP_CLOSE_TAG = "</up>";
    private static final String DOWN_TAG = "<down>";
    private static final String DOWN_CLOSE_TAG = "</down>";
    private static final String LEFT_TAG = "<left>";
    private static final String LEFT_CLOSE_TAG = "</left>";
    private static final String RIGHT_TAG = "<right>";
    private static final String RIGHT_CLOSE_TAG = "</right>";
    private static final String CREATURES_TAG = "<creatures>";
    private static final String CREATURES_CLOSE_TAG = "</creatures>";
    private static final String CREATURE_TAG = "<creature>";
    private static final String NAME_TAG = "<name>";
    private static final String NAME_CLOSE_TAG = "</name>";
    private static final String HEALTH_TAG = "<health>";
    private static final String HEALTH_CLOSE_TAG = "</health>";
    private static final String ATTACK_TAG = "<attack>";
    private static final String ATTACK_CLOSE_TAG = "</attack>";
    private static final String IS_BOSS_TAG = "<isBoss>";
    private static final String IS_BOSS_CLOSE_TAG = "</isBoss>";

    public static World loadWorld() {
        World world = new World();

        world.setRooms(getRoomsFromFile());
        return world;
    }

    private static List<Room> getRoomsFromFile() {
        List<Room> rooms = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(
                    Paths.get(WorldLoader.class.getClassLoader()
                            .getResource(CONFIG_FILENAME).toURI()));

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.contains(ROOM_TAG)) {
                    Room room = parseRoomData(lines, i);
                    rooms.add(room);
                }
            }

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.contains(ROOM_TAG)) {
                    fillRoomsRelations(rooms, lines, i);
                }
            }

        } catch (IOException | URISyntaxException e) {
            System.out.println("Error while getting a game config");
            e.printStackTrace();
        }

        return rooms;
    }

    private static void fillRoomsRelations(List<Room> rooms, List<String> lines, int i) {
        String roomId = getStringValue(lines.get(i + 1), ID_TAG, ID_CLOSE_TAG);

        Room currentRoom = getRoomById(rooms, roomId);

        int offset = i + 3;
        String nextLine = lines.get(offset);
        while (!nextLine.contains(ROOM_CLOSE_TAG)) {

            checkUp(currentRoom, nextLine, rooms);
            checkDown(currentRoom, nextLine, rooms);
            checkLeft(currentRoom, nextLine, rooms);
            checkRight(currentRoom, nextLine, rooms);

            nextLine = lines.get(++offset);
        }
    }

    private static String getStringValue(String line, String tag, String closeTag) {
        return line.substring(
                line.indexOf(tag) + tag.length(),
                line.indexOf(closeTag));
    }

    private static void checkRight(Room room, String line, List<Room> rooms) {
        if (line.contains(RIGHT_TAG)) {
            String rightRoomId = getStringValue(line, RIGHT_TAG, RIGHT_CLOSE_TAG);

            room.setRight(getRoomById(rooms, rightRoomId));
        }
    }

    private static void checkLeft(Room room, String line, List<Room> rooms) {
        if (line.contains(LEFT_TAG)) {
            String leftRoomId = getStringValue(line, LEFT_TAG, LEFT_CLOSE_TAG);

            room.setLeft(getRoomById(rooms, leftRoomId));
        }
    }

    private static void checkDown(Room room, String line, List<Room> rooms) {
        if (line.contains(DOWN_TAG)) {
            String downRoomId = getStringValue(line, DOWN_TAG, DOWN_CLOSE_TAG);

            room.setDown(getRoomById(rooms, downRoomId));
        }
    }

    private static void checkUp(Room room, String line, List<Room> rooms) {
        if (line.contains(UP_TAG)) {
            String upRoomId = getStringValue(line, UP_TAG, UP_CLOSE_TAG);

            room.setUp(getRoomById(rooms, upRoomId));
        }
    }

    private static Room getRoomById(List<Room> rooms, String roomId) {
        return rooms.stream()
                .filter(room -> Long.valueOf(roomId).equals(room.getId()))
                .findAny().orElse(null);
    }

    private static Room parseRoomData(List<String> lines, int i) {
        Room room = new Room();

        String roomId = getStringValue(lines.get(i + 1), ID_TAG, ID_CLOSE_TAG);
        room.setId(Long.valueOf(roomId));

        String message = getStringValue(lines.get(i + 2), MESSAGE_TAG, MESSAGE_CLOSE_TAG);
        room.setWelcomeMessage(message);

        String creaturesLine = lines.get(i + 3);
        if (creaturesLine.contains(CREATURES_TAG)) {
            parseCreaturesData(lines, i, room);
        }

        return room;
    }

    private static void parseCreaturesData(List<String> lines, int i, Room room) {
        int offset = i + 4;
        String nextLine = lines.get(offset);

        while (!nextLine.contains(CREATURES_CLOSE_TAG)) {

            if (nextLine.contains(CREATURE_TAG)) {
                Creature creature = new Creature();

                String name = getStringValue(lines.get(++offset), NAME_TAG, NAME_CLOSE_TAG);
                creature.setName(name);

                String health = getStringValue(lines.get(++offset), HEALTH_TAG, HEALTH_CLOSE_TAG);
                creature.setHealth(Integer.valueOf(health));

                String attack = getStringValue(lines.get(++offset), ATTACK_TAG, ATTACK_CLOSE_TAG);
                creature.setAttack(Integer.valueOf(attack));

                String isBoss = getStringValue(lines.get(++offset), IS_BOSS_TAG, IS_BOSS_CLOSE_TAG);
                creature.setBoss(Boolean.valueOf(isBoss));

                room.getCreatures().add(creature);
            }

            nextLine = lines.get(++offset);
        }
    }
}
