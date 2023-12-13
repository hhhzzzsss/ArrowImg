package com.github.hhhzzzsss.arrowimg;

import net.fabricmc.api.ClientModInitializer;

public class ArrowImgInitializer implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ArrowImg.INSTANCE.init();
    }
}
