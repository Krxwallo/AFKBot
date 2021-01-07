package com.justAm0dd3r.afkbot.config;

import com.justAm0dd3r.afkbot.AFKBot;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.CheckReturnValue;

@MethodsReturnNonnullByDefault
public class Config {
    // Logger
    private static final Logger LOGGER = LogManager.getLogger(AFKBot.MODID);

    private static final String CATEGORY_GENERAL = "general";
    private static final String AFK_LIST_KEY = "afk_list";

    // Config Values
    public static String[] afkList = new String[] {};
    public static Property afkListProperty;

    public static void readConfig() {
        Configuration cfg = AFKBot.config;
        try {
            cfg.load();
            initGeneralConfig(cfg);
        } catch (Exception e1) {
            LOGGER.error("Problem loading config file!", e1);
        } finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }
    }

    // Update the config file when the value was changed ingame by a command.
    public static void updateConfigFile() {
        afkListProperty.set(afkList);
        AFKBot.config.save();
    }

    private static void initGeneralConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General configuration");
        afkList = cfg.getStringList(AFK_LIST_KEY, CATEGORY_GENERAL, afkList, "List of currently afk players.");
        afkListProperty = cfg.get(CATEGORY_GENERAL, AFK_LIST_KEY, afkList, "List of currently afk players.");
    }

    @CheckReturnValue
    public static boolean getAfk(String name) {
        return ArrayUtils.contains(afkList, name);
    }

    public static String[] setAfk(String name) {
        return afkList = ArrayUtils.add(afkList, name);
    }

    public static String[] setAfk(String name, boolean value) {
        if (value) return setAfk(name);
        else       return removeAfk(name);
    }

    public static String[] removeAfk(String name) {
        return afkList = ArrayUtils.removeAllOccurences(afkList, name);
    }
}