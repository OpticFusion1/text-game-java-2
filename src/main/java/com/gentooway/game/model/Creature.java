package com.gentooway.game.model;

import java.io.Serializable;

/**
 * Class for a game creature.
 */
public class Creature implements Serializable {

    private String name;
    private Integer health;
    private Integer attack;
    private boolean isAlive = true;
    private boolean isBoss;

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public Integer getAttack() {
        return attack;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isBoss() {
        return isBoss;
    }

    public void setBoss(boolean boss) {
        isBoss = boss;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Creature stats: " +
                "\nname='" + name + '\'' +
                "\nhealth=" + health +
                "\nattack=" + attack +
                "\nisBoss=" + isBoss;
    }
}
