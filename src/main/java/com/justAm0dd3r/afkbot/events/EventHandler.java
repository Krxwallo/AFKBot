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

@Mod.EventBusSubscriber()
public class EventHandler {
    @SubscribeEvent
    public static void damageEvent(LivingDamageEvent evt) {
        EntityLivingBase entity = evt.getEntityLiving();
        if (entity instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP)entity;
            if (Config.getAfk(player.getName())) {
                AFKBot.logger.debug("Player " + player.getName() + "got damage while afk.");
                player.connection.disconnect(new TextComponentString(TextFormatting.YELLOW + "You got damage while being afk."
                        + TextFormatting.GREEN +  " You are now" + TextFormatting.WHITE + " not afk" + TextFormatting.GREEN + "."));
                Config.removeAfk(player.getName());
                Config.updateConfigFile();
            }
        }
    }
}
