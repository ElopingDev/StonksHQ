package me.uwu.stonkshq.event.bus;

import fr.shyrogan.post.configuration.impl.DefaultEventBusConfiguration;
import fr.shyrogan.post.utils.DynamicClassLoader;
import net.minecraft.launchwrapper.Launch;

public class StonksBusConfiguration extends DefaultEventBusConfiguration {
    private final static DynamicClassLoader CLASS_LOADER =
            new DynamicClassLoader(Launch.classLoader);


    @Override
    public DynamicClassLoader classLoader() {

        return CLASS_LOADER;

    }
}
