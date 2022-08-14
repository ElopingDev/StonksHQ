package me.uwu.stonkshq;

import gg.essential.api.EssentialAPI;
import me.uwu.stonkshq.command.SkyBotCommand;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(name = Consts.NAME, modid = Consts.MODID, version = Consts.VERSION)
public class StonksHQMod {
    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        EssentialAPI.getCommandRegistry().registerCommand(new SkyBotCommand());
        StonksHQ.INSTANCE.initialize();
    }
}