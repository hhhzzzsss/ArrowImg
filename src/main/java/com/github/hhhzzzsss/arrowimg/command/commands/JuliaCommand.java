package com.github.hhhzzzsss.arrowimg.command.commands;

import com.github.hhhzzzsss.arrowimg.ArrowImg;
import com.github.hhhzzzsss.arrowimg.command.ClientCommand;
import com.github.hhhzzzsss.arrowimg.generators.CircleGenerator;
import com.github.hhhzzzsss.arrowimg.generators.JuliaSetGenerator;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class JuliaCommand extends ClientCommand {
    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> getNode() {
        return
            literal("julia")
                .then(argument("power", IntegerArgumentType.integer(2))
                .then(argument("c_real", DoubleArgumentType.doubleArg())
                .then(argument("c_imag", DoubleArgumentType.doubleArg())
                .then(argument("scale", DoubleArgumentType.doubleArg(0.1))
                .then(argument("count", IntegerArgumentType.integer(1))
                    .executes(ctx -> {
                        if (!ArrowImg.INSTANCE.hasTarget()) {
                            ctx.getSource().sendFeedback(Text.literal("You must set a target first"));
                            return 1;
                        }
                        ArrowImg.INSTANCE.drawing = true;
                        ArrowImg.INSTANCE.pointGenerator = new JuliaSetGenerator(
                                ArrowImg.INSTANCE.targetPos, ArrowImg.INSTANCE.targetAxis1, ArrowImg.INSTANCE.targetAxis2,
                                ctx.getArgument("power", Integer.class),
                                ctx.getArgument("c_real", Double.class),
                                ctx.getArgument("c_imag", Double.class),
                                ctx.getArgument("scale", Double.class),
                                ctx.getArgument("count", Integer.class)
                        );
                        ArrowImg.INSTANCE.imgLoaded = true;
                        return 0;
                    })
                )))));
    }
}
