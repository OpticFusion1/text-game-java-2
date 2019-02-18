package com.gentooway.game.model.enums;

/**
 * User in game action types.
 */
public enum UserActionType {
    MOVE(1),
    CREATURE_KILL(3),
    BOSS_KILL(10);

    Integer exp;

    UserActionType(Integer exp) {
        this.exp = exp;
    }

    public Integer getExp() {
        return exp;
    }
}
