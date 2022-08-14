package me.uwu.skybot;

import fr.shyrogan.post.EventBus;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import me.uwu.skybot.event.bus.SkyBotBusConfiguration;
import me.uwu.skybot.config.SkyBotConfig;
import me.uwu.skybot.system.module.ModuleManager;

@Getter
@Log4j2(topic = "SkyBot")
public enum SkyBot {
    INSTANCE;

    private final EventBus eventBus;
    private SkyBotConfig config;

    SkyBot() {
        this.eventBus = new EventBus(new SkyBotBusConfiguration());
    }

    public void initialize() {
        this.config = new SkyBotConfig();

        ModuleManager.INSTANCE.initialize();
    }
}
