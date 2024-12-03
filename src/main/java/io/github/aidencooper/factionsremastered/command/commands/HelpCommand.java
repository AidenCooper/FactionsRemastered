package io.github.aidencooper.factionsremastered.command.commands;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.CommandPlaceholder;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;

@Command({"f", "factions"})
public class HelpCommand {
    @CommandPlaceholder
    public String placeholder(@NotNull BukkitCommandActor actor) {
        return this.help(actor);
    }

    @Subcommand("help")
    public String help(@NotNull BukkitCommandActor actor) {
        return "<red>RED</red>\n<green>GREEN</green>";
    }
}
