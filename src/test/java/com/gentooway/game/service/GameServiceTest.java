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

import java.nio.file.Paths;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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

    @Test
    void shouldMoveToTheUpRoom() {
        // given
        Room currentRoom = new Room();
        currentRoom.setWelcomeMessage("currentRoom message");

        Room upRoom = new Room();
        upRoom.setWelcomeMessage("upRoom message");

        currentRoom.setUp(upRoom);

        Character character = new Character();
        character.setCurrentRoom(currentRoom);
        when(world.getCharacter()).thenReturn(character);

        // when
        gameService.moveUp();

        // then
        assertThat(character.getCurrentRoom(), is(upRoom));
        assertThat(character.getExperience(), is(1));
    }

    @Test
    void shouldMoveToTheDownRoom() {
        // given
        Room currentRoom = new Room();
        currentRoom.setWelcomeMessage("currentRoom message");

        Room downRoom = new Room();
        downRoom.setWelcomeMessage("downRoom message");

        currentRoom.setDown(downRoom);

        Character character = new Character();
        character.setCurrentRoom(currentRoom);
        when(world.getCharacter()).thenReturn(character);

        // when
        gameService.moveDown();

        // then
        assertThat(character.getCurrentRoom(), is(downRoom));
        assertThat(character.getExperience(), is(1));
    }

    @Test
    void shouldMoveToTheLeftRoom() {
        // given
        Room currentRoom = new Room();
        currentRoom.setWelcomeMessage("currentRoom message");

        Room leftRoom = new Room();
        leftRoom.setWelcomeMessage("leftRoom message");

        currentRoom.setLeft(leftRoom);

        Character character = new Character();
        character.setCurrentRoom(currentRoom);
        when(world.getCharacter()).thenReturn(character);

        // when
        gameService.moveLeft();

        // then
        assertThat(character.getCurrentRoom(), is(leftRoom));
        assertThat(character.getExperience(), is(1));
    }

    @Test
    void shouldMoveToTheRightRoom() {
        // given
        Room currentRoom = new Room();
        currentRoom.setWelcomeMessage("currentRoom message");

        Room rightRoom = new Room();
        rightRoom.setWelcomeMessage("rightRoom message");

        currentRoom.setRight(rightRoom);

        Character character = new Character();
        character.setCurrentRoom(currentRoom);
        when(world.getCharacter()).thenReturn(character);

        // when
        gameService.moveRight();

        // then
        assertThat(character.getCurrentRoom(), is(rightRoom));
        assertThat(character.getExperience(), is(1));
    }

    @Test
    void shouldStayAtTheSameRoomIfThereIsNoWay() {
        // given
        Room currentRoom = new Room();
        currentRoom.setWelcomeMessage("currentRoom message");

        Character character = new Character();
        character.setCurrentRoom(currentRoom);
        when(world.getCharacter()).thenReturn(character);

        // when
        gameService.moveUp();

        // then
        assertThat(character.getCurrentRoom(), is(currentRoom));
        assertThat(character.getExperience(), is(0));
    }

    @Test
    void shouldSaveCurrentWorldState() {
        // when
        gameService.save();

        // then
        assertThat(Paths.get("world_save").toFile().exists(), is(true));
    }

    @Test
    void shouldLoadSavedWorldState() {
        // given
        world = spy(World.class);
        gameService = new GameService(world);

        Room currentRoom = new Room();
        currentRoom.setId(1L);
        currentRoom.setWelcomeMessage("currentRoom message");

        Room rightRoom = new Room();
        rightRoom.setId(2L);
        rightRoom.setWelcomeMessage("rightRoom message");

        currentRoom.setRight(rightRoom);

        Character character = new Character();
        character.setCurrentRoom(currentRoom);
        world.setCharacter(character);

        gameService.save();
        gameService.moveRight();

        // when
        gameService.load();

        // then
        assertThat(gameService.getWorld().getCharacter().getCurrentRoom().getId(), is(currentRoom.getId()));
    }

    @Test
    void shouldPrintCharacterStats() {
        // given
        when(world.getCharacter()).thenReturn(new Character());

        // when
        gameService.showStats();
    }

    @Test
    void shouldReachANewLevelWhileMovingUp() {
        // given
        Room currentRoom = new Room();
        currentRoom.setUp(new Room());

        Character character = new Character();
        character.setLevel(4);
        character.setExperience(99);
        character.setCurrentRoom(currentRoom);
        when(world.getCharacter()).thenReturn(character);

        // when
        gameService.moveUp();

        // then
        assertThat(character.getLevel(), is(5));
    }
}