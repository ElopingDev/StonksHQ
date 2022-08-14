package me.uwu.stonkshq.command;

import gg.essential.api.commands.Command;
import gg.essential.api.commands.DefaultHandler;
import gg.essential.api.commands.SubCommand;
import gg.essential.api.utils.GuiUtil;
import me.uwu.stonkshq.StonksHQ;
import me.uwu.stonkshq.system.module.impl.farming.AutoFarm;
import me.uwu.stonkshq.system.module.impl.misc.TestModule;
import me.uwu.stonkshq.system.module.ModuleManager;

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
        GuiUtil.open(Objects.requireNonNull(StonksHQ.INSTANCE.getConfig().gui()));
    }

    @SubCommand(
            value = "farm",
            description = "TOwOggles FAwArming."
    )
    public void farm() {
        boolean value = !StonksHQ.INSTANCE.getConfig().autoFarm;
        StonksHQ.INSTANCE.getConfig().autoFarm = value;
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
