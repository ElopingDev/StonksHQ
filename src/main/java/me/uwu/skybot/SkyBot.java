package me.uwu.skybot;

import gg.essential.api.EssentialAPI;
import me.uwu.skybot.command.SkyBotCommand;
import me.xtrm.skybot.config.SkyBotConfig;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(name = Consts.NAME, modid = Consts.MODID, version = Consts.VERSION)
public class SkyBot {
    @Mod.Instance(Consts.MODID)
    public static SkyBot INSTANCE;

    private SkyBotConfig config;

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        this.config = new SkyBotConfig();

        EssentialAPI.getCommandRegistry().registerCommand(new SkyBotCommand());
    }

    public SkyBotConfig getConfig() {
        return config;
    }
}