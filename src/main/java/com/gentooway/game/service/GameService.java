package com.gentooway.game.service;

import com.gentooway.game.model.Character;
import com.gentooway.game.model.Creature;
import com.gentooway.game.model.Room;
import com.gentooway.game.model.World;
import com.gentooway.game.model.enums.UserActionType;
import com.gentooway.game.model.enums.WorldState;

import java.io.*;
import java.util.List;

import static com.gentooway.game.model.constants.ConsoleMessages.*;
import static com.gentooway.game.model.enums.UserActionType.*;
import static com.gentooway.game.model.enums.WorldState.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

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

        System.out.println(NEW_CHARACTER_CREATED);
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

            gainExperience(MOVE);
            checkCreatures();
        } else {
            System.out.println(THERE_IS_NO_WAY);
        }
    }

    /**
     * Save the current world state.
     */
    public void save() {
        try (FileOutputStream file = new FileOutputStream("world_save");
             ObjectOutputStream out = new ObjectOutputStream(file)) {

            out.writeObject(world);

            System.out.println(GAME_HAS_BEEN_SAVED);

        } catch (IOException e) {
            System.out.println(SAVING_ERROR);
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

            System.out.println(GAME_HAS_BEEN_LOADED);

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(LOADING_ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Print character stats.
     */
    public void showStats() {
        System.out.println(world.getCharacter());
    }

    /**
     * Gain experience depending on the action type and check if new level reached.
     *
     * @param actionType type of action
     */
    private void gainExperience(UserActionType actionType) {
        Character character = world.getCharacter();

        int newExperience = character.getExperience() + actionType.getExp();
        character.setExperience(newExperience);

        checkLevelUpdate();
    }

    /**
     * Check if a new level reached.
     * <p>
     * Formula:
     * <p>
     * currentExperience >= 10 * currentLevel + (10 * (currentLevel - 1))
     */
    private void checkLevelUpdate() {
        Character character = world.getCharacter();

        Integer experience = character.getExperience();
        Integer experienceToTheNextLevel = 10 * character.getLevel() + (10 * (character.getLevel() - 1));

        if (experience >= experienceToTheNextLevel) {
            Integer newLevel = character.getLevel() + 1;
            character.setLevel(newLevel);

            character.setHealth(character.getHealth() + 30);
            character.setAttack(character.getAttack() + 10);
            character.setPotionCharges(character.getPotionCharges() + 1);

            System.out.println("Congratulations! You have reached the " + newLevel + " level!");
        }
    }

    /**
     * Checks whether the current room has alive creatures and begins the new battle if they exist.
     * If the current world state is IN_GAME and alice creatures exist, sets the state to BATTLE.
     * If the current world state is BATTLE and there are no more alive creatures, sets the state to IN_GAME.
     */
    private void checkCreatures() {
        List<Creature> aliveCreatures = getAliveCreatures();

        WorldState worldState = world.getState();
        if (!aliveCreatures.isEmpty() && worldState.equals(IN_GAME)) {
            System.out.println(CREATURES_MESSAGE);
            System.out.println("Number of creatures: " + aliveCreatures.size());

            world.setState(BATTLE);
        } else if (aliveCreatures.isEmpty() && worldState.equals(BATTLE)) {
            System.out.println(CREATURES_KILLED);

            world.setState(IN_GAME);
        }
    }

    /**
     * Attack an alive creature.
     */
    public void attackCreature() {
        List<Creature> aliveCreatures = getAliveCreatures();

        if (!aliveCreatures.isEmpty()) {
            Character character = world.getCharacter();
            Creature creature = aliveCreatures.get(0);
            System.out.println("Attacking \"" + creature.getName() + "\" creature.");
            System.out.println(creature);

            Integer characterAttack = character.getAttack();
            Integer creatureNewHealth = creature.getHealth() - characterAttack;
            creature.setHealth(creatureNewHealth);
            System.out.println("You have damaged the creature for " + character.getAttack());

            Integer creatureAttack = creature.getAttack();
            Integer characterNewHealth = character.getHealth() - creatureAttack;
            character.setHealth(characterNewHealth);
            System.out.println("You have got " + creatureAttack + " damage");

            checkCharacterHealth(character);
            checkCreatureHealth(creature);

            checkCreatures();
        }
    }

    /**
     * Checks if a creature has more than zero health.
     *
     * @param creature the creature to check
     */
    private void checkCreatureHealth(Creature creature) {
        if (creature.getHealth() <= 0) {
            System.out.println("You have killed \"" + creature.getName() + "\" creature!");
            creature.setAlive(false);
            gainExperience(creature.isBoss() ? BOSS_KILL : CREATURE_KILL);
        }
    }

    /**
     * Check if a character has more than zero health.
     *
     * @param character the character
     */
    private void checkCharacterHealth(Character character) {
        if (character.getHealth() <= 0) {
            System.out.println(YOU_HAVE_DIED);
            world.setState(START_MENU);
        }
    }

    /**
     * Uses potion to increase character health by 100.
     */
    public void usePotion() {
        Character character = world.getCharacter();
        Integer potionCharges = character.getPotionCharges();

        if (potionCharges > 0) {
            character.setHealth(character.getHealth() + 100);
            character.setPotionCharges(character.getPotionCharges() - 1);

            System.out.println("One charge of potion has been used! Now you have " + character.getHealth() + " health.");
        } else {
            System.out.println(NO_POTION_CHARGES_LEFT);
        }
    }

    private List<Creature> getAliveCreatures() {
        return world.getCharacter().getCurrentRoom().getCreatures().stream()
                .filter(Creature::isAlive)
                .collect(toList());
    }

    World getWorld() {
        return world;
    }
}
