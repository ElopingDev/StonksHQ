package me.uwu.skybot;

import fr.shyrogan.post.EventBus;
import gg.essential.api.EssentialAPI;
import lombok.Getter;
import me.uwu.skybot.command.SkyBotCommand;
import me.uwu.skybot.event.bus.SkyBotBusConfiguration;
import me.xtrm.skybot.config.SkyBotConfig;

@Getter
public enum SkyBot {
    INSTANCE;

    private final EventBus eventBus;
    private SkyBotConfig config;

    SkyBot() {
        this.eventBus = new EventBus(new SkyBotBusConfiguration());
    }

    public void initialize() {
        this.config = new SkyBotConfig();

        EssentialAPI.getCommandRegistry().registerCommand(new SkyBotCommand());
    }
}
