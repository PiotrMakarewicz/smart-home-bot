package piotrmakarewicz.smarthomebot.home;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class Home {
    private final Room kitchen = new Room().addLight();
    private final Room livingRoom = new Room().addLight().addTelevision();
    private final Room bathroom = new Room().addLight().addCurtain();
    private final Room bedroom = new Room().addLight().addCurtain();
    private final Room basement = new Room().addLight().addCentralHeating();

    public Room getRoomByName(String name){
        switch (name){
            case "kuchnia" -> {return kitchen;}
            case "salon" -> {return livingRoom;}
            case "łazienka" -> {return bathroom;}
            case "sypialnia" -> {return bedroom;}
            case "piwnica" -> {return basement;}
            default -> {return null;}
        }
    }

    public Room getKitchen() {
        return kitchen;
    }

    public Room getLivingRoom() {
        return livingRoom;
    }

    public Room getBathroom() {
        return bathroom;
    }

    public Room getBedroom() {
        return bedroom;
    }

    public Room getBasement() {
        return basement;
    }
}
