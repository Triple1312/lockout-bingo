package net.abrikoos.lockout_bingo.server.goals;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

//public class GoalListItemM extends GoalListItem {
//
//    final List<ItemStack> stacks;
//    final ItemRenderer itemRenderer;
//
//    public GoalListItemM(String name, String description, int difficulty, List<LockoutGoalTag> tags, String id, List<ItemStack> stacks) {
//        super(name, description, difficulty, tags, id, new ArrayList<>());
//        this.stacks = stacks;
//        this.itemRenderer = MinecraftClient.getInstance().getItemRenderer();
//    }
//
//
//    @Override
//    public void renderTexture(@NotNull DrawContext ctx, float delta, int x, int y, int width, int height) {
//        itemRenderer.renderItem(stacks.getFirst(), ModelTransformationMode.GUI,true, true,   x, y);
//    }
//}
