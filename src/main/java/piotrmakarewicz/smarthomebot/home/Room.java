package piotrmakarewicz.smarthomebot.home;

import piotrmakarewicz.smarthomebot.home.device.Light;
import piotrmakarewicz.smarthomebot.home.device.Television;

public class Room {
    private final Light light;
    private final Television television;

    public Room(Light light, Television television){
        this.light = light;
        this.television = television;
    }


    public Light getLight() {
        return light;
    }

    public Television getTelevision(){
        return television;
    }
}
