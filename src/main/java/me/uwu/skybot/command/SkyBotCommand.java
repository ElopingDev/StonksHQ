package me.uwu.skybot.command;

import gg.essential.api.commands.Command;
import gg.essential.api.commands.DefaultHandler;
import gg.essential.api.commands.SubCommand;
import gg.essential.api.utils.GuiUtil;
import me.uwu.skybot.SkyBot;
import me.uwu.skybot.system.module.impl.farming.AutoFarm;
import me.uwu.skybot.system.module.impl.farming.TestModule;
import me.uwu.skybot.system.module.ModuleManager;

import java.util.Objects;

public class SkyBotCommand extends Command {
    public SkyBotCommand() {
        super("skybot", true);
    }

    @DefaultHandler
    @SubCommand(
            value = "gui",
            description = "Open the configuration gui."
    )
    public void openGui() {
        GuiUtil.open(Objects.requireNonNull(SkyBot.INSTANCE.getConfig().gui()));
    }

    @SubCommand(
            value = "farm",
            description = "TOwOggles FAwArming."
    )
    public void farm() {
        boolean value = !SkyBot.INSTANCE.getConfig().autoFarm;
        SkyBot.INSTANCE.getConfig().autoFarm = value;
        ModuleManager.INSTANCE.typed(AutoFarm.class).setEnabled(value);
    }

    @SubCommand(
            value = "test",
            description = "TEST"
    )
    public void test() {
        ModuleManager.INSTANCE.typed(TestModule.class).toggle();
    }
}
