package io.github.aidencooper.factionsremastered.command.responses;

import io.github.aidencooper.factionsremastered.FactionsPlugin;
import lombok.AccessLevel;
import lombok.Getter;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.response.ResponseHandler;

@Getter(AccessLevel.PRIVATE)
public class StringResponse implements ResponseHandler<BukkitCommandActor, String> {
    @NotNull private final FactionsPlugin plugin;

    public StringResponse(@NotNull FactionsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void handleResponse(String string, ExecutionContext<BukkitCommandActor> executionContext) {
        this.getPlugin().getAudiences().sender(executionContext.actor().sender()).sendMessage(MiniMessage.miniMessage().deserialize(string));
    }
}
