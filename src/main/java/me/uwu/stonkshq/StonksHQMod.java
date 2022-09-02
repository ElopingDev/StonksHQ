package me.uwu.stonkshq;

import com.google.gson.Gson;
import gg.essential.api.EssentialAPI;
import me.uwu.stonkshq.command.SkyBotCommand;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

@Mod(name = Consts.NAME, modid = Consts.MODID, version = Consts.VERSION)
public class StonksHQMod {


    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        StonksHQ.INSTANCE.initialize();

        EssentialAPI.getCommandRegistry().registerCommand(new SkyBotCommand());


    }
}
