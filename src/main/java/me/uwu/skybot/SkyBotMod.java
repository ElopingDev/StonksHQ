package me.uwu.skybot;

import gg.essential.api.EssentialAPI;
import me.uwu.skybot.command.SkyBotCommand;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(name = Consts.NAME, modid = Consts.MODID, version = Consts.VERSION)
public class SkyBotMod {
    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        EssentialAPI.getCommandRegistry().registerCommand(new SkyBotCommand());
        SkyBot.INSTANCE.initialize();
    }
}