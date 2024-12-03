package io.github.aidencooper.factionsremastered;

import io.github.aidencooper.factionsremastered.command.commands.HelpCommand;
import io.github.aidencooper.factionsremastered.command.commands.ReloadCommand;
import io.github.aidencooper.factionsremastered.command.responses.StringResponse;
import io.github.aidencooper.factionsremastered.config.ConfigManager;
import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.Lamp;
import revxrsal.commands.bukkit.BukkitLamp;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;

import java.io.IOException;

@Getter
public final class FactionsPlugin extends JavaPlugin {
    private BukkitAudiences audiences;
    private ConfigManager configManager;
    private Lamp<BukkitCommandActor> commandHandler;

    @Override
    public void onEnable() {
        // ADVENTURE API
        this.audiences = BukkitAudiences.create(this);

        // CONFIG
        this.configManager = new ConfigManager(this);
        try {
            this.getConfigManager().loadAll();
        } catch (IOException exception) {
            this.getLogger().severe("Failed to load the config files.");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // COMMANDS
        this.commandHandler = BukkitLamp.builder(this)
                .dependency(FactionsPlugin.class, this)
                .dependency(ConfigManager.class, this.getConfigManager())
                .responseHandler(String.class, new StringResponse(this))
                .build();
        this.getCommandHandler().register(
                new HelpCommand(),
                new ReloadCommand()
        );
    }

    @Override
    public void onDisable() {
        // CONFIG
        try { this.getConfigManager().saveAll(); }
        catch (IOException exception) { this.getLogger().severe("Failed to save the config files."); }

        // ADVENTURE
        this.getAudiences().close();
    }
}
