package com.gentooway.game.menu;

import com.gentooway.game.model.enums.WorldState;

import java.util.ArrayList;
import java.util.List;

import static com.gentooway.game.model.enums.WorldState.IN_GAME;
import static com.gentooway.game.model.enums.WorldState.START_MENU;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

/**
 * Menu commands enum.
 */
public enum MenuCommand {
    HELP("help", asList(START_MENU, IN_GAME)),
    STATS("stats", singletonList(IN_GAME)),
    NEW_GAME("new game", singletonList(START_MENU)),
    MOVE_UP("move up", singletonList(IN_GAME)),
    MOVE_DOWN("move down", singletonList(IN_GAME)),
    MOVE_RIGHT("move right", singletonList(IN_GAME)),
    MOVE_LEFT("move left", singletonList(IN_GAME)),
    SAVE("save", singletonList(IN_GAME)),
    LOAD("load", asList(START_MENU, IN_GAME)),
    EXIT("exit", asList(START_MENU, IN_GAME)),
    ;

    private String value;
    private List<WorldState> states;

    MenuCommand(String value, List<WorldState> states) {
        this.value = value;
        this.states = states;
    }

    public String getValue() {
        return value;
    }

    /**
     * Getting menu command by command string
     *
     * @param command command string
     * @return menu command or null
     */
    public static MenuCommand getMenuCommand(String command) {
        for (MenuCommand menuCommand : values()) {
            if (menuCommand.getValue().equals(command)) {
                return menuCommand;
            }
        }

        return null;
    }

    /**
     * Getting list of menu commands for current world state
     *
     * @param state world state
     * @return List of menu commands
     */
    public static List<MenuCommand> valuesForState(WorldState state) {
        List<MenuCommand> commands = new ArrayList<>();

        for (MenuCommand command : values()) {
            if (command.states.contains(state)) {
                commands.add(command);
            }
        }

        return commands;
    }
}
