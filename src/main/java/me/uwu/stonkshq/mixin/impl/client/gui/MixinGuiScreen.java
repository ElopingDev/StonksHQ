package me.uwu.stonkshq.mixin.impl.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(GuiScreen.class)
public class MixinGuiScreen {
    @Shadow
    public Minecraft mc;
    @Shadow
    protected List<GuiButton> buttonList;
}
