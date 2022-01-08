package piotrmakarewicz.smarthomebot.home.device;

public class Light {
    public Light() {
        this.isOn = true;
    }
    private boolean isOn;

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }
}
