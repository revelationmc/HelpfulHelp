package net.savagedev.helpfulhelp.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.savagedev.helpfulhelp.HelpfulHelp;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelpCmd implements CommandExecutor {
    private final HelpfulHelp plugin;

    public HelpCmd(HelpfulHelp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            this.sendHelpMessage(sender);
            return true;
        }

        this.sendHelpMessage(sender, args[0]);
        return true;
    }

    private void sendHelpMessage(CommandSender sender) {
        // Send the header message first...
        sender.sendMessage(this.plugin.getConfig().getStringList("help-menu.header")
                .stream()
                .map(str -> ChatColor.translateAlternateColorCodes('&', str))
                .toArray(String[]::new)
        );

        // Loop through the categories.
        for (String category : this.plugin.getConfig().getConfigurationSection("help-menu.categories").getKeys(false)) {
            String command = this.plugin.getConfig().getString(String.format("help-menu.categories.%s.command", category));
            String hover = this.plugin.getConfig().getString(String.format("help-menu.categories.%s.hover", category));
            String name = this.plugin.getConfig().getString(String.format("help-menu.categories.%s.name", category));

            // Build a clickable TextComponent with the name that executes the command.
            HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', hover)).create());
            ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, this.formatCommand(command));

            TextComponent categoryMessage = new TextComponent(ChatColor.translateAlternateColorCodes('&', name));
            categoryMessage.setClickEvent(clickEvent);
            categoryMessage.setHoverEvent(hoverEvent);

            // Finally send the player the TextComponent...
            sender.spigot().sendMessage(categoryMessage);
        }

        // Finally, send the footer message.
        sender.sendMessage(this.plugin.getConfig().getStringList("help-menu.footer")
                .stream()
                .map(str -> ChatColor.translateAlternateColorCodes('&', str))
                .toArray(String[]::new)
        );
    }

    private void sendHelpMessage(CommandSender sender, String query) {
        // Check if the config contains the requested category...
        if (!this.plugin.getConfig().getConfigurationSection("categories").contains(query.toLowerCase())) {
            // If not, send them an error message.
            sender.sendMessage(this.plugin.getConfig().getStringList("unknown-category")
                    .stream()
                    .map(str -> ChatColor.translateAlternateColorCodes('&', str))
                    .toArray(String[]::new)
            );
            return;
        }

        // It does contain that category, so send them more information about that category.
        sender.sendMessage(this.plugin.getConfig().getStringList("categories." + query.toLowerCase())
                .stream()
                .map(str -> ChatColor.translateAlternateColorCodes('&', str))
                .toArray(String[]::new)
        );
    }

    private String formatCommand(String command) {
        return command.startsWith("/") ? command : "/" + command;
    }
}
