package net.abrikoos.blockout.client.gui;

import net.abrikoos.blockout.client.ClientGameStateV2;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public class StructureBeamRenderer {

    public static void renderBeams(MatrixStack matrices, Camera camera) {
//        if (!ClientGameStateV2.isGameRunning()) return;
        if (ClientGameStateV2.structures == null) return;
        MinecraftClient mc = MinecraftClient.getInstance();
        ClientWorld world = mc.world;
        if (world == null) return;
        Vec3d cam = camera.getPos();
        long time = world.getTime();
        float tickDelta = 1/20f;
        VertexConsumerProvider.Immediate vcp = mc.getBufferBuilders().getEntityVertexConsumers();

        ClientGameStateV2.structures.structures().forEach(structure -> {
            if (Math.abs(structure.x()-cam.x) + Math.abs(structure.y() - cam.y) > 256) return;
            matrices.push();
            matrices.translate(structure.x() - cam.x, structure.y() - cam.y, structure.z() - cam.z);
            renderBeam(matrices, vcp, time, tickDelta,
                    new float[]{structure.r()/255f, structure.g()/255f, structure.b()/255f}, true);
            matrices.pop();
        });


    }

    private static void renderBeam(MatrixStack matrices,
                                   VertexConsumerProvider vcp,
                                   long worldTime,
                                   float tickDelta,
                                   float[] rgb,
                                   boolean drawInner) {

        float time = worldTime + tickDelta;
        float rot = (time * 0.025f) % 1.0f;                 // rotation phase 0..1
        float angle = time * 0.025f * (float)(Math.PI * 2);

        // Scroll UV so the texture moves upward
        float v0 = -time * 0.2f;
        float v1 = v0 + 256;

        // Build a rotating square (outer shell)
        float x1 = (float)Math.cos(angle) * 0.25f;
        float z1 = (float)Math.sin(angle) * 0.25f;
        float x2 = (float)Math.cos(angle + Math.PI / 2) * 0.25f;
        float z2 = (float)Math.sin(angle + Math.PI / 2) * 0.25f;

        // Outer beam
        VertexConsumer outer = vcp.getBuffer(RenderLayer.getBeaconBeam(Identifier.of("minecraft", "textures/entity/beacon_beam.png"), true));
        Matrix4f posMat = matrices.peek().getPositionMatrix();

        int light = 0x00F000F0; // fullbright packed light
        int alpha = 255;

        // 4 sides (quad strip)
        putQuad(outer, posMat,  x1, 0,  z1,  x1, 256,  z1,  x2, 256,  z2,  x2, 0,  z2,  0.0f, v0, 1.0f, v1, rgb, alpha, light);
        putQuad(outer, posMat,  x2, 0,  z2,  x2, 256,  z2, -x1, 256, -z1, -x1, 0, -z1,  0.0f, v0, 1.0f, v1, rgb, alpha, light);
        putQuad(outer, posMat, -x1, 0, -z1, -x1, 256, -z1, -x2, 256, -z2, -x2, 0, -z2,  0.0f, v0, 1.0f, v1, rgb, alpha, light);
        putQuad(outer, posMat, -x2, 0, -z2, -x2, 256, -z2,  x1, 256,  z1,  x1, 0,  z1,  0.0f, v0, 1.0f, v1, rgb, alpha, light);

        if (drawInner) {
            // Inner core (thinner, additive)
            float ix1 = (float)Math.cos(angle) * 0.2f;
            float iz1 = (float)Math.sin(angle) * 0.2f;
            float ix2 = (float)Math.cos(angle + Math.PI / 2) * 0.2f;
            float iz2 = (float)Math.sin(angle + Math.PI / 2) * 0.2f;

            VertexConsumer inner = vcp.getBuffer(RenderLayer.getBeaconBeam(Identifier.of("minecraft", "textures/entity/beacon_beam.png"), false));
            int innerAlpha = 180;

            putQuad(inner, posMat,  ix1, 0,  iz1,  ix1, 256,  iz1,  ix2, 256,  iz2,  ix2, 0,  iz2,  0.0f, v0, 1.0f, v1, rgb, innerAlpha, light);
            putQuad(inner, posMat,  ix2, 0,  iz2,  ix2, 256,  iz2, -ix1, 256, -iz1, -ix1, 0, -iz1,  0.0f, v0, 1.0f, v1, rgb, innerAlpha, light);
            putQuad(inner, posMat, -ix1, 0, -iz1, -ix1, 256, -iz1, -ix2, 256, -iz2, -ix2, 0, -iz2,  0.0f, v0, 1.0f, v1, rgb, innerAlpha, light);
            putQuad(inner, posMat, -ix2, 0, -iz2, -ix2, 256, -iz2,  ix1, 256,  iz1,  ix1, 0,  iz1,  0.0f, v0, 1.0f, v1, rgb, innerAlpha, light);
        }
    }


    private static void putQuad(
            VertexConsumer vc, Matrix4f mat,
            float x1, float y1, float z1,
            float x2, float y2, float z2,
            float x3, float y3, float z3,
            float x4, float y4, float z4,
            float u0, float v0, float u1, float v1,
            float[] rgb, int alpha, int light) {

        int r = (int)(rgb[0] * 255);
        int g = (int)(rgb[1] * 255);
        int b = (int)(rgb[2] * 255);

        vc.vertex(mat, x1, y1, z1).color(r, g, b, alpha).texture(u0, v0).light(light);
        vc.vertex(mat, x2, y2, z2).color(r, g, b, alpha).texture(u0, v1).light(light);
        vc.vertex(mat, x3, y3, z3).color(r, g, b, alpha).texture(u1, v1).light(light);
        vc.vertex(mat, x4, y4, z4).color(r, g, b, alpha).texture(u1, v0).light(light);
    }



}
