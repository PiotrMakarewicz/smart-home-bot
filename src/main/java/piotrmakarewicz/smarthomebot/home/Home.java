package piotrmakarewicz.smarthomebot.home;

import org.springframework.stereotype.Component;
import piotrmakarewicz.smarthomebot.home.device.Light;
import piotrmakarewicz.smarthomebot.home.device.Television;

@Component
public class Home {
    private final Room kitchen = new Room(new Light(true), null);
    private final Room storage = new Room(new Light(false), null);
    private final Room livingRoom = new Room(new Light(true), new Television());
    private final Room bathroom = new Room(new Light(true), null);
    private final Room bedroom = new Room(new Light(true), null);
    private final Room basement = new Room(new Light(false), null);

    public Room getRoomByName(String name){
        switch (name){
            case "kuchnia" -> {return kitchen;}
            case "spiżarnia" -> {return storage;}
            case "salon" -> {return livingRoom;}
            case "łazienka" -> {return bathroom;}
            case "sypialnia" -> {return bedroom;}
            case "kotłownia" -> {return basement;}
            default -> {return null;}
        }
    }
}
