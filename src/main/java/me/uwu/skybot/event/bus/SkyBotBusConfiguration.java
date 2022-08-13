package me.uwu.skybot.event.bus;

import fr.shyrogan.post.configuration.impl.DefaultEventBusConfiguration;
import fr.shyrogan.post.utils.DynamicClassLoader;
import net.minecraft.launchwrapper.Launch;

public class SkyBotBusConfiguration extends DefaultEventBusConfiguration {
    private final static DynamicClassLoader CLASS_LOADER =
            new DynamicClassLoader(Launch.classLoader);

    @Override
    public DynamicClassLoader classLoader() {
        return CLASS_LOADER;
    }
}
