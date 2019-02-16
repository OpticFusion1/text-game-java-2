package com.gentooway.game.menu;

import java.util.function.Consumer;

import static com.gentooway.game.Main.printMenu;

/**
 * Functions for handling menu actions.
 */
public class MenuHandlers {

    public static final Consumer<String> helpCommandHandler = (command) -> printMenu();
    public static final Consumer<String> moveUpCommandHandler = (command) -> System.out.println("moving up...");
    public static final Consumer<String> moveDownCommandHandler = (command) -> System.out.println("moving down...");
    public static final Consumer<String> moveRightCommandHandler = (command) -> System.out.println("moving right...");
    public static final Consumer<String> moveLeftCommandHandler = (command) -> System.out.println("moving left...");
    public static final Consumer<String> saveCommandHandler = (command) -> System.out.println("saving...");
    public static final Consumer<String> loadCommandHandler = (command) -> System.out.println("loading...");
    public static final Consumer<String> unknownCommandHandler = (command) -> System.out.println("Unknown command");
}
