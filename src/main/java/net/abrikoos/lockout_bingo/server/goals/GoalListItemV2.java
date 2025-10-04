package net.abrikoos.lockout_bingo.server.goals;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static net.abrikoos.lockout_bingo.server.goals.LockoutGoalTag.*;

public class GoalListItemV2 {
    public String name;
    public String description;
    public int difficulty;
    public List<LockoutGoalTag> tags;
    public String id;
    protected float delta = 0;
    public List<Identifier> resourceids;
    public List<ItemStack> itemStacks;
    private DrawableModifier background;
    private DrawableModifier topLeft;
    private DrawableModifier topRight;
    private DrawableModifier bottomLeft;
    private DrawableModifier bottomRight;

    public static final float TEXT_SCALE = 3f;
    public static final TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;



    private GoalListItemV2(String name, String description, int difficulty, List<LockoutGoalTag> tags, String id) {
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.tags = tags;
        this.id = id;
        this.tagsToDrawables();
    }

    public void draw(@NotNull DrawContext ctx, float delta, int x, int y, int width, int height) {
        this.delta += delta;
    }



    public static GoalListItemV2 createImaged(String name, String description, int difficulty, List<LockoutGoalTag> tags, String id, List<ItemStack> resourceids) {
        return (new GoalListItemV2(name, description, difficulty, tags, id)).addStacks(resourceids);

    }

    public static GoalListItemV2 createStacked(String name, String description, int difficulty, List<LockoutGoalTag> tags, String id, List<Identifier> resourceids) {
        return (new GoalListItemV2(name, description, difficulty, tags, id)).addImages(resourceids);
    }

    public GoalListItemV2 addStacks(List<ItemStack> resourceids) {
        this.itemStacks = resourceids;
        return this;
    }

    public GoalListItemV2 addImages(List<Identifier> resourceids) {
        this.resourceids = resourceids;
        return this;
    }

    public GoalListItemV2 addBackground(Identifier resourceid) {
        this.background = new IndentifierModifier(resourceid);
        return this;
    }

    public GoalListItemV2 addBackground(ItemStack stack) {
        this.background = new ItemStackModifier(stack);
        return this;
    }

    public GoalListItemV2 addBackground(String text) {
        this.background = new TextModifier(text);
        return this;
    }

    public GoalListItemV2 addTopLeft(Identifier resourceid) {
        this.topLeft = new IndentifierModifier(resourceid);
        return this;
    }

    public GoalListItemV2 addTopLeft(ItemStack stack) {
        this.topLeft = new ItemStackModifier(stack);
        return this;
    }

    public GoalListItemV2 addTopLeft(String text) {
        this.topLeft = new TextModifier(text);
        return this;
    }

    public GoalListItemV2 addTopRight(Identifier resourceid) {
        this.topRight = new IndentifierModifier(resourceid);
        return this;
    }

    public GoalListItemV2 addTopRight(ItemStack stack) {
        this.topRight = new ItemStackModifier(stack);
        return this;
    }

    public GoalListItemV2 addTopRight(String text) {
        this.bottomLeft = new TextModifier(text);
        return this;
    }

    public GoalListItemV2 addBottomLeft(Identifier resourceid) {
        this.bottomLeft = new IndentifierModifier(resourceid);
        return this;
    }

    public GoalListItemV2 addBottomLeft(ItemStack stack) {
        this.bottomLeft = new ItemStackModifier(stack);
        return this;
    }

    public GoalListItemV2 addBottomLeft(String text) {
        this.bottomLeft = new TextModifier(text);
        return this;
    }

    public GoalListItemV2 addBottomRight(Identifier resourceid) {
        this.bottomRight = new IndentifierModifier(resourceid);
        return this;
    }

    public GoalListItemV2 addBottomRight(ItemStack stack) {
        this.bottomRight = new ItemStackModifier(stack);
        return this;
    }

    public GoalListItemV2 addBottomRight(String text) {
        this.bottomRight = new TextModifier(text);
        return this;
    }

    boolean hasTag(LockoutGoalTag tag) {
        return tags.contains(tag);
    }


    private void tagsToDrawables() {
        for (LockoutGoalTag tag: this.tags) {
            switch(tag){
                case die:
                    this.addTopLeft(Identifier.of("lockout-bingo", "goalicon/modifiers/skull.png"));
                    break;
                case kill:
                    this.addTopLeft(Identifier.of("lockout-bingo", "goalicon/modifiers/hotbar_attack_indicator_progress.png"));
                    break;
                case breed:
                    this.addTopLeft(Identifier.of("lockout-bingo", "goalicon/modifiers/heart.png"));
                    break;
                case dont:
                    this.addTopRight(Identifier.of("lockout-bingo", "goalicon/modifiers/dont.png"));
                    break;
                case tame:
                    this.addTopLeft(Identifier.of("lockout-bingo", "goalicon/modifiers/tame.png"));
                    break;
                case ride:
                    this.addTopRight(Identifier.of("lockout-bingo", "goalicon/modifiers/saddle.png"));
                    break;
                case broken:
                    this.addTopLeft(Identifier.of("lockout-bingo", "goalicon/modifiers/item_broken.png"));
                    break;
                case more:
                    this.addBottomLeft(Identifier.of("lockout-bingo", "goalicon/modifiers/more.png"));
                    break;
                case unique:
                    this.addTopRight(Identifier.of("lockout-bingo", "goalicon/modifiers/unique.png"));
                    break;
                case use:
                    this.addTopLeft(Identifier.of("lockout-bingo", "goalicon/modifiers/item_used.png"));
                    break;
                case obtain:
                    this.addTopLeft(Identifier.of("lockout-bingo", "goalicon/modifiers/item_obtained.png"));
                    break;
            }
        }
    }

    abstract class DrawableModifier {

        abstract void render(@NotNull DrawContext ctx, float delta, int x, int y, int width, int height);
    }

    class IndentifierModifier extends DrawableModifier {
        Identifier id;

        public IndentifierModifier(Identifier id) {
            this.id = id;
        }

        @Override
        void render(@NotNull DrawContext ctx, float delta, int x, int y, int width, int height) {
            ctx.drawTexture(id, x, y, 0, 0, width, height, width, height);
        }

    }

    class ItemStackModifier extends DrawableModifier {
        ItemStack stack;

        public ItemStackModifier(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        void render(@NotNull DrawContext ctx, float delta, int x, int y, int width, int height) {
            MatrixStack matrices = ctx.getMatrices();
            matrices.push();
            matrices.translate(x+3, y+3, 0);
            matrices.scale((float) (width -6)/16, (float) (height -6)/16, 1);
            ctx.drawItemWithoutEntity(stack, 0, 0);
            matrices.pop();
        }

    }

    class TextModifier extends DrawableModifier {

        String text;

        public TextModifier(String text) {
            this.text = text;
        }

        @Override
        void render(@NotNull DrawContext ctx, float delta, int x, int y, int width, int height) {
            MatrixStack matrices = ctx.getMatrices();
            matrices.push();
            matrices.translate(x, y, 0);
            matrices.scale(TEXT_SCALE, TEXT_SCALE, 1);
            ctx.drawText(textRenderer, text, 0, 0, 0xFFFFFF, false);
            matrices.pop();
        }

    }



}
