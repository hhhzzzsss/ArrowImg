package com.github.hhhzzzsss.arrowimg;

import com.github.hhhzzzsss.arrowimg.command.CommandHandler;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

public class ArrowImg {
    public static final ArrowImg INSTANCE = new ArrowImg();

    private CommandHandler commandHandler;

    public double arrowDensity = 5.0;
    public double imageSize = 10.0;
    public int arrowRate = 5;

    public boolean drawing = false;
    public boolean imgLoaded = false;
    public ArrayList<Vec3d> targets = null;
    public int index = 0;

    public void init() {
        commandHandler = new CommandHandler();
        ClientCommandRegistrationCallback.EVENT.register(commandHandler::registerCommands);
    }

    public void disableActions() {
        drawing = false;
        imgLoaded = false;
        targets = null;
        index = 0;
    }
}
