package me.uwu.skybot;

import gg.essential.api.EssentialAPI;
import me.uwu.skybot.command.SkyBotCommand;
import me.xtrm.skybot.config.SkyBotConfig;

public enum SkyBot {
    INSTANCE;

    private SkyBotConfig config;

    SkyBot() {
    }

    public void initialize() {
        this.config = new SkyBotConfig();

        EssentialAPI.getCommandRegistry().registerCommand(new SkyBotCommand());
    }

    public SkyBotConfig getConfig() {
        return config;
    }
}
