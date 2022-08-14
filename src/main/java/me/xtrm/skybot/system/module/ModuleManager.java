package me.xtrm.skybot.system.module;

import fr.shyrogan.post.receiver.annotation.Subscribe;
import lombok.extern.log4j.Log4j2;
import me.uwu.skybot.SkyBot;
import me.uwu.skybot.event.impl.EventKeyboard;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;

@Log4j2(topic = "SkyBot/ModuleManager")
public enum ModuleManager {
    INSTANCE;

    private final Reflections reflect =
            new Reflections("me.uwu.skybot.system.module.impl");
    private final Map<String, Module> moduleMap;
    private final Map<Module, Module.Metadata> moduleMetadataMap;

    ModuleManager() {
        this.moduleMap = new HashMap<>();
        this.moduleMetadataMap = new HashMap<>();
    }

    public void initialize() {
        reflect.getTypesAnnotatedWith(Module.Metadata.class).forEach(clazz -> {
            if (!Module.class.isAssignableFrom(clazz))
                return;
            Module.Metadata metadata =
                    clazz.getDeclaredAnnotation(Module.Metadata.class);
            if (metadata == null)
                return;
            try {
                Module module = (Module) clazz.getConstructor().newInstance();
                this.moduleMap.put(metadata.id(), module);
                this.moduleMetadataMap.put(module, metadata);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        });

        log.info("Constructed {} modules.", this.moduleMap.size());

        this.moduleMetadataMap.forEach((mod, meta) -> {
            if (meta.defaultState()) {
                mod.toggle();
            }
        });

        SkyBot.INSTANCE.getEventBus().subscribe(this);
    }

    public Module named(String id) {
        return this.moduleMap.get(id);
    }

    @SuppressWarnings("unchecked")
    public <T extends Module> T typed(Class<T> type) {
        return (T) this.moduleMap.values()
                .stream()
                .filter(it -> it.getClass() == type)
                .findFirst()
                .orElse(null);
    }

    @Subscribe
    public void onKey(EventKeyboard eventKeyboard) {
        this.moduleMetadataMap.entrySet()
                .stream()
                .filter(it -> it.getValue().keybind() == eventKeyboard.getKey())
                .map(Map.Entry::getKey)
                .forEach(Module::toggle);
    }
}
