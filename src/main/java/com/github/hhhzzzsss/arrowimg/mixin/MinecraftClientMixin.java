package com.github.hhhzzzsss.arrowimg.mixin;

import com.github.hhhzzzsss.arrowimg.ArrowImg;
import com.github.hhhzzzsss.arrowimg.Util;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
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

    @Shadow
    public ClientPlayerInteractionManager interactionManager;

    @Inject(at = @At("HEAD"), method = "tick()V")
    private void onTick(CallbackInfo ci) {
        if (world != null) {
            if (ArrowImg.INSTANCE.imgLoaded) {
                if (ArrowImg.INSTANCE.pointGenerator.hasNext()) {
                    if (Util.aimAt(ArrowImg.INSTANCE.pointGenerator.next())) {
                        Util.holdLoadedCrossbow();
                        interactionManager.interactItem(player, Hand.MAIN_HAND);
                    }
                } else {
                    ArrowImg.INSTANCE.disableActions();
                }
            }
        } else {
            ArrowImg.INSTANCE.disableActions();
        }
    }
}
