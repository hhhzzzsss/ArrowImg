package com.github.hhhzzzsss.arrowimg;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class Util {
    public static Double getLaunchAngle(double tx, double ty, double g, double d, double v) {
        if (tx < ty * 0.001) { // If it's near the asymptotes, just return a vertical angle
            return ty>0 ? Math.PI/2.0 : -Math.PI/2.0;
        }

        double md = 1.0-d;
        double log_md = Math.log(md);
        double g_d = g/d; // This is terminal velocity
        double theta = Math.atan2(ty, tx);
        double prev_abs_ydif = Double.POSITIVE_INFINITY;

        // 20 iterations max, although it usually converges in 3 iterations
        for (int i=0; i<20; i++) {
            double cost = Math.cos(theta);
            double sint = Math.sin(theta);
            double tant = sint/cost;
            double vx = v * cost;
            double vy = v * sint;
            double y = tx*(g_d+vy)/vx - g_d*Math.log(1-d*tx/vx)/log_md;
            double ydif = y-ty;
            double abs_ydif = Math.abs(ydif);

            // If it's getting farther away, there's probably no solution
            if (abs_ydif>prev_abs_ydif) {
                return null;
            }
            else if (abs_ydif < 0.0001) {
                return theta;
            }

            double dy_dtheta = tx + g*tx*tant / ((-d*tx+v*cost)*log_md) + g*tx*tant/(d*v*cost) + tx*tant*tant;
            theta -= ydif/dy_dtheta;
            prev_abs_ydif = abs_ydif;
        }

        // If exceeded max iterations, return null
        return null;
    }

    public static boolean aimAt(Vec3d target) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        double dx = target.getX() - player.getX();
        double dy = target.getY() - player.getEyeY();
        double dz = target.getZ() - player.getZ();
        Double launchAngle = getLaunchAngle(Math.sqrt(dx*dx+dz*dz), dy, 0.05, 0.01, 3.15);
        if (launchAngle == null) {
            return false;
        } else {
            player.setPitch((float) (-launchAngle*180.0/Math.PI));
            player.setYaw(MathHelper.wrapDegrees((float)(MathHelper.atan2(dz, dx) * 57.2957763671875) - 90.0f));
            player.setHeadYaw(player.getYaw());
            return true;
        }
    }

    public static void holdLoadedCrossbow() {
        ItemStack crossbow = Items.CROSSBOW.getDefaultStack();

        CrossbowItem.setCharged(crossbow, true);

        ItemStack projectile = Items.SPECTRAL_ARROW.getDefaultStack();

        NbtCompound crossbowNbt = crossbow.getOrCreateNbt();
        NbtList nbtList = crossbowNbt.contains("ChargedProjectiles", NbtElement.LIST_TYPE) ? crossbowNbt.getList("ChargedProjectiles", NbtElement.COMPOUND_TYPE) : new NbtList();
        NbtCompound projectileStackNbt = new NbtCompound();
        projectile.writeNbt(projectileStackNbt);
        nbtList.add(projectileStackNbt);
        crossbowNbt.put("ChargedProjectiles", nbtList);

        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        ClientPlayerInteractionManager interactionManager = MinecraftClient.getInstance().interactionManager;
        player.getInventory().setStack(player.getInventory().selectedSlot, crossbow);
        interactionManager.clickCreativeStack(crossbow, 9 + (27 + player.getInventory().selectedSlot) % 36);
    }

    public static BlockHitResult raycast() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return null;
        HitResult hitRes = player.raycast(1000, 0, false);
        if (hitRes != null && hitRes instanceof BlockHitResult) {
            return (BlockHitResult) hitRes;
        } else {
            return null;
        }
    }

    public static boolean isHorizontal(Direction dir) {
        return dir == Direction.NORTH || dir == Direction.EAST || dir == Direction.SOUTH || dir == Direction.WEST;
    }
}
