package me.uwu.skybot.command;

import gg.essential.api.commands.Command;
import gg.essential.api.commands.SubCommand;
import gg.essential.api.utils.GuiUtil;
import me.uwu.skybot.SkyBot;

import java.util.Objects;

public class SkyBotCommand extends Command {
    public SkyBotCommand() {
        super("skybot", true);
    }

    @SubCommand(
            value = "gui",
            aliases = {"config", "ui", "open"},
            description = "Open the configuration gui."
    )
    public void openGui() {
        GuiUtil.open(Objects.requireNonNull(SkyBot.INSTANCE.getConfig().gui()));
    }

    @SubCommand(
            value = "start",
            aliases = {"run", "go"},
            description = "Starts the bot."
    )
    public void start() {
        // oui
    }

    @SubCommand(
            value = "stop",
            aliases = {"end"},
            description = "Stops the bot."
    )
    public void stop() {
        // oui
    }
}
