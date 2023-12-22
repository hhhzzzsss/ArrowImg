package com.github.hhhzzzsss.arrowimg;

import com.github.hhhzzzsss.arrowimg.command.CommandHandler;
import com.github.hhhzzzsss.arrowimg.generators.PointGenerator;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class ArrowImg {
    public static final ArrowImg INSTANCE = new ArrowImg();

    private CommandHandler commandHandler;

    public Vec3d targetPos;
    public Direction targetAxis1;
    public Direction targetAxis2;

    public boolean drawing = false;
    public boolean imgLoaded = false;
    public PointGenerator pointGenerator = null;

    public void init() {
        commandHandler = new CommandHandler();
        ClientCommandRegistrationCallback.EVENT.register(commandHandler::registerCommands);
    }

    public void disableActions() {
        drawing = false;
        imgLoaded = false;
        pointGenerator = null;
    }

    public boolean hasTarget() {
        return targetPos != null && targetAxis1 != null && targetAxis2 != null;
    }
}
