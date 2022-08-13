package me.uwu.skybot;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(name = Consts.NAME, modid = Consts.MODID, version = Consts.VERSION)
public class SkyBotMod {
    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        SkyBot.INSTANCE.initialize();
    }
}