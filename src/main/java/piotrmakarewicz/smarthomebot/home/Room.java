package piotrmakarewicz.smarthomebot.home;

import piotrmakarewicz.smarthomebot.home.device.Light;

public class Room {
    private final Light light;
    public Room(Light light){
        this.light = light;
    }


    public Light getLight() {
        return light;
    }
}
