package com.gentooway.game.menu;

/**
 * Menu commands enum.
 */
public enum MenuCommand {
    HELP("help"),
    MOVE_UP("move up"),
    MOVE_DOWN("move down"),
    MOVE_RIGHT("move right"),
    MOVE_LEFT("move left"),
    SAVE("save"),
    LOAD("load"),
    EXIT("exit"),
    ;

    private String value;

    MenuCommand(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
