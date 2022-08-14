package me.uwu.skybot.command;

import gg.essential.api.EssentialAPI;
import gg.essential.api.commands.Command;
import gg.essential.api.commands.DefaultHandler;
import gg.essential.api.commands.SubCommand;
import gg.essential.api.utils.GuiUtil;
import me.uwu.skybot.SkyBot;
import me.uwu.skybot.system.module.impl.farming.AutoFarm;
import me.xtrm.skybot.system.module.ModuleManager;
import net.minecraft.util.EnumChatFormatting;

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
            description = "Toggles farming."
    )
    public void farm() {
        boolean value = !SkyBot.INSTANCE.getConfig().autoFarm;
        SkyBot.INSTANCE.getConfig().autoFarm = value;
        EssentialAPI.getNotifications().push(
                "Auto Farm",
                "Farmbot is now " + (
                        value
                                ? EnumChatFormatting.GREEN + "enabled"
                                : EnumChatFormatting.RED + "disabled"
                ) + EnumChatFormatting.RESET + "."
        );
        ModuleManager.INSTANCE.typed(AutoFarm.class).setEnabled(value);
    }
}
