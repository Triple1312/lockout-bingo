package net.abrikoos.blockout.item;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.joml.Quaternionf;

import static net.abrikoos.blockout.item.BlockoutModItems.PLAYER_COMPASS;
import static net.abrikoos.blockout.item.BlockoutModItems.PLAYER_COMPASS_ANGLE;

public class CustomCompassRenderer2 implements BuiltinItemRendererRegistry.DynamicItemRenderer{

    private static final Identifier TEXTURE = Identifier.of("blockout", "compass.png");
    private static final Identifier MODEL = Identifier.of("blockout", "item/custom_compass");

    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        matrices.translate(0.5, 0.5, 0.0);
        Float angle = stack.get(PLAYER_COMPASS_ANGLE);
        if (angle == null) {
            angle = 0.0F;
        }
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(angle * 360.0F));

        RenderLayer renderLayer = RenderLayer.getEntityCutoutNoCull(TEXTURE);
        VertexConsumer consumer = vertexConsumers.getBuffer(renderLayer);
        BakedModel model = MinecraftClient.getInstance().getBakedModelManager().getModel(MODEL);
//        MinecraftClient.getInstance().getItemRenderer().renderBakedItemModel(model, stack, light, overlay, matrices, consumer);




    }
}
