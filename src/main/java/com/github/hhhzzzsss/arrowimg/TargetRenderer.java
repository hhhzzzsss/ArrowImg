package com.github.hhhzzzsss.arrowimg;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;

public class TargetRenderer {
    public static void render(MatrixStack matrixStack) {
        if (!ArrowImg.INSTANCE.hasTarget()) {
            return;
        }

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        matrixStack.push();

        Camera camera = MinecraftClient.getInstance().getBlockEntityRenderDispatcher().camera;
        if (camera == null) return;
        Vec3d camPos = camera.getPos();

        matrixStack.translate(ArrowImg.INSTANCE.targetPos.x - camPos.x, ArrowImg.INSTANCE.targetPos.y - camPos.y, ArrowImg.INSTANCE.targetPos.z - camPos.z);

        drawSquareFill(matrixStack, ArrowImg.INSTANCE.targetAxis1, ArrowImg.INSTANCE.targetAxis2);
        drawSquareOutline(matrixStack, ArrowImg.INSTANCE.targetAxis1, ArrowImg.INSTANCE.targetAxis2);
        drawAxes(matrixStack, ArrowImg.INSTANCE.targetAxis1, ArrowImg.INSTANCE.targetAxis2);

        matrixStack.pop();

        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.lineWidth(1.0f);
        RenderSystem.enableCull();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
    }



    private static void drawSquareFill(MatrixStack matrixStack, Direction axis1, Direction axis2) {
        RenderSystem.setShaderColor(0.5f, 0.5f, 1.0f, 0.25f);

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionProgram);

        Vec3d pos1 = Vec3d.ZERO.offset(axis1, -0.5).offset(axis2, -0.5);
        Vec3d pos2 = Vec3d.ZERO.offset(axis1, 0.5).offset(axis2, -0.5);
        Vec3d pos3 = Vec3d.ZERO.offset(axis1, 0.5).offset(axis2, 0.5);
        Vec3d pos4 = Vec3d.ZERO.offset(axis1, -0.5).offset(axis2, 0.5);

        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);

        bufferBuilder.vertex(matrix, (float)pos1.x, (float)pos1.y, (float)pos1.z).next();
        bufferBuilder.vertex(matrix, (float)pos2.x, (float)pos2.y, (float)pos2.z).next();
        bufferBuilder.vertex(matrix, (float)pos3.x, (float)pos3.y, (float)pos3.z).next();
        bufferBuilder.vertex(matrix, (float)pos4.x, (float)pos4.y, (float)pos4.z).next();
        bufferBuilder.vertex(matrix, (float)pos1.x, (float)pos1.y, (float)pos1.z).next();

        tessellator.draw();
    }

    private static void drawSquareOutline(MatrixStack matrixStack, Direction axis1, Direction axis2) {
        RenderSystem.setShaderColor(0.5f, 0.5f, 1.0f, 0.5f);

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionProgram);

        Vec3d pos1 = Vec3d.ZERO.offset(axis1, -0.5).offset(axis2, -0.5);
        Vec3d pos2 = Vec3d.ZERO.offset(axis1, 0.5).offset(axis2, -0.5);
        Vec3d pos3 = Vec3d.ZERO.offset(axis1, 0.5).offset(axis2, 0.5);
        Vec3d pos4 = Vec3d.ZERO.offset(axis1, -0.5).offset(axis2, 0.5);

        bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION);

        bufferBuilder.vertex(matrix, (float)pos1.x, (float)pos1.y, (float)pos1.z).next();
        bufferBuilder.vertex(matrix, (float)pos2.x, (float)pos2.y, (float)pos2.z).next();
        bufferBuilder.vertex(matrix, (float)pos3.x, (float)pos3.y, (float)pos3.z).next();
        bufferBuilder.vertex(matrix, (float)pos4.x, (float)pos4.y, (float)pos4.z).next();
        bufferBuilder.vertex(matrix, (float)pos1.x, (float)pos1.y, (float)pos1.z).next();

        tessellator.draw();
    }

    public static void drawAxes(MatrixStack matrixStack, Direction axis1, Direction axis2) {
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 0.5f);
        RenderSystem.lineWidth(2.5f);
        RenderSystem.disableCull();

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getRenderTypeLinesProgram);

        Vec3d pos0 = Vec3d.ZERO;
        Vec3d pos1 = Vec3d.ZERO.offset(axis1, 1.0);
        Vec3d pos2 = Vec3d.ZERO.offset(axis2, 1.0);
        Vector4f pos0T = matrix.transform(new Vector4f(pos0.toVector3f(), 1.0f));
        Vector4f pos1T = matrix.transform(new Vector4f(pos1.toVector3f(), 1.0f));
        Vector4f pos2T = matrix.transform(new Vector4f(pos2.toVector3f(), 1.0f));
        pos0T.div(pos0T.w);
        pos1T.div(pos1T.w);
        pos2T.div(pos2T.w);
        float n1x = pos1T.x-pos0T.x;
        float n1y = pos1T.y-pos0T.y;
        float n2x = pos2T.x-pos0T.x;
        float n2y = pos2T.y-pos0T.y;

        bufferBuilder.begin(VertexFormat.DrawMode.LINES, VertexFormats.LINES);

        bufferBuilder.vertex(matrix, (float)pos0.x, (float)pos0.y, (float)pos0.z).color(1.0f, 0.0f, 0.0f, 1.0f).normal(n1x, n1y, 0).next();
        bufferBuilder.vertex(matrix, (float)pos1.x, (float)pos1.y, (float)pos1.z).color(1.0f, 0.0f, 0.0f, 1.0f).normal(n1x, n1y, 0).next();

        bufferBuilder.vertex(matrix, (float)pos2.x, (float)pos2.y, (float)pos2.z).color(0.0f, 0.0f, 1.0f, 1.0f).normal(n2x, n2y, 0).next();
        bufferBuilder.vertex(matrix, (float)pos0.x, (float)pos0.y, (float)pos0.z).color(0.0f, 0.0f, 1.0f, 1.0f).normal(n2x, n2y, 0).next();

        tessellator.draw();
    }
}
