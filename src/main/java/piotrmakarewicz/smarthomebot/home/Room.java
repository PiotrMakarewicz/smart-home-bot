package piotrmakarewicz.smarthomebot.home;

import piotrmakarewicz.smarthomebot.home.device.Light;
import piotrmakarewicz.smarthomebot.home.device.Television;

public class Room {
    private Light light;
    private Television television;

    public Room(){
    }

    public Room addLight(){
        if (light == null)
            this.light = new Light();
        return this;
    }

    public Room addTelevision(){
        if (television == null)
            this.television = new Television();
        return this;
    }

    public Light getLight() {
        return light;
    }

    public Television getTelevision(){
        return television;
    }
}
