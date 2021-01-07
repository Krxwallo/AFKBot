package com.justAm0dd3r.afkbot;

import com.justAm0dd3r.afkbot.commands.ChangeAFKCommand;
import com.justAm0dd3r.afkbot.config.Config;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = AFKBot.MODID, name = AFKBot.NAME, version = AFKBot.VERSION, acceptableRemoteVersions = "*")
public class AFKBot
{
    public static final String MODID = "afkbot";
    public static final String NAME = "AFKBot";
    public static final String VERSION = "1.0";

    public static Logger logger;

    // Config instance
    public static Configuration config;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent evt) {
        logger = evt.getModLog();
        File directory = evt.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), MODID + ".cfg"));
        Config.readConfig();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent evt) {
        if (config.hasChanged()) {
            config.save();
        }
    }

    @Mod.EventHandler
    public static void onServerStart(FMLServerStartingEvent evt) {
        evt.registerServerCommand(new ChangeAFKCommand());
    }
}
