package com.gentooway.game.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for a world room.
 */
public class Room implements Serializable {

    private Long id;
    private String welcomeMessage;
    private Room up;
    private Room down;
    private Room left;
    private Room right;

    private List<Creature> creatures = new ArrayList<>();

    public Room getUp() {
        return up;
    }

    public void setUp(Room up) {
        this.up = up;
    }

    public Room getDown() {
        return down;
    }

    public void setDown(Room down) {
        this.down = down;
    }

    public Room getLeft() {
        return left;
    }

    public void setLeft(Room left) {
        this.left = left;
    }

    public Room getRight() {
        return right;
    }

    public void setRight(Room right) {
        this.right = right;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    public void setWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    public List<Creature> getCreatures() {
        return creatures;
    }

    public void setCreatures(List<Creature> creatures) {
        this.creatures = creatures;
    }
}
