package net.abrikoos.lockout_bingo.goals;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.texture.*;
import net.minecraft.util.Identifier;

import java.util.List;

public class GoalListItem {
    public String name;
    public String description;
    public int difficulty;
    public List<Identifier> resourceids;
    public List<LockoutGoalTag> tags;
    public String id;
    private float delta = 0;
    public Sprite sprite;

    public GoalListItem(String name, String description, int difficulty, List<LockoutGoalTag> tags, String id, List<Identifier> resourceids) {
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.resourceids = resourceids;
        this.tags = tags;
        this.id = id;
    }

    boolean hasTag(LockoutGoalTag tag) {
        return tags.contains(tag);
    }

    boolean redstone() {
        return hasTag(LockoutGoalTag.redstone);
    }

    boolean end() {
        return hasTag(LockoutGoalTag.end);
    }

    boolean nether() {
        return hasTag(LockoutGoalTag.nether);
    }

    boolean die() {
        return hasTag(LockoutGoalTag.die);
    }

    boolean dont() {
        return hasTag(LockoutGoalTag.dont);
    }

    boolean advancement() {
        return hasTag(LockoutGoalTag.advancement);
    }

    boolean eat() {
        return hasTag(LockoutGoalTag.eat);
    }

    boolean breed() {
        return hasTag(LockoutGoalTag.breed);
    }

    public void draw(DrawContext ctx, float delta, int x, int y, int width, int height) {
        this.delta += delta;
        ctx.drawTexture(this.resourceids.get(0), x+3, y+3, 0, 0, width-6, height-6, width-6, height-6);
        drawModifiers(ctx, x, y, x+width, y+height);
    }

    private void drawModifiers(DrawContext ctx, int x1, int y1, int x2, int y2) {
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.textRenderer;
        for (LockoutGoalTag tag : this.tags) {
            switch (tag) {
                case die:
                    ctx.drawTexture(Identifier.of("lockout-bingo", "goalicon/modifiers/skull.png"), 2*(x2-x1)/3 +x1 -1, y1+1, 0, 0, (x2-x1)/3, (y2-y1)/3,(x2-x1)/3, (y2-y1)/3);
                    break;
                case kill:
                    ctx.drawTexture(Identifier.of("lockout-bingo", "goalicon/modifiers/kill.png"), x1+1, y1+1, 0, 0, (x2-x1)/3, (y2-y1)/3, (x2-x1)/3, (y2-y1)/3);
                    break;
                case breed:
                    ctx.drawTexture(Identifier.of("lockout-bingo", "goalicon/modifiers/heart.png"), 2*(x2-x1)/3  + x1-1, y1+1, 0, 0, (x2-x1)/3, (y2-y1)/3, (x2-x1)/3, (y2-y1)/3);
                    break;
                case dont:
                    ctx.drawTexture(Identifier.of("lockout-bingo", "goalicon/modifiers/dont.png"), 2*(x2-x1)/3 + x1-1, y1+1, 0, 0, (x2-x1)/3, (y2-y1)/3, (x2-x1)/3, (y2-y1)/3);
                    break;
                case tame:
                    ctx.drawTexture(Identifier.of("lockout-bingo", "goalicon/modifiers/tame.png"), 2*(x2-x1)/3 +x1-1, y1+1, 0, 0, (x2-x1)/3, (y2-y1)/3, (x2-x1)/3, (y2-y1)/3);
                    break;
                case ride:
                    ctx.drawTexture(Identifier.of("minecraft", "textures/item/saddle.png"), 2*(x2-x1)/3 +x1-1, y1+1, 0, 0, (x2-x1)/3, (y2-y1)/3, (x2-x1)/3, (y2-y1)/3);
            }
            if (tag == LockoutGoalTag.C6) {
                ctx.drawText(textRenderer,"6", x2-2-(x2-x1)/5, y2 -(x2-x1)/3 -1, 0xffffffff, true);
            }
            else if (tag == LockoutGoalTag.C8) {
                ctx.drawText(textRenderer,"8", x2-2-(x2-x1)/5, y2 -(x2-x1)/3 -1, 0xffffffff, true);
            }
            else if (tag == LockoutGoalTag.C7) {
                ctx.drawText(textRenderer,"7", x2-2-(x2-x1)/5, y2 -(x2-x1)/3 -1, 0xffffffff, true);
            }
            else if (tag == LockoutGoalTag.C10) {
                ctx.drawText(textRenderer,"10", x2-1-(x2-x1)/3, y2 -(x2-x1)/3 -1, 0xffffffff, true);
            }
            else if (tag == LockoutGoalTag.C15) {
                ctx.drawText(textRenderer,"15", x2-1-(x2-x1)/3, y2 -(x2-x1)/3 -1, 0xffffffff, true);
            }
            else if (tag == LockoutGoalTag.C20) {
                ctx.drawText(textRenderer,"20", x2-1-(x2-x1)/3, y2 -(x2-x1)/3 -1, 0xffffffff, true);
            }
            else if (tag == LockoutGoalTag.C25) {
                ctx.drawText(textRenderer,"25", x2-1-(x2-x1)/3, y2 -(x2-x1)/3 -1, 0xffffffff, true);
            }
            else if (tag == LockoutGoalTag.C30) {
                ctx.drawText(textRenderer,"30", x2-1-(x2-x1)/3, y2 -(x2-x1)/3 -1, 0xffffffff, true);
            }
            else if (tag == LockoutGoalTag.C64) {
                ctx.drawText(textRenderer,"64", x2-1-(x2-x1)/3, y2 -(x2-x1)/3 -1, 0xffffffff, true);
            }
            else if (tag == LockoutGoalTag.C5) {
                ctx.drawText(textRenderer,"5", x2-2-(x2-x1)/5, y2 -(x2-x1)/3 -1, 0xffffffff, true);
            }
            else if (tag == LockoutGoalTag.C4) {
                ctx.drawText(textRenderer,"4", x2-2-(x2-x1)/5, y2 -(x2-x1)/3 -1, 0xffffffff, true);
            }
            else if (tag == LockoutGoalTag.C3) {
                ctx.drawText(textRenderer,"3", x2-2-(x2-x1)/5, y2 -(x2-x1)/3 -1, 0xffffffff, true);
            }
            else if (tag == LockoutGoalTag.C2) {
                ctx.drawText(textRenderer,"2", x2-2-(x2-x1)/5, y2 -(x2-x1)/3 -1, 0xffffffff, true);
            }
            else if (tag == LockoutGoalTag.C200) {
                ctx.drawText(textRenderer,"200", x2-1-(x2-x1)/3, y2 -(x2-x1)/3 -1, 0xffffffff, true);
            }
            else if (tag == LockoutGoalTag.C500) {
                ctx.drawText(textRenderer,"500", x2-1-(x2-x1)/3, y2 -(x2-x1)/3 -1, 0xffffffff, true);
            }
            else if (tag == LockoutGoalTag.C300) {
                ctx.drawText(textRenderer,"300", x2-1-(x2-x1)/3, y2 -(x2-x1)/3 -1, 0xffffffff, true);
            }
        }
    }

    public void encode(ByteBuf buf) {
        buf.writeByte(this.id.length());
        buf.writeCharSequence(this.id, java.nio.charset.StandardCharsets.UTF_8);
    }



}
