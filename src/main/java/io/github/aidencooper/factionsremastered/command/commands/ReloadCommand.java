package io.github.aidencooper.factionsremastered.command.commands;

import io.github.aidencooper.factionsremastered.FactionsPlugin;
import io.github.aidencooper.factionsremastered.config.ConfigManager;
import lombok.AccessLevel;
import lombok.Getter;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Dependency;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;

import java.io.IOException;

@Getter(AccessLevel.PRIVATE)
@Command({"f", "factions"})
public class ReloadCommand {
    @Dependency private ConfigManager configManager;
    @Dependency private FactionsPlugin plugin;

    @Subcommand("reload")
    public String reload(BukkitCommandActor actor) {
        try { this.getConfigManager().reloadAll(); }
        catch (IOException exception) {
            return "ERROR";
        }

        return this.getConfigManager().getLang().getString("prefix") + this.getConfigManager().getLang().getString("description.reload");
    }
}
