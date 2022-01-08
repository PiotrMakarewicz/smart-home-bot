package piotrmakarewicz.smarthomebot.home.device;

public class Curtain {
    public Curtain() {
        this.isCovering = true;
    }
    private boolean isCovering;

    public boolean isCovering() {
        return isCovering;
    }

    public void setCovering(boolean isCovering) {
        this.isCovering = isCovering;
    }
}
