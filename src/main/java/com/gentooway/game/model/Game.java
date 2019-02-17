package com.gentooway.game.model;

import com.gentooway.game.menu.MenuCommand;
import com.gentooway.game.service.GameService;

import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

import static com.gentooway.game.menu.MenuCommand.*;
import static com.gentooway.game.service.WorldLoader.loadWorld;

/**
 * Main class of the game.
 */
public class Game {

    private Scanner scanner;
    private final GameService gameService;
    private World world;

    private Map<MenuCommand, Consumer<String>> commandToHandler = new EnumMap<>(MenuCommand.class);

    public Game() {
        this.world = loadWorld();
        this.gameService = new GameService(world);
        this.scanner = new Scanner(System.in);

        initCommandsMap();
    }

    private void initCommandsMap() {
        commandToHandler.put(HELP, command -> printMenu());
        commandToHandler.put(NEW_GAME, command -> gameService.createNewGame());
        commandToHandler.put(MOVE_UP, command -> gameService.moveUp());
        commandToHandler.put(MOVE_DOWN, command -> gameService.moveDown());
        commandToHandler.put(MOVE_RIGHT, command -> gameService.moveRight());
        commandToHandler.put(MOVE_LEFT, command -> gameService.moveLeft());
        commandToHandler.put(SAVE, command -> gameService.save());
        commandToHandler.put(LOAD, command -> gameService.load());
    }

    public void start() {
        // todo printGameIntro()
        printMenu();

        String command = scanner.nextLine();

        while (!EXIT.getValue().equals(command)) {
            handleCurrentCommand(command);

            command = scanner.nextLine();
        }

        scanner.close();
    }

    private void handleCurrentCommand(String command) {
        commandToHandler
                .getOrDefault(getMenuCommand(command),
                        value -> System.out.println("Unknown command"))
                .accept(command);
    }

    private void printMenu() {
        for (MenuCommand commands : valuesForState(world.getState())) {
            System.out.println(commands.getValue());
        }
    }
}
