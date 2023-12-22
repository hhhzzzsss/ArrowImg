package com.github.hhhzzzsss.arrowimg.command.commands;

import com.github.hhhzzzsss.arrowimg.ArrowImg;
import com.github.hhhzzzsss.arrowimg.command.ClientCommand;
import com.github.hhhzzzsss.arrowimg.generators.CircleGenerator;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class CircleCommand extends ClientCommand {
    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> getNode() {
        return
            literal("circle")
            .then(
                argument("radius", DoubleArgumentType.doubleArg(0.01))
                .then(
                    argument("count", IntegerArgumentType.integer(1))
                    .executes(ctx -> {
                        ArrowImg.INSTANCE.drawing = true;
                        ArrowImg.INSTANCE.pointGenerator = new CircleGenerator(
                                ArrowImg.INSTANCE.targetPos, ArrowImg.INSTANCE.targetAxis1, ArrowImg.INSTANCE.targetAxis2,
                                ctx.getArgument("radius", Double.class),
                                ctx.getArgument("count", Integer.class)
                        );
                        ArrowImg.INSTANCE.imgLoaded = true;
                        return 0;
                    })
                )
            );
    }
}
