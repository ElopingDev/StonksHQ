package me.uwu.skybot.command;

import gg.essential.api.commands.Command;
import gg.essential.api.commands.DefaultHandler;
import gg.essential.api.utils.GuiUtil;
import me.uwu.skybot.SkyBot;

import java.util.Objects;

public class SkyBotCommand extends Command {
    public SkyBotCommand() {
        super("skybot");
    }

    @DefaultHandler
    public void handle() {
        GuiUtil.open(Objects.requireNonNull(SkyBot.INSTANCE.getConfig().gui()));
    }
}
