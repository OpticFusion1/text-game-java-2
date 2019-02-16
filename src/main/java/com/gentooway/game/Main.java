package com.gentooway.game;

import com.gentooway.game.model.Game;

/**
 * Application entry point.
 */
public class Main {

    private static final Game game = new Game();

    public static void main(String[] args) {
        game.start();
    }
}
