package com.gentooway.game.model;

import java.io.Serializable;

/**
 * Class for a character.
 */
public class Character implements Serializable {

    private Integer health = 100;
    private Integer level = 1;
    private Integer experience = 0;
    private Integer attack = 10;
    private Room currentRoom;

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public Integer getAttack() {
        return attack;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    @Override
    public String toString() {
        return "Character info: " +
                "\nhealth = " + health +
                "\nlevel = " + level +
                "\nexperience = " + experience +
                "\nattack " + attack;
    }
}
