package piotrmakarewicz.smarthomebot.home;

import piotrmakarewicz.smarthomebot.home.device.CentralHeating;
import piotrmakarewicz.smarthomebot.home.device.Curtain;
import piotrmakarewicz.smarthomebot.home.device.Light;
import piotrmakarewicz.smarthomebot.home.device.Television;

public class Room {
    private Light light;
    private Television television;
    private CentralHeating centralHeating;
    private Curtain curtain;

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

    public Room addCentralHeating(){
        if (centralHeating == null)
            centralHeating = new CentralHeating();
        return this;
    }

    public Room addCurtain(){
        if (curtain == null)
            curtain = new Curtain();
        return this;
    }

    public Light getLight() {
        return light;
    }

    public Television getTelevision(){
        return television;
    }

    public CentralHeating getCentralHeating() {
        return centralHeating;
    }

    public Curtain getCurtain() {
        return curtain;
    }
}
