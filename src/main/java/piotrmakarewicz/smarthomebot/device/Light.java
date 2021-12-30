package piotrmakarewicz.smarthomebot.device;

public class Light {
    public Light(boolean isOn) {
        this.setOn(isOn);
    }
    private boolean isOn;

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }
}
