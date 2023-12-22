package com.github.hhhzzzsss.arrowimg.command.commands;

import com.github.hhhzzzsss.arrowimg.ArrowImg;
import com.github.hhhzzzsss.arrowimg.Util;
import com.github.hhhzzzsss.arrowimg.command.ClientCommand;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class TargetCommand extends ClientCommand {
    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> getNode() {
        return
            literal("target")
            .then(
                literal("set")
                .executes(ctx -> {
                    BlockHitResult blockHitRes = Util.raycast();
                    if (blockHitRes == null) {
                        ctx.getSource().sendFeedback(Text.literal("You must be looking at a block"));
                        return 1;
                    }
                    ArrowImg.INSTANCE.targetPos = blockHitRes.getPos();
                    if (Util.isHorizontal(blockHitRes.getSide())) {
                        ArrowImg.INSTANCE.targetAxis1 = blockHitRes.getSide().rotateCounterclockwise(Direction.Axis.Y);
                        ArrowImg.INSTANCE.targetAxis2 = Direction.UP;
                    } else {
                        Direction playerDirection = Direction.fromRotation(ctx.getSource().getPlayer().getYaw(0));
                        ArrowImg.INSTANCE.targetAxis1 = playerDirection.rotateClockwise(Direction.Axis.Y);
                        if (blockHitRes.getSide() == Direction.UP) {
                            ArrowImg.INSTANCE.targetAxis2 = playerDirection;
                        } else {
                            ArrowImg.INSTANCE.targetAxis2 = playerDirection.getOpposite();
                        }
                    }
                    ctx.getSource().sendFeedback(Text.literal("Set target at %.1f %.1f %.1f".formatted(blockHitRes.getPos().x, blockHitRes.getPos().y, blockHitRes.getPos().z)));
                    return 0;
                })
            ).then(
                literal("clear")
                .executes(ctx -> {
                    ArrowImg.INSTANCE.targetPos = null;
                    ArrowImg.INSTANCE.targetAxis1 = null;
                    ArrowImg.INSTANCE.targetAxis2 = null;
                    ctx.getSource().sendFeedback(Text.literal("Cleared target"));
                    return 0;
                })
            );
    }
}
