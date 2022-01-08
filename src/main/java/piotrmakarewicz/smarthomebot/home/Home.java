package piotrmakarewicz.smarthomebot.home;

import org.springframework.stereotype.Component;

@Component
public class Home {
    private final Room kitchen = new Room().addLight();
    private final Room storage = new Room().addLight();
    private final Room livingRoom = new Room().addLight().addTelevision();
    private final Room bathroom = new Room().addLight();
    private final Room bedroom = new Room().addLight();
    private final Room basement = new Room().addLight();

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
