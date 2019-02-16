package com.gentooway.game.menu;

import com.gentooway.game.model.enums.WorldState;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.gentooway.game.menu.MenuCommand.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

class MenuCommandTest {

    @Test
    void shouldReturnEnumValue() {
        assertThat(MenuCommand.getMenuCommand("help"), is(HELP));
    }

    @Test
    void shouldReturnNullValue() {
        assertThat(MenuCommand.getMenuCommand("123"), nullValue());
    }

    @Test
    void shouldReturnListOfStateDependentMenuCommands() {
        // given
        WorldState state = WorldState.START_MENU;
        List<MenuCommand> expectedStates = Arrays.asList(HELP, LOAD, EXIT);

        // when
        List<MenuCommand> commands = MenuCommand.valuesForState(state);

        // then
        assertThat(commands, is(expectedStates));
    }
}