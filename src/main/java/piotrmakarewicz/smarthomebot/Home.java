package piotrmakarewicz.smarthomebot;

import org.springframework.stereotype.Component;
import piotrmakarewicz.smarthomebot.device.Light;

@Component
public class Home {
    private final Room kitchen = new Room(new Light(true));
    private final Room storage = new Room(new Light(false));
    private final Room livingRoom = new Room(new Light(true));
    private final Room bathroom = new Room(new Light(true));
    private final Room bedroom = new Room(new Light(true));
    private final Room basement = new Room(new Light(false));

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
