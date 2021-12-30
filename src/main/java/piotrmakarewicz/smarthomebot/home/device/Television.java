package piotrmakarewicz.smarthomebot.home.device;

public class Television {
    private boolean isOn;
    private TvChannel channel;

    public Television() {
        this.setOn(false);
        this.setChannel(null);
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    public TvChannel getChannel() {
        return channel;
    }

    public void setChannel(TvChannel channel) {
        this.channel = channel;
    }
}
