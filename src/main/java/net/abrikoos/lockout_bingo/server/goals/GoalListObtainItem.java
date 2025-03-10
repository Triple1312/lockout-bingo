package net.abrikoos.lockout_bingo.server.goals;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GoalListObtainItem extends GoalListItem {

    List<ItemStack> stacks;

    public GoalListObtainItem(String name, String description, int difficulty, List<LockoutGoalTag> tags, String id, List<ItemStack> stacks) {
        super(name, description, difficulty, tags, id, new ArrayList<>());
        this.stacks = stacks;
    }

    @Override
    public void renderTexture(@NotNull DrawContext ctx, float delta, int x, int y, int width, int height) {
        MatrixStack matrices = ctx.getMatrices();
        matrices.push();
        matrices.translate(x+3, y+3, 0);
        matrices.scale((float) (width -6)/16, (float) (height -6)/16, 1);
        ctx.drawItemWithoutEntity(stacks.get(0), 0, 0);
        matrices.pop();
    }
}
