package net.savagedev.helpfulhelp;

import net.savagedev.helpfulhelp.commands.HelpCmd;
import net.savagedev.helpfulhelp.commands.HelpReloadCmd;
import org.bukkit.plugin.java.JavaPlugin;

public class HelpfulHelp extends JavaPlugin {
    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.loadCommands();
    }

    private void loadCommands() {
        this.getCommand("helpreload").setExecutor(new HelpReloadCmd(this));
        this.getCommand("help").setExecutor(new HelpCmd(this));
    }
}
