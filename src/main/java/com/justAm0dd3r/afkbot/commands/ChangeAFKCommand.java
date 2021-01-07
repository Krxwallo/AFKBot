package com.justAm0dd3r.afkbot.commands;

import com.google.common.collect.Lists;
import com.justAm0dd3r.afkbot.AFKBot;
import com.justAm0dd3r.afkbot.config.Config;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

public class ChangeAFKCommand extends CommandBase {
    private final List<String> aliases = Lists.newArrayList("afkb");

    @Nonnull
    @Override
    public String getName() {
        return AFKBot.MODID;
    }

    @Nonnull
    @Override
    public String getUsage(@Nonnull ICommandSender sender) {
        return "jsvpercentage <percentage>";
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Nonnull
    @Override
    public List<String> getAliases() {
        return aliases;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) throws CommandException {
        if (args.length < 1) {
            sender.sendMessage(new TextComponentString(TextFormatting.BLUE + "Usage: " + TextFormatting.RESET + "afkbot " + TextFormatting.ITALIC + "<on/off/toggle>"));
            return;
        }

        String name = Objects.requireNonNull(sender.getCommandSenderEntity()).getName();
        boolean oldAFK = Config.getAfk(name);
        boolean afk;
        String arg = args[0];
        switch (arg) {
            case "on":
                afk = true;
                break;
            case "off":
                afk = false;
                break;
            case "toggle":
                afk = !oldAFK;
                break;
            default:
                sender.sendMessage(new TextComponentString(TextFormatting.RED + "Bad afk value: " + TextFormatting.WHITE + arg
                        + TextFormatting.RED + " | Must be either " + TextFormatting.WHITE + "on " + TextFormatting.RED + ", "
                        + TextFormatting.WHITE + "off" + TextFormatting.RED + " or " + TextFormatting.WHITE + "toggle" + TextFormatting.RED + "."));
                return;
        }

        // Same-as-the-last-1-check
        if (afk == oldAFK) {
            sender.sendMessage(new TextComponentString(TextFormatting.YELLOW + "You are already " + TextFormatting.WHITE
                    + (afk ? "" : "not ") + "afk" + TextFormatting.YELLOW + "."));
            return;
        }

        Config.setAfk(name, afk);
        Config.updateConfigFile();

        sender.sendMessage(new TextComponentString(TextFormatting.GREEN + "You are now " + TextFormatting.WHITE + (afk ? "" : "not ") + "afk" + TextFormatting.RESET + TextFormatting.GREEN + "."));
    }
}
