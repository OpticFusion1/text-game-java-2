package com.gentooway.game;

import com.gentooway.game.model.Game;
import com.gentooway.game.model.World;

import static com.gentooway.game.service.WorldLoader.loadWorld;

/**
 * Application entry point.
 */
public class Main {

    public static void main(String[] args) {
        World world = loadWorld(args);

        Game game = new Game(world);
        game.start();
    }
}
