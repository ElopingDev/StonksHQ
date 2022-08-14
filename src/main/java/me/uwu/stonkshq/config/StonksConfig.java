package me.uwu.stonkshq.config;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;
import lombok.SneakyThrows;
import me.uwu.stonkshq.Consts;
import me.uwu.stonkshq.system.module.ModuleManager;
import me.uwu.stonkshq.system.module.impl.farming.AutoFarm;
import me.uwu.stonkshq.system.module.impl.misc.FastPlace;

import java.io.File;

public class StonksConfig extends Vigilant {
    @Property(
            type = PropertyType.SWITCH,
            name = "Enabled",
            description = "Is this thing on?",
            category = "Auto Farm"
    )
    public boolean autoFarm = false;

    @Property(
            type = PropertyType.SELECTOR,
            name = "Facing",
            description = "Where the fuck to look at.",
            category = "Auto Farm",
            options = {"North", "South", "West", "East"}
    )
    public int enumFacingOrd = 0;

    @Property(
            type = PropertyType.SWITCH,
            name = "Unstuck",
            description = "Stops you from being a whiney bitch.",
            category = "Auto Farm"
    )
    public boolean unstuck = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Enabled",
            description = "brrrrrrrrrrrrrrrrrrrrrrrrrr.",
            category = "Fast Place"
    )
    public boolean fastPlace = false;

    @SneakyThrows
    public StonksConfig() {
        super(
                new File("./config/skybot.toml"),
                Consts.NAME + " (" + Consts.VERSION + ")"
        );
        initialize();

        registerListener(
                getClass().getDeclaredField("autoFarm"),
                state -> ModuleManager.INSTANCE.typed(AutoFarm.class)
                        .setEnabled((boolean) state)
        );
        registerListener(
                getClass().getDeclaredField("fastPlace"),
                state -> ModuleManager.INSTANCE.typed(FastPlace.class)
                        .setEnabled((boolean) state)
        );
    }
}
