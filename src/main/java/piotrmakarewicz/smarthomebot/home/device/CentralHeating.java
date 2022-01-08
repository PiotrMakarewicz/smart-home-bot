package piotrmakarewicz.smarthomebot.home.device;

import java.util.concurrent.atomic.AtomicInteger;

public class CentralHeating {
    private AtomicInteger targetTemp = new AtomicInteger(21);
    private AtomicInteger currentTemp = new AtomicInteger(21);

    public CentralHeating() {
        new Thread(() -> {
            try {
                for (int i = 0; i < 1000; i++) {
                    Thread.sleep(5000);
                    if (targetTemp.get() > currentTemp.get()) {
                        currentTemp.incrementAndGet();
                    } else if (targetTemp.get() > currentTemp.get()) {
                        currentTemp.decrementAndGet();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public int getTargetTemp() {
        return targetTemp.get();
    }

    public void setTargetTemp(int targetTemp) {
        this.targetTemp.set(targetTemp);
    }

    public int getCurrentTemp() {
        return currentTemp.get();
    }

}
