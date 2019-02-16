package com.gentooway.game;

import com.gentooway.game.menu.MenuCommand;

import java.util.Scanner;

import static com.gentooway.game.menu.MenuCommand.EXIT;

/**
 * Application entry point.
 */
public class Main {

    public static void main(String[] args) {
        printMenu();

        try (Scanner scanner = new Scanner(System.in)) {
            String command = scanner.nextLine();

            while (!EXIT.getValue().equals(command)) {
                command = scanner.nextLine();
            }
        }
    }

    private static void printMenu() {
        for (MenuCommand commands : MenuCommand.values()) {
            System.out.println(commands.getValue());
        }
    }
}
