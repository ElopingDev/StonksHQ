package me.uwu.stonkshq.ui.font;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import me.uwu.stonkshq.Consts;
import me.uwu.stonkshq.api.ui.font.IFontRenderer;
import me.uwu.stonkshq.ui.font.glyph.GlyphPage;
import me.uwu.stonkshq.ui.font.glyph.GlyphPageFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2(topic = "delta-core/FontLoader")
public enum FontManager {
    INSTANCE;

    private final List<FontData> loadedFonts = new ArrayList<>();
    private final Map<FontData, IFontRenderer> fontRenderers = new HashMap<>();

    private IFontRenderer minecraftRenderer;

    public void initialize() {
        if (!loadedFonts.isEmpty()) return;

        this.loadedFonts.addAll(
                Arrays.asList(
                        new Gson().fromJson(
                                new InputStreamReader(
                                        Objects.requireNonNull(
                                                getClass().getResourceAsStream(
                                                        "/assets/"
                                                                + Consts.MODID
                                                                + "/fonts/font_list.json"
                                                )
                                        )
                                ), FontData[].class
                        )
                )
        );

        this.loadedFonts.forEach(f -> {
            IFontRenderer fontRenderer = generateFontRenderer(f);
            this.fontRenderers.put(f, fontRenderer);
        });

        log.info(
                "Available fonts: \"{}\"",
                loadedFonts.stream()
                        .map(FontData::getFontName)
                        .collect(Collectors.joining("\", \""))
        );
    }

    private IFontRenderer generateFontRenderer(FontData font) {
        log.info("Generating FontRenderer via Synaptic... (" + font.getFontName() + " @ " + font.getBaseSize() + "px)");

        char[] charset = new char[256];
        for (int i = 0; i < charset.length; i++) {
            charset[i] = (char) i;
        }

        InputStream is = getClass().getResourceAsStream(
                "/assets/"
                        + Consts.MODID
                        + "/fonts/"
                        + font.getFontName().toLowerCase()
                        + "/data.ttf"
        );
        assert is != null;

        Font baseFont;
        try {
            baseFont = Font.createFont(0, is);
        } catch (Throwable t) {
            throw new RuntimeException("Couldn't load font " + font.getFontName() + "!");
        }

        Font normal = baseFont.deriveFont(Font.PLAIN, font.getBaseSize());
        Font bold = baseFont.deriveFont(Font.BOLD, font.getBaseSize());
        Font italic = baseFont.deriveFont(Font.ITALIC, font.getBaseSize());
        Font boldItalic = baseFont.deriveFont(Font.BOLD | Font.ITALIC, font.getBaseSize());

        GlyphPage page = new GlyphPage(normal, true, true);
        page.generateGlyphPage(charset);
        page.setupTexture();
        GlyphPage page2 = new GlyphPage(bold, true, true);
        page2.generateGlyphPage(charset);
        page2.setupTexture();
        GlyphPage page3 = new GlyphPage(italic, true, true);
        page3.generateGlyphPage(charset);
        page3.setupTexture();
        GlyphPage page4 = new GlyphPage(boldItalic, true, true);
        page4.generateGlyphPage(charset);
        page4.setupTexture();

        return new GlyphPageFontRenderer(page, page2, page3, page4);
    }

    private IFontRenderer generateMinecraftRenderer() {
        return new IFontRenderer() {
            private final FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;

            @Override
            public int getFontHeight() {
                return fr.FONT_HEIGHT;
            }

            @Override
            public int getStringWidth(String text) {
                return fr.getStringWidth(text);
            }

            @Override
            public void drawString(String text, double x, double y, int color) {
                fr.drawString(text, (int) x, (int) y, color);
            }

            @Override
            public void drawStringWithShadow(String text, double x, double y, int color) {
                fr.drawStringWithShadow(text, (int) x, (int) y, color);
            }
        };
    }

    public IFontRenderer getFontRenderer(String name) {
        if (name == null || name.isEmpty()) return null;

        if (loadedFonts.isEmpty() || name.equalsIgnoreCase("minecraft")) {
            if (this.minecraftRenderer == null) {
                this.minecraftRenderer = generateMinecraftRenderer();
            }
            return minecraftRenderer;
        }

        String fontName = name;
        int sizeModifier = -1;

        int index = name.indexOf('/');
        if (index != -1) {
            fontName = name.substring(0, index);
            try {
                sizeModifier = Integer.parseInt(name.substring(index + 1));
            } catch (Throwable ignored) {
                log.warn("Error while parsing size modifier in font declaration: \"" + name + "\"");
            }
        }

        return getFontRenderer(fontName, sizeModifier);
    }

    public IFontRenderer getFontRenderer(String fontName, int size) {
        FontData data = new FontData(fontName, size);
        if (size == -1) {
            data = this.loadedFonts.stream().filter(d -> d.getFontName().equalsIgnoreCase(fontName)).findFirst().orElse(data);
        }
        return getFontRenderer(data);
    }

    private IFontRenderer getFontRenderer(FontData fontData) {
        return this.fontRenderers.computeIfAbsent(fontData, this::generateFontRenderer);
    }
}
