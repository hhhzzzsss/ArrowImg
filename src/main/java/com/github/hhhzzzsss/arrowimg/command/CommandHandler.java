package com.github.hhhzzzsss.arrowimg.command;

import com.github.hhhzzzsss.arrowimg.command.commands.CircleCommand;
import com.github.hhhzzzsss.arrowimg.command.commands.TargetCommand;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class CommandHandler {
    private final List<ClientCommand> commands = new ArrayList<>();

    public CommandHandler() {
        addCommand(TargetCommand::new);
        addCommand(CircleCommand::new);
    }

    private void addCommand(Supplier<ClientCommand> commandConstructor) {
        commands.add(commandConstructor.get());
    }

    public void registerCommands(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        for (ClientCommand command : commands) {
            dispatcher.register(literal("arrowimg").then(command.getNode()));
        }
    }

    public List<ClientCommand> getCommands() {
        return Collections.unmodifiableList(commands);
    }
}
