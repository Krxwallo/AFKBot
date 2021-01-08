package com.justAm0dd3r.afkbot.events;

import com.justAm0dd3r.afkbot.client.gui.GuiButtonAfk;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

// Static client event handler -> doesn't need to get registered.
@Mod.EventBusSubscriber(value = Side.CLIENT)
public class ClientEventHandler {
    // Static functions get automatically called without needing to get registered.
    @SubscribeEvent
    public static void onInitGui(GuiScreenEvent.InitGuiEvent.Post evt) {
        // Use .Post because then the buttons were already added and one can modify them.
        // Any Gui was opened
        if (evt.getGui() instanceof GuiIngameMenu) {
            // Ingame Menu was opened. Now we can change the gui and/or add buttons.
            GuiIngameMenu gui = ((GuiIngameMenu) evt.getGui());
            // Add own buttons for changing afk at the bottom of the screen.
            GuiButton buttonOn  = new GuiButtonAfk(8, gui.width / 2 - 100, gui.height / 4 + 120 + 32, 98, 20,true);
            GuiButton buttonOff = new GuiButtonAfk(8, gui.width / 2 +   2, gui.height / 4 + 120 + 32, 98, 20,false);
            evt.getButtonList().add(buttonOn);
            evt.getButtonList().add(buttonOff);
        }
    }
}
