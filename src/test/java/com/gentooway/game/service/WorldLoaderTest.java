package com.gentooway.game.service;

import com.gentooway.game.model.Room;
import com.gentooway.game.model.World;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class WorldLoaderTest {

    @Test
    void shouldLoadWorldFromTestConfig() {
        // when
        World world = WorldLoader.loadWorld();

        // then
        List<Room> rooms = world.getRooms();
        assertThat(rooms.size(), is(5));

        Room room = rooms.get(0);
        Room leftRoom = rooms.get(1);
        Room rightRoom = rooms.get(2);
        Room upRoom = rooms.get(3);
        Room downRoom = rooms.get(4);

        assertThat(room.getLeft(), is(leftRoom));
        assertThat(room.getRight(), is(rightRoom));
        assertThat(room.getUp(), is(upRoom));
        assertThat(room.getDown(), is(downRoom));

        assertThat(leftRoom.getRight(), is(room));
        assertThat(rightRoom.getLeft(), is(room));
        assertThat(upRoom.getDown(), is(room));
        assertThat(downRoom.getUp(), is(room));


        assertThat(leftRoom.getCreatures().size(), is(2));
        assertThat(downRoom.getCreatures().size(), is(1));
    }
}