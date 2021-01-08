package com.justAm0dd3r.afkbot.events;

import com.justAm0dd3r.afkbot.AFKBot;
import com.justAm0dd3r.afkbot.config.Config;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

// Static server event handler -> doesn't need to get registered.
@Mod.EventBusSubscriber(value = Side.SERVER)
public class ServerEventHandler {
    @SubscribeEvent
    public static void damageEvent(LivingDamageEvent evt) {
        EntityLivingBase entity = evt.getEntityLiving();
        if (entity instanceof EntityPlayerMP) {
            // A player got damaged.
            EntityPlayerMP player = (EntityPlayerMP)entity;
            // Check if the player is marked afk.
            if (Config.getAfk(player.getName())) {
                // Player is marked afk.
                AFKBot.logger.debug("Player " + player.getName() + "got damage while afk.");
                // Disconnect player
                player.connection.disconnect(new TextComponentString(TextFormatting.YELLOW + "You got damage while being afk."
                        + TextFormatting.GREEN +  " You are now" + TextFormatting.WHITE + " not afk" + TextFormatting.GREEN + "."));
                // Mark player as not afk anymore.
                Config.removeAfk(player.getName());
                // Update the config file.
                Config.updateConfigFile();
            }
        }
    }
}
