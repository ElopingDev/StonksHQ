package me.xtrm.skybot.config;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;
import me.uwu.skybot.Consts;

import java.io.File;

public class SkyBotConfig extends Vigilant {
    @Property(
            type = PropertyType.SWITCH,
            name = "Active",
            description = "Whether the bot is active or not.",
            category = "General"
    )
    public boolean active = true;

    @Property(
            type = PropertyType.SELECTOR,
            name = "Facing",
            description = "Where the fuck to look at",
            category = "Auto Farm",
            options = {"North", "South", "West", "East"}
    )
    public int enumFacingOrd = 0;

    public SkyBotConfig() {
        super(
                new File("./config/skybot.toml"),
                Consts.NAME + " (" + Consts.VERSION + ")"
        );
        initialize();
    }
}
