package com.github.hhhzzzsss.arrowimg.mixin;

import com.github.hhhzzzsss.arrowimg.ArrowImg;
import com.github.hhhzzzsss.arrowimg.ArrowImgInitializer;
import com.github.hhhzzzsss.arrowimg.Util;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow
    public ClientWorld world;

    @Shadow
    public ClientPlayerEntity player;

    @Inject(at = @At("HEAD"), method = "tick()V")
    private void onTick(CallbackInfo ci) {
        if (this.world != null) {
            if (ArrowImg.INSTANCE.imgLoaded) {
                for (int i = 0; i < ArrowImg.INSTANCE.arrowRate; i++) {
                    if (ArrowImg.INSTANCE.index < ArrowImg.INSTANCE.targets.size()) {
                        if (Util.aimAt(ArrowImg.INSTANCE.targets.get(ArrowImg.INSTANCE.index++))) {
                            player.networkHandler.sendPacket(new PlayerMoveC2SPacket.Full(player.getX(), player.getY(), player.getZ(), player.getYaw(), player.getPitch(), player.isOnGround());
                            player.networkHandler.sendPacket(new );
                        }
                    } else {
                        ArrowImg.INSTANCE.disableActions();
                        break;
                    }
                }
            }
//            if (ArrowImg.INSTANCE.drawing) {
//                MinecraftClient.getInstance().player.setVelocity(Vec3d.ZERO);
//                if (ArrowImg.INSTANCE.stayPosition != null) {
//                    if (this.player.getPos().squaredDistanceTo(ArrowImg.INSTANCE.stayPosition) <= 4.0) {
//                        this.player.setPosition(ArrowImg.INSTANCE.stayPosition);
//                    }
//                }
//            }
        } else {
            ArrowImg.INSTANCE.disableActions();
        }
    }
}
