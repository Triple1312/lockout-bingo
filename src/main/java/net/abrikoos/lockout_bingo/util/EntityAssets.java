package net.abrikoos.lockout_bingo.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.abrikoos.lockout_bingo.client.ClientGameStateV2;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityAssets {

    private static Map<EntityType, Identifier> entityAssets = new HashMap<>();

    private static int TEXTURE_SIZE = 64;

    private static List<Framebuffer> fbs = new ArrayList<>();

    private static List<Identifier> textureIdentifiers = new ArrayList<>();

    public static void generateEntityAssets() {
        ClientWorld world = ClientGameStateV2.client.world;
        entityAssets.put(EntityType.COW, generateEntityAsset(EntityType.COW.create(world)));
        entityAssets.put(EntityType.PIG, generateEntityAsset(EntityType.PIG.create(world)));
        entityAssets.put(EntityType.CHICKEN, generateEntityAsset(EntityType.CHICKEN.create(world)));
        entityAssets.put(EntityType.SHEEP, generateEntityAsset(EntityType.SHEEP.create(world)));
        entityAssets.put(EntityType.WOLF, generateEntityAsset(EntityType.WOLF.create(world)));
        entityAssets.put(EntityType.OCELOT, generateEntityAsset(EntityType.OCELOT.create(world)));
        entityAssets.put(EntityType.PARROT, generateEntityAsset(EntityType.PARROT.create(world)));
        entityAssets.put(EntityType.CAT, generateEntityAsset(EntityType.CAT.create(world)));
        entityAssets.put(EntityType.FOX, generateEntityAsset(EntityType.FOX.create(world)));
        entityAssets.put(EntityType.PANDA, generateEntityAsset(EntityType.PANDA.create(world)));
        entityAssets.put(EntityType.POLAR_BEAR, generateEntityAsset(EntityType.POLAR_BEAR.create(world)));
        entityAssets.put(EntityType.RABBIT, generateEntityAsset(EntityType.RABBIT.create(world)));
        entityAssets.put(EntityType.LLAMA, generateEntityAsset(EntityType.LLAMA.create(world)));
        entityAssets.put(EntityType.DONKEY, generateEntityAsset(EntityType.DONKEY.create(world)));
        entityAssets.put(EntityType.MULE, generateEntityAsset(EntityType.MULE.create(world)));
        entityAssets.put(EntityType.HORSE, generateEntityAsset(EntityType.HORSE.create(world)));
        entityAssets.put(EntityType.SKELETON_HORSE, generateEntityAsset(EntityType.SKELETON_HORSE.create(world)));
        entityAssets.put(EntityType.ZOMBIE_HORSE, generateEntityAsset(EntityType.ZOMBIE_HORSE.create(world)));
        entityAssets.put(EntityType.STRIDER, generateEntityAsset(EntityType.STRIDER.create(world)));
        entityAssets.put(EntityType.HOGLIN, generateEntityAsset(EntityType.HOGLIN.create(world)));
        entityAssets.put(EntityType.PIGLIN, generateEntityAsset(EntityType.PIGLIN.create(world)));
        entityAssets.put(EntityType.ZOMBIFIED_PIGLIN, generateEntityAsset(EntityType.ZOMBIFIED_PIGLIN.create(world)));
        entityAssets.put(EntityType.PIGLIN_BRUTE, generateEntityAsset(EntityType.PIGLIN_BRUTE.create(world)));
        entityAssets.put(EntityType.VILLAGER, generateEntityAsset(EntityType.VILLAGER.create(world)));
        entityAssets.put(EntityType.WANDERING_TRADER, generateEntityAsset(EntityType.WANDERING_TRADER.create(world)));
        entityAssets.put(EntityType.IRON_GOLEM, generateEntityAsset(EntityType.IRON_GOLEM.create(world)));
        entityAssets.put(EntityType.SNOW_GOLEM, generateEntityAsset(EntityType.SNOW_GOLEM.create(world)));
        entityAssets.put(EntityType.CREEPER, generateEntityAsset(EntityType.CREEPER.create(world)));
        entityAssets.put(EntityType.SPIDER, generateEntityAsset(EntityType.SPIDER.create(world)));
        entityAssets.put(EntityType.CAVE_SPIDER, generateEntityAsset(EntityType.CAVE_SPIDER.create(world)));
        entityAssets.put(EntityType.ENDERMAN, generateEntityAsset(EntityType.ENDERMAN.create(world)));
        entityAssets.put(EntityType.SILVERFISH, generateEntityAsset(EntityType.SILVERFISH.create(world)));
        entityAssets.put(EntityType.BLAZE, generateEntityAsset(EntityType.BLAZE.create(world)));
        entityAssets.put(EntityType.GHAST, generateEntityAsset(EntityType.GHAST.create(world)));
        entityAssets.put(EntityType.MAGMA_CUBE, generateEntityAsset(EntityType.MAGMA_CUBE.create(world)));
        entityAssets.put(EntityType.ZOMBIE, generateEntityAsset(EntityType.ZOMBIE.create(world)));
        entityAssets.put(EntityType.SKELETON, generateEntityAsset(EntityType.SKELETON.create(world)));
        entityAssets.put(EntityType.WITHER_SKELETON, generateEntityAsset(EntityType.WITHER_SKELETON.create(world)));
        entityAssets.put(EntityType.STRAY, generateEntityAsset(EntityType.STRAY.create(world)));
        entityAssets.put(EntityType.HUSK, generateEntityAsset(EntityType.HUSK.create(world)));
        entityAssets.put(EntityType.DROWNED, generateEntityAsset(EntityType.DROWNED.create(world)));
        entityAssets.put(EntityType.ZOMBIE_VILLAGER, generateEntityAsset(EntityType.ZOMBIE_VILLAGER.create(world)));
        entityAssets.put(EntityType.PILLAGER, generateEntityAsset(EntityType.PILLAGER.create(world)));
        entityAssets.put(EntityType.VINDICATOR, generateEntityAsset(EntityType.VINDICATOR.create(world)));
        entityAssets.put(EntityType.EVOKER, generateEntityAsset(EntityType.EVOKER.create(world)));
        entityAssets.put(EntityType.RAVAGER, generateEntityAsset(EntityType.RAVAGER.create(world)));
        entityAssets.put(EntityType.ILLUSIONER, generateEntityAsset(EntityType.ILLUSIONER.create(world)));
        entityAssets.put(EntityType.WITCH, generateEntityAsset(EntityType.WITCH.create(world)));
        entityAssets.put(EntityType.SLIME, generateEntityAsset(EntityType.SLIME.create(world)));
        entityAssets.put(EntityType.WITHER, generateEntityAsset(EntityType.WITHER.create(world)));
        entityAssets.put(EntityType.ENDER_DRAGON, generateEntityAsset(EntityType.ENDER_DRAGON.create(world)));
        entityAssets.put(EntityType.GUARDIAN, generateEntityAsset(EntityType.GUARDIAN.create(world)));
        entityAssets.put(EntityType.ELDER_GUARDIAN, generateEntityAsset(EntityType.ELDER_GUARDIAN.create(world)));
        entityAssets.put(EntityType.SHULKER, generateEntityAsset(EntityType.SHULKER.create(world)));
        entityAssets.put(EntityType.PHANTOM, generateEntityAsset(EntityType.PHANTOM.create(world)));
        entityAssets.put(EntityType.WARDEN, generateEntityAsset(EntityType.WARDEN.create(world)));
    }

    private static Identifier generateEntityAsset(Entity entity) {
        Framebuffer framebuffer = new SimpleFramebuffer(TEXTURE_SIZE, TEXTURE_SIZE, true, MinecraftClient.IS_SYSTEM_MAC);
        framebuffer.beginWrite(true);
        RenderSystem.clear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT, MinecraftClient.IS_SYSTEM_MAC);
        MatrixStack matrixStack = new MatrixStack();
        matrixStack.translate(TEXTURE_SIZE / 2.0, TEXTURE_SIZE / 2.0, 50);
        matrixStack.scale(-30.0f, 30.0f, 30.0f);

        EntityRenderDispatcher dispatcher = ClientGameStateV2.client.getEntityRenderDispatcher();
        dispatcher.setRenderShadows(false);

        VertexConsumerProvider.Immediate vertexConsumers = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();

        dispatcher.render(entity, 0, 0, 0, 0, 1.0f, matrixStack, vertexConsumers, 15728880);
        vertexConsumers.draw();
        dispatcher.setRenderShadows(true);
        framebuffer.endWrite();
        fbs.add(framebuffer);

        RenderSystem.bindTexture(framebuffer.getColorAttachment());
        NativeImage image = new NativeImage(TEXTURE_SIZE, TEXTURE_SIZE, true);
        image.loadFromTextureImage(0, true);
        NativeImageBackedTexture texture = new NativeImageBackedTexture(image);
        texture.upload();
        Identifier identifier = ClientGameStateV2.client.getTextureManager().registerDynamicTexture("entity_" + entity.toString(), texture);
        textureIdentifiers.add(identifier);
        return identifier;
    }

    public static boolean isGenerated() {
        return !entityAssets.isEmpty();
    }

    public static Identifier getEntityAssetIdentifier(EntityType entityType) {
        return entityAssets.get(entityType);
    }


}
