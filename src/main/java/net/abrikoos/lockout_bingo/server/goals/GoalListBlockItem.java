package net.abrikoos.lockout_bingo.server.goals;

import net.abrikoos.lockout_bingo.client.ClientGameStateV2;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GoalListBlockItem extends GoalListItem {

    List<ItemStack> stacks;

    public GoalListBlockItem(String name, String description, int difficulty, List<LockoutGoalTag> tags, String id, List<ItemStack> stacks) {
        super(name, description, difficulty, tags, id, new ArrayList<>());
        this.stacks = stacks;
    }

    @Override
    public void renderTexture(@NotNull DrawContext ctx, float delta, int x, int y, int width, int height) {
        MatrixStack matrices = ctx.getMatrices();
        matrices.push();
        matrices.translate(x+3, y+3, 0);
        matrices.scale((float) (width -6)/16, (float) (height -6)/16, 1);

//        ctx.drawItemInSlot(ClientGameStateV2.client.textRenderer,stacks.get(0), 0, 0);
        ctx.drawItemWithoutEntity(stacks.get(0), 0, 0);
//        matrices.scale((float) 16/(width -6), (float) 16/(height -6), 1);
//        if (stacks.get(0).getCount() > 1) {
//            ctx.drawItemInSlot(ClientGameStateV2.client.textRenderer,stacks.get(0), width /2, height /2);
////            ctx.drawText(ClientGameStateV2.client.textRenderer, String.valueOf(stacks.get(0).getCount()), width /2, height /2, 0xFFFFFF, true);
//        }
        matrices.pop();
    }
}
