package com.gentooway.game.model;

import java.io.Serializable;

import static com.gentooway.game.model.constants.CharacterDefaultStats.*;

/**
 * Class for a character.
 */
public class Character implements Serializable {

    private Integer health = DEFAULT_HEALTH;
    private Integer level = DEFAULT_LEVEL;
    private Integer experience = DEFAULT_EXPERIENCE;
    private Integer attack = DEFAULT_ATTACK;
    private Integer potionCharges = DEFAULT_POTION_CHARGES;
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

    public Integer getPotionCharges() {
        return potionCharges;
    }

    public void setPotionCharges(Integer potionCharges) {
        this.potionCharges = potionCharges;
    }

    @Override
    public String toString() {
        return "Character info: " +
                "\nhealth = " + health +
                "\nlevel = " + level +
                "\nexperience = " + experience +
                "\nattack " + attack +
                "\npotion charges = " + potionCharges;
    }
}
