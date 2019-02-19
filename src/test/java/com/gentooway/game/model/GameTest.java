package com.gentooway.game.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Scanner;

import static com.gentooway.game.menu.MenuCommand.EXIT;
import static com.gentooway.game.menu.MenuCommand.HELP;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameTest {

    @Mock
    private Scanner scanner;

    @Mock
    private World world;

    @InjectMocks
    private Game game = new Game(world);

    @Test
    void shouldStartGameHandleInputAndCloseResourceAfterExit() {
        // given
        when(scanner.nextLine())
                .thenReturn(HELP.getValue())
                .thenReturn("123")
                .thenReturn(EXIT.getValue());

        // when
        game.start();

        // then
        verify(scanner).close();
    }
}