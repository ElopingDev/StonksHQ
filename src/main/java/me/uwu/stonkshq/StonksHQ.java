package me.uwu.stonkshq;

import fr.shyrogan.post.EventBus;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import me.uwu.stonkshq.config.StonksConfig;
import me.uwu.stonkshq.event.bus.StonksBusConfiguration;
import me.uwu.stonkshq.system.module.ModuleManager;

@Getter
@Log4j2(topic = Consts.NAME)
public enum StonksHQ {
    INSTANCE;

    private final EventBus eventBus;
    private StonksConfig config;
    private boolean isInitialized = false;

    StonksHQ() {
        this.eventBus = new EventBus(new StonksBusConfiguration());
    }

    public void initialize() {
        if (this.isInitialized) {
            throw new RuntimeException("fuck");
        }
        this.isInitialized = true;
        this.config = new StonksConfig();

        ModuleManager.INSTANCE.initialize();
    }
}
