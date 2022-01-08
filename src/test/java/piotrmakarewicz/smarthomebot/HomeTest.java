package piotrmakarewicz.smarthomebot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import piotrmakarewicz.smarthomebot.home.Home;
import piotrmakarewicz.smarthomebot.home.Room;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class HomeTest {
    @Autowired Home home;

    @Test
    public void getRoomByName(){
        // given
        // Home - autowired

        // when
        Room kitchen = home.getRoomByName("kuchnia");
        Room livingRoom = home.getRoomByName("salon");
        Room bathroom = home.getRoomByName("łazienka");
        Room bedroom = home.getRoomByName("sypialnia");
        Room basement = home.getRoomByName("piwnica");

        // then
        for (Room room: List.of(kitchen, livingRoom, bathroom, bedroom, basement)){
            assertNotNull(room);
            assertNotNull(room.getLight());
        }
        assertNotNull(livingRoom.getTelevision());
    }
}

