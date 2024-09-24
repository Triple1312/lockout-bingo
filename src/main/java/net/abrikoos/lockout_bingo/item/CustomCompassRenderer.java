package net.abrikoos.lockout_bingo.item;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;

//import static net.abrikoos.lockout_bingo.item.LockoutModItems.PLAYER_COMPASS_LOCATION;
//
//public class CustomCompassRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
//
//
//    public static final Identifier CUSTOM_PLAYER_COMPASS_TEXTURE = Identifier.of("lockout-bingo", "textures/item/player_compass.png");
//
//
//    @Override
//    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
//        MinecraftClient client = MinecraftClient.getInstance();
//        ItemRenderer itemRenderer = client.getItemRenderer();
//        Identifier texture = Identifier.of("minecraft", "item/compass");
//
//        client.getTextureManager().bindTexture(texture);
//        BlockPos target_pos = stack.get(PLAYER_COMPASS_LOCATION).target().get().pos();
//        Vec3d rotation = BlockPos.min(target_pos, client.player.getBlockPos()).toCenterPos();
//        matrices.push();
//        matrices.multiply(new Quaternionf(rotation.x, rotation.y, rotation.z, 1));
//        itemRenderer.renderItem(stack, mode, false, matrices, vertexConsumers, light, overlay, itemRenderer.getModel(stack, client.world, null, 0));
//        matrices.pop();
//
//    }
//
//
//
//}
