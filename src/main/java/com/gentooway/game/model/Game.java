package com.gentooway.game.model;

import com.gentooway.game.menu.MenuCommand;
import com.gentooway.game.service.GameService;

import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;

import static com.gentooway.game.menu.MenuCommand.*;
import static com.gentooway.game.model.constants.ConsoleMessages.MENU_HEADER;
import static com.gentooway.game.model.constants.ConsoleMessages.UNKNOWN_COMMAND;

/**
 * Main class of the game.
 */
public class Game {

    private Scanner scanner;
    private final GameService gameService;
    private World world;

    private Map<MenuCommand, Runnable> commandToHandler = new EnumMap<>(MenuCommand.class);

    public Game(World world) {
        this.world = world;
        this.gameService = new GameService(world);
        this.scanner = new Scanner(System.in);

        initCommandsMap();
    }

    private void initCommandsMap() {
        commandToHandler.put(HELP, this::printMenu);
        commandToHandler.put(STATS, gameService::showStats);
        commandToHandler.put(ATTACK, gameService::attackCreature);
        commandToHandler.put(USE_POTION, gameService::usePotion);
        commandToHandler.put(NEW_GAME, gameService::createNewGame);
        commandToHandler.put(MOVE_UP, gameService::moveUp);
        commandToHandler.put(MOVE_DOWN, gameService::moveDown);
        commandToHandler.put(MOVE_RIGHT, gameService::moveRight);
        commandToHandler.put(MOVE_LEFT, gameService::moveLeft);
        commandToHandler.put(SAVE, gameService::save);
        commandToHandler.put(LOAD, gameService::load);
    }

    public void start() {
        printMenu();

        String command = scanner.nextLine();

        while (!EXIT.getValue().equals(command)) {
            handleCurrentCommand(command);
            printMenu();

            command = scanner.nextLine();
        }

        scanner.close();
    }

    private void handleCurrentCommand(String command) {
        commandToHandler
                .getOrDefault(getMenuCommand(command),
                        () -> System.out.println(UNKNOWN_COMMAND))
                .run();
    }

    private void printMenu() {
        System.out.println();
        System.out.println(MENU_HEADER);
        for (MenuCommand command : valuesForState(world.getState())) {
            System.out.println(" * " + command.getValue());
        }
        System.out.println();
    }
}
