package net.abrikoos.blockout.server.goals;

import io.netty.buffer.ByteBuf;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.texture.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class GoalListItem {
    public String name;
    public String description;
    public int difficulty;
    public List<Identifier> resourceids;
    public List<BlockoutGoalTag> tags;
    public String id;
    protected float delta = 0;
    public Sprite sprite;

    public GoalListItem(String name, String description, int difficulty, List<BlockoutGoalTag> tags, String id, List<Identifier> resourceids) {
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.resourceids = resourceids;
        this.tags = tags;
        this.id = id;
    }

    boolean hasTag(BlockoutGoalTag tag) {
        return tags.contains(tag);
    }

    boolean redstone() {
        return hasTag(BlockoutGoalTag.redstone);
    }

    boolean end() {
        return hasTag(BlockoutGoalTag.end);
    }

    boolean nether() {
        return hasTag(BlockoutGoalTag.nether);
    }

    boolean die() {
        return hasTag(BlockoutGoalTag.die);
    }

    boolean dont() {
        return hasTag(BlockoutGoalTag.dont);
    }

    boolean advancement() {
        return hasTag(BlockoutGoalTag.advancement);
    }

    boolean eat() {
        return hasTag(BlockoutGoalTag.eat);
    }

    boolean breed() {
        return hasTag(BlockoutGoalTag.breed);
    }

    public void renderTexture(@NotNull DrawContext ctx, float delta, int x, int y, int width, int height) {
        ctx.drawTexture(this.resourceids.get((int) (this.delta /80 % resourceids.size())), x+3, y+3, 0, 0, width-6, height-6, width-6, height-6);
    }

    public void draw(@NotNull DrawContext ctx, float delta, int x, int y, int width, int height) {
        this.delta += delta;
        if (tags.contains(BlockoutGoalTag.advancement)) {
//            ctx.drawTexture(Identifier.of("blockout:goalicon/advancement/challenge_done.png"), x+3, y+3, 0, 0, width-6, height-6, width-6, height-6); // todo maybe change ?
        }
        renderTexture(ctx, delta, x, y, width, height);
        drawModifiers(ctx, x, y, x+width, y+height);
    }

    private void drawModifiers(DrawContext ctx, int x1, int y1, int x2, int y2) {
        final float textScale = 3;
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.textRenderer;
        MatrixStack matrices = ctx.getMatrices();
        matrices.push();
        matrices.translate(0, 0, 200);
        for (BlockoutGoalTag tag : this.tags) {
            switch (tag) {
                case die:
                    ctx.drawTexture(Identifier.of("blockout", "goalicon/modifiers/skull.png"), x1+1, y1+1, 0, 0, (x2-x1)/4, (y2-y1)/4,(x2-x1)/4, (y2-y1)/4);
                    break;
                case kill:
                    ctx.drawTexture(Identifier.of("blockout", "goalicon/modifiers/hotbar_attack_indicator_progress.png"), x1+1, y1+1, 0, 0, (x2-x1)/4, (y2-y1)/4, (x2-x1)/4, (y2-y1)/4);
                    break;
                case breed:
                    ctx.drawTexture(Identifier.of("blockout", "goalicon/modifiers/heart.png"), x1+1, y1+1, 0, 0, (x2-x1)/4, (y2-y1)/4, (x2-x1)/4, (y2-y1)/4);
                    break;
                case dont:
                    ctx.drawTexture(Identifier.of("blockout", "goalicon/modifiers/dont.png"), 3*(x2-x1)/4 + x1-1, y1+1, 0, 0, (x2-x1)/4, (y2-y1)/4, (x2-x1)/4, (y2-y1)/4);
                    break;
                case tame:
                    ctx.drawTexture(Identifier.of("blockout", "goalicon/modifiers/tame.png"), x1+1, y1+1, 0, 0, (x2-x1)/4, (y2-y1)/4, (x2-x1)/4, (y2-y1)/4);
                    break;
                case ride:
                    ctx.drawTexture(Identifier.of("minecraft", "textures/item/saddle.png"), 3*(x2-x1)/4 +x1-1, y1+1, 0, 0, (x2-x1)/4, (y2-y1)/4, (x2-x1)/4, (y2-y1)/4);
                    break;
                case broken:
                    ctx.drawTexture(Identifier.of("blockout", "goalicon/modifiers/item_broken.png"), x1+1, y1+1, 0, 0, (x2-x1)/4, (y2-y1)/4, (x2-x1)/4, (y2-y1)/4);
                    break;
                case more:
                    ctx.drawTexture(Identifier.of("blockout", "goalicon/modifiers/up-arrow.png"), x2-2-(x2-x1)/5, y2 -(x2-x1)/4 -1, 0, 0, (x2-x1)/4, (y2-y1)/4, (x2-x1)/4, (y2-y1)/4);
                    break;
                case unique:
                    ctx.drawTexture(Identifier.of("blockout", "goalicon/modifiers/unique.png"), 3*(x2-x1)/4 +x1-1, y1+1, 0, 0, (x2-x1)/4, (y2-y1)/4, (x2-x1)/4, (y2-y1)/4);
                    break;
                case use:
                    ctx.drawTexture(Identifier.of("blockout", "goalicon/modifiers/item_used.png"), x1+1, y1+1, 0, 0, (x2-x1)/4, (y2-y1)/4, (x2-x1)/4, (y2-y1)/4);
                    break;
                case obtain:
                    ctx.drawTexture(Identifier.of("blockout", "goalicon/modifiers/item_picked_up.png"), x1+1, y1+1, 0, 0, (x2-x1)/4, (y2-y1)/4, (x2-x1)/4, (y2-y1)/4);
                    break;
            }
            if (tag == BlockoutGoalTag.C6) {
                matrices.push();
                matrices.translate(x2-2-(x2-x1)/5, y2 -(x2-x1)/3 -1,0);
                matrices.scale(textScale, textScale, 1);
                ctx.drawText(textRenderer,"6", 0,0, 0xffffffff, true);
                matrices.pop();
            }
            else if (tag == BlockoutGoalTag.C8) {
                matrices.push();
                matrices.translate(x2-2-(x2-x1)/5, y2 -(x2-x1)/3 -1,0);
                matrices.scale(textScale, textScale, 1);
                ctx.drawText(textRenderer,"8", 0,0, 0xffffffff, true);
                matrices.pop();
            }
            else if (tag == BlockoutGoalTag.C7) {
                matrices.push();
                matrices.translate(x2-2-(x2-x1)/5, y2 -(x2-x1)/3 -1,0);
                matrices.scale(textScale, textScale, 1);
                ctx.drawText(textRenderer,"7", 0,0, 0xffffffff, true);
                matrices.pop();
            }
            else if (tag == BlockoutGoalTag.C10) {
                matrices.push();
                matrices.translate(x2-1-(x2-x1)/3, y2 -(x2-x1)/3 -1,0);
                matrices.scale(textScale, textScale, 1);
                ctx.drawText(textRenderer,"10", 0, 0, 0xffffffff, true);
                matrices.pop();
            }
            else if (tag == BlockoutGoalTag.C15) {
                matrices.push();
                matrices.translate(x2-1-(x2-x1)/3, y2 -(x2-x1)/3 -1,0);
                matrices.scale(textScale, textScale, 1);
                ctx.drawText(textRenderer,"15", 0, 0, 0xffffffff, true);
                matrices.pop();
            }
            else if (tag == BlockoutGoalTag.C20) {
                matrices.push();
                matrices.translate(x2-1-(x2-x1)/3, y2 -(x2-x1)/3 -1,0);
                matrices.scale(textScale, textScale, 1);
                ctx.drawText(textRenderer,"20", 0, 0, 0xffffffff, true);
                matrices.pop();
            }
            else if (tag == BlockoutGoalTag.C25) {
                matrices.push();
                matrices.translate(x2-1-(x2-x1)/3, y2 -(x2-x1)/3 -1,0);
                matrices.scale(textScale, textScale, 1);
                ctx.drawText(textRenderer,"25", 0, 0, 0xffffffff, true);
                matrices.pop();
            }
            else if (tag == BlockoutGoalTag.C30) {
                matrices.push();
                matrices.translate(x2-1-(x2-x1)/3, y2 -(x2-x1)/3 -1,0);
                matrices.scale(textScale, textScale, 1);
                ctx.drawText(textRenderer,"30", 0, 0, 0xffffffff, true);
                matrices.pop();
            }
            else if (tag == BlockoutGoalTag.C35) {
                matrices.push();
                matrices.translate(x2-1-(x2-x1)/3, y2 -(x2-x1)/3 -1,0);
                matrices.scale(textScale, textScale, 1);
                ctx.drawText(textRenderer,"35", 0, 0, 0xffffffff, true);
                matrices.pop();
            }
            else if (tag == BlockoutGoalTag.C64) {
                matrices.push();
                matrices.translate(x2-1-(x2-x1)/3, y2 -(x2-x1)/3 -1,0);
                matrices.scale(textScale, textScale, 1);
                ctx.drawText(textRenderer,"64", 0, 0, 0xffffffff, true);
                matrices.pop();
            }
            else if (tag == BlockoutGoalTag.C5) {
                matrices.push();
                matrices.translate(x2-2-(x2-x1)/5, y2 -(x2-x1)/3 -1,0);
                matrices.scale(textScale, textScale, 1);
                ctx.drawText(textRenderer,"5", 0, 0, 0xffffffff, true);
                matrices.pop();
            }
            else if (tag == BlockoutGoalTag.C4) {
                matrices.push();
                matrices.translate(x2-2-(x2-x1)/5, y2 -(x2-x1)/3 -1,0);
                matrices.scale(textScale, textScale, 1);
                ctx.drawText(textRenderer,"4", 0, 0, 0xffffffff, true);
                matrices.pop();
            }
            else if (tag == BlockoutGoalTag.C3) {
                matrices.push();
                matrices.translate(x2-2-(x2-x1)/5, y2 -(x2-x1)/3 -1,0);
                matrices.scale(textScale, textScale, 1);
                ctx.drawText(textRenderer,"3", 0, 0, 0xffffffff, true);
                matrices.pop();
            }
            else if (tag == BlockoutGoalTag.C2) {
                matrices.push();
                matrices.translate(x2-2-(x2-x1)/5, y2 -(x2-x1)/3 -1,0);
                matrices.scale(textScale, textScale, 1);
                ctx.drawText(textRenderer,"2", 0, 0, 0xffffffff, true);
                matrices.pop();
            }
            else if(tag == BlockoutGoalTag.C100) {
                matrices.push();
                matrices.translate(x2-1-(x2-x1)/3, y2 -(x2-x1)/3 -1,0);
                matrices.scale(textScale, textScale, 1);
                ctx.drawText(textRenderer, "100",0, 0, 0xffffffff, true);
                matrices.pop();
            }
            else if (tag == BlockoutGoalTag.C200) {
                matrices.push();
                matrices.translate(x2-1-(x2-x1)/3, y2 -(x2-x1)/3 -1,0);
                matrices.scale(textScale, textScale, 1);
                ctx.drawText(textRenderer,"200", 0, 0, 0xffffffff, true);
                matrices.pop();
            }
            else if (tag == BlockoutGoalTag.C500) {
                matrices.push();
                matrices.translate(x2-1-(x2-x1)/3, y2 -(x2-x1)/3 -1,0);
                matrices.scale(textScale, textScale, 1);
                ctx.drawText(textRenderer,"500", 0,0, 0xffffffff, true);
                matrices.pop();
            }
            else if (tag == BlockoutGoalTag.C300) {
                matrices.push();
                matrices.translate(x2-1-(x2-x1)/3, y2 -(x2-x1)/3 -1,0);
                matrices.scale(textScale, textScale, 1);
                ctx.drawText(textRenderer,"300", 0, 0, 0xffffffff, true);
                matrices.pop();
            }
            else if (tag == BlockoutGoalTag.C400) {
                matrices.push();
                matrices.translate(x2-1-(x2-x1)/3, y2 -(x2-x1)/3 -1,0);
                matrices.scale(textScale, textScale, 1);
                ctx.drawText(textRenderer,"400", 0, 0, 0xffffffff, true);
                matrices.pop();
            }
            else if (tag == BlockoutGoalTag.km1) {
                matrices.push();
                matrices.translate(x2-1-(x2-x1)/3, y2 -(x2-x1)/3 -1,0);
                matrices.scale(textScale, textScale, 1);
                ctx.drawText(textRenderer,"1km", 0, 0, 0xffffffff, true);
                matrices.pop();
            }
        }
        ctx.getMatrices().pop();
    }

    public void encode(ByteBuf buf) {
        buf.writeByte(this.id.length());
        buf.writeCharSequence(this.id, java.nio.charset.StandardCharsets.UTF_8);
    }



}
