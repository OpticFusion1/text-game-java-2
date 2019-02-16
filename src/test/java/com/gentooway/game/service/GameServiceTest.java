package com.gentooway.game.service;

import com.gentooway.game.model.Character;
import com.gentooway.game.model.Room;
import com.gentooway.game.model.World;
import com.gentooway.game.model.enums.WorldState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private World world;

    @InjectMocks
    private GameService gameService;

    @Test
    void shouldThrowExceptionIfWorldsRoomsAreEmpty() {
        assertThrows(RuntimeException.class, () -> gameService.createNewGame());
    }

    @Test
    void shouldCreateNewCharacter() {
        // given
        Room room = new Room();
        Room secondRoom = new Room();
        when(world.getRooms()).thenReturn(asList(room, secondRoom));

        // when
        gameService.createNewGame();

        // then
        ArgumentCaptor<Character> captor = ArgumentCaptor.forClass(Character.class);

        verify(world).setCharacter(captor.capture());
        verify(world).setState(WorldState.IN_GAME);

        Character character = captor.getValue();
        assertThat(character.getCurrentRoom(), is(room));
        assertThat(character.getExperience(), is(0));
        assertThat(character.getHealth(), is(100));
        assertThat(character.getLevel(), is(1));
    }
}