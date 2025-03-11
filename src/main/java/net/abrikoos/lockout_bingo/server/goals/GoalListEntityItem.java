package net.abrikoos.lockout_bingo.server.goals;

import com.mojang.blaze3d.systems.RenderSystem;
import net.abrikoos.lockout_bingo.client.ClientGameStateV2;
import net.abrikoos.lockout_bingo.util.EntityAssets;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class GoalListEntityItem extends GoalListItem {

    List<EntityType> entities;

    public GoalListEntityItem(String name, String description, int difficulty, List<LockoutGoalTag> tags, String id, List<EntityType> entities) {
        super(name, description, difficulty, tags, id, new ArrayList<>());
        this.entities = entities;
        try {
            if (!EntityAssets.isGenerated()) {
                EntityAssets.generateEntityAssets();
            }
            for (EntityType entity : entities) {
                this.resourceids.add(EntityAssets.getEntityAssetIdentifier(entity));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void renderTexture(@NotNull DrawContext ctx, float delta, int x, int y, int width, int height) {
        try {
            if (!EntityAssets.isGenerated()) {
                EntityAssets.generateEntityAssets();
            }
            for (EntityType entity : entities) {
                this.resourceids.add(EntityAssets.getEntityAssetIdentifier(entity));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
