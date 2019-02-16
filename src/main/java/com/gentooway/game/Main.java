package com.gentooway.game;

import com.gentooway.game.menu.MenuCommand;
import com.gentooway.game.model.World;

import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

import static com.gentooway.game.menu.MenuCommand.*;
import static com.gentooway.game.menu.MenuHandlers.*;
import static com.gentooway.game.service.WorldLoader.loadWorld;

/**
 * Application entry point.
 */
public class Main {

    private static World world = loadWorld();

    private static final Map<MenuCommand, Consumer<String>> commandToHandler =
            new EnumMap<MenuCommand, Consumer<String>>(MenuCommand.class) {
                {
                    put(HELP, helpCommandHandler);
                    put(MOVE_UP, moveUpCommandHandler);
                    put(MOVE_DOWN, moveDownCommandHandler);
                    put(MOVE_RIGHT, moveRightCommandHandler);
                    put(MOVE_LEFT, moveLeftCommandHandler);
                    put(SAVE, saveCommandHandler);
                    put(LOAD, loadCommandHandler);
                }
            };

    public static void main(String[] args) {
        printMenu();

        try (Scanner scanner = new Scanner(System.in)) {
            String command = scanner.nextLine();

            while (!EXIT.getValue().equals(command)) {
                commandToHandler.getOrDefault(getMenuCommand(command), unknownCommandHandler).accept(command);

                command = scanner.nextLine();
            }
        }
    }

    public static void printMenu() {
        for (MenuCommand commands : valuesForState(world.getState())) {
            System.out.println(commands.getValue());
        }
    }
}
