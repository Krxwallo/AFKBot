package com.justAm0dd3r.afkbot.client.gui;

import com.justAm0dd3r.afkbot.AFKBot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiButtonAfk extends GuiButton {
    public boolean afk;

    public GuiButtonAfk(int buttonId, int x, int y, int widthIn, int heightIn, boolean afkIn) {
        super(buttonId, x, y, widthIn, heightIn, getText(afkIn));
        afk = afkIn;
    }

    public GuiButtonAfk(int buttonId, int x, int y, boolean afkIn) {
        super(buttonId, x, y, getText(afkIn));
        afk = afkIn;
    }

    /**
     * Is used for getting text in constructor
     * @param isAfk the afk value
     * @return the display text
     */
    private static String getText(boolean isAfk) {
        return isAfk ? "TURN AFK ON" : "TURN AFK OFF";
    }

    /**
     * @return the display text
     */
    private String getText() {
        return getText(afk);
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        // Mouse pressed anywhere on the gui.
        // Check if it is on the button.
        if (isMouseOver()) {
            // Mouse pressed when over button -> button pressed.
            AFKBot.logger.info("Button pressed.");
            Minecraft.getMinecraft().player.sendChatMessage("/afkbot " + (afk ? "on" : "off"));
            return true;
        }
        return false;
    }
}
