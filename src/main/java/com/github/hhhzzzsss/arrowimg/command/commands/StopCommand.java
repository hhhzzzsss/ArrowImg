package com.github.hhhzzzsss.arrowimg.command.commands;

import com.github.hhhzzzsss.arrowimg.ArrowImg;
import com.github.hhhzzzsss.arrowimg.command.ClientCommand;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class StopCommand extends ClientCommand {
    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> getNode() {
        return
            literal("stop")
                .executes(ctx -> {
                    ctx.getSource().sendFeedback(Text.literal("Stopping all actions"));
                    ArrowImg.INSTANCE.disableActions();
                    return 1;
                });
    }
}
