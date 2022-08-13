package me.uwu.skybot.command;

import gg.essential.api.commands.Command;
import gg.essential.api.commands.SubCommand;
import gg.essential.api.utils.GuiUtil;
import me.uwu.skybot.SkyBot;
import me.uwu.skybot.system.module.impl.misc.AutoFarm;
import me.xtrm.skybot.system.module.ModuleManager;

import java.util.Objects;

public class SkyBotCommand extends Command {
    public SkyBotCommand() {
        super("skybot", true);
    }

    @SubCommand(
            value = "gui",
            description = "Open the configuration gui."
    )
    public void openGui() {
        GuiUtil.open(Objects.requireNonNull(SkyBot.INSTANCE.getConfig().gui()));
    }

    @SubCommand(
            value = "start",
            description = "Starts the bot."
    )
    public void start() {
        // oui
    }

    @SubCommand(
            value = "farm",
            description = "Starts farming."
    )
    public void farm() {
        ModuleManager.INSTANCE.typed(AutoFarm.class).setEnabled(true);
    }

    @SubCommand(
            value = "stop",
            description = "Stops the bot."
    )
    public void stop() {
        ModuleManager.INSTANCE.typed(AutoFarm.class).setEnabled(false);
    }
}
