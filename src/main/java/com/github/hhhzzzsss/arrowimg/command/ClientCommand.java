package com.github.hhhzzzsss.arrowimg.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public abstract class ClientCommand {
    protected ClientCommand() {}

    public abstract LiteralArgumentBuilder<FabricClientCommandSource> getNode();
}
