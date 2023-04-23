package net.savagedev.helpfulhelp.commands;

import net.savagedev.helpfulhelp.HelpfulHelp;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelpReloadCmd implements CommandExecutor {
    private final HelpfulHelp plugin;

    public HelpReloadCmd(HelpfulHelp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        this.plugin.reloadConfig();
        sender.sendMessage(ChatColor.GREEN + "Plugin reloaded.");
        return true;
    }
}
