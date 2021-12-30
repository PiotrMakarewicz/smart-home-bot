package piotrmakarewicz.smarthomebot;

import piotrmakarewicz.smarthomebot.device.Light;

public class Room {
    private final Light light;
    public Room(Light light){
        this.light = light;
    }


    public Light getLight() {
        return light;
    }
}
