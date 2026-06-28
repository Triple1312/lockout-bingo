package net.abrikoos.blockout.server;

import net.abrikoos.blockout.server.listeners.TickListener;
import net.minecraft.server.MinecraftServer;

import java.util.Random;

public class ThunderyWeatherCycle {
    private static final int MINIMAL_TIMEOUT = 8400; // 7 minutes
    private static final int MAXIMAL_TIMEOUT = 14400; // 12 minutes
    private static final int THUNDERING_DURATION = 1200; // 1 minute

    private Random random;

    private int thunderTicks = 0;
    private int thunderTimeout = 0;
    private boolean isThundering = false;

    public ThunderyWeatherCycle() {
        this.random = new Random();
        restart();
    }

    public int getThunderTicks() {
        return thunderTicks;
    }

    public int getThunderTimeout() {
        return thunderTimeout;
    }

    public boolean isThundering() {
        return isThundering;
    }

    private int generateTimeoutLength() {
        return MINIMAL_TIMEOUT + random.nextInt(MAXIMAL_TIMEOUT - MINIMAL_TIMEOUT + 1);
    }

    public void tick(MinecraftServer server) {
        if (isThundering()) {
            thunderTicks--;
            if (thunderTicks <= 0) {
                isThundering = false;
                thunderTimeout = generateTimeoutLength();
                server.getWorlds().forEach(world -> world.setWeather(0, thunderTimeout, false, false));
            }
        } else {
            thunderTimeout--;
            if (thunderTimeout <= 0) {
                isThundering = true;
                thunderTicks = THUNDERING_DURATION;
                server.getWorlds().forEach(world -> world.setWeather(0, 0, true, true));
            }
        }
    }

    public void stop() {
        TickListener.unsubscribe(this::tick);
    }

    public void restart() {
        this.thunderTimeout = generateTimeoutLength();
        this.thunderTicks = 0;
        this.isThundering = false;
        TickListener.subscribe(this::tick);
    }

}
