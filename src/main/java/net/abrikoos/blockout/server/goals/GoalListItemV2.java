package net.abrikoos.blockout.server.goals;

import net.abrikoos.blockout.BlockoutLogger;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.DefaultedRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GoalListItemV2 {
    public String name;
    public String description;
    public int difficulty;
    public List<BlockoutGoalTag> tags;
    public String id;
    protected float delta = 0;
    public List<Identifier> resourceids;
    public List<ItemStack> itemStacks;
    private DrawableModifier background;
    private DrawableModifier topLeft;
    private DrawableModifier topRight;
    private DrawableModifier bottomLeft;
    private DrawableModifier bottomRight;

    int visibleIconCount = 0;

    public static final float TEXT_SCALE = 3f;



    private GoalListItemV2(String name, String description, int difficulty, List<BlockoutGoalTag> tags, String id) {
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.tags = tags;
        this.id = id;
        this.resourceids = new ArrayList<>();
        this.itemStacks = new ArrayList<>();
        this.tagsToDrawables();
    }

    public void draw(@NotNull DrawContext ctx, float delta, int x, int y, int width, int height) {
        this.delta += delta;
        if (this.delta > 1000){
            this.delta = 0;
            this.visibleIconCount += 1;
            if (this.visibleIconCount > this.resourceids.size() + this.itemStacks.size() -1){
                this.visibleIconCount = 0;
            }
        }

        if (this.background != null) {
            this.background.render(ctx, delta, x, y, width, height);
        }

        if (this.resourceids.size() > this.visibleIconCount){
            ctx.drawTexture(this.resourceids.get(this.visibleIconCount), x, y, 0, 0, width, height, width, height);
        }
        else {
            int index = this.visibleIconCount - this.resourceids.size();
            MatrixStack matrices = ctx.getMatrices();
            matrices.push();
            try {
                matrices.translate(x+3, y+3, 0);
                matrices.scale((float) (width -6)/16, (float) (height -6)/16, 1);
                ctx.drawItemWithoutEntity(this.itemStacks.get(index), 0, 0);
            }
            catch (Exception e){
                BlockoutLogger.log("Could not render ItemStack for item " + this.itemStacks.get(index).getItem().getName().getString() + ": " + e.getMessage());
            }
            matrices.pop();
        }
        if (this.topLeft != null) {
            this.topLeft.render(ctx, delta, x+1, y+1, width / 4, height / 4);
        }
        if (this.topRight != null) {
            this.topRight.render(ctx, delta, x + (width * 3 / 4) -1, y + 1, width / 4, height / 4);
        }
        if (this.bottomLeft != null) {
            this.bottomLeft.render(ctx, delta, x +1, y + (height * 3 / 4) -1, width / 4, height / 4);
        }
        if (this.bottomRight != null) {
            this.bottomRight.render(ctx, delta, x + (width * 3 / 4) -1, y + (height * 3 / 4) -1, width / 4, height / 4);
        }

    }



    public static GoalListItemV2 createStacked(String name, String description, int difficulty, List<BlockoutGoalTag> tags, String id, List<ItemStack> resourceids) {
        return (new GoalListItemV2(name, description, difficulty, tags, id)).addStacks(resourceids);
    }

    public static GoalListItemV2 createStackedString(String name, String description, int difficulty, List<BlockoutGoalTag> tags, String id, List<String> itemStrings) {
        List<Item> items = new ArrayList<>();
        for (String itemString : itemStrings) {
            DefaultedRegistry<Item> itemReg = Registries.ITEM;
            Optional<Item> item = itemReg.getOrEmpty(Identifier.ofVanilla(itemString));
            // todo maybe add code for custom items here later
            if (item.isPresent()) {
                items.add(item.orElseThrow());
            }
        }
        if (items.isEmpty()) {
            items.add(Items.BARRIER);
            BlockoutLogger.log("GoalListItemV2: No valid items found for goal " + name + ", adding barrier as placeholder");
        }
        return new GoalListItemV2(name, description, difficulty, tags, id).addStacks(items.stream().map(Item::getDefaultStack).toList());
    }

    public static GoalListItemV2 createImaged(String name, String description, int difficulty, List<BlockoutGoalTag> tags, String id, List<Identifier> resourceids) {
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
        this.topRight = new TextModifier(text);
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

    boolean hasTag(BlockoutGoalTag tag) {
        return tags.contains(tag);
    }


    private void tagsToDrawables() {
        for (BlockoutGoalTag tag: this.tags) {
            switch(tag){
                case die:
                    this.addTopLeft(Identifier.of("blockout", "goalicon/modifiers/skull.png"));
                    break;
                case kill:
                    this.addTopLeft(Identifier.of("blockout", "goalicon/modifiers/hotbar_attack_indicator_progress.png"));
                    break;
                case breed:
                    this.addTopLeft(Identifier.of("blockout", "goalicon/modifiers/heart.png"));
                    break;
                case dont:
                    this.addTopRight(Identifier.of("blockout", "goalicon/modifiers/dont.png"));
                    break;
                case tame:
                    this.addTopLeft(Identifier.of("blockout", "goalicon/modifiers/tame.png"));
                    break;
                case ride:
                    this.addTopRight(Items.SADDLE.getDefaultStack());
                    break;
                case broken:
                    this.addTopLeft(Identifier.of("blockout", "goalicon/modifiers/item_broken.png"));
                    break;
                case more:
                    this.addBottomLeft(Identifier.of("blockout", "goalicon/modifiers/up-arrow.png"));
                    break;
                case unique:
                    this.addTopRight(Identifier.of("blockout", "goalicon/modifiers/unique.png"));
                    break;
                case use:
                    this.addTopLeft(Identifier.of("blockout", "goalicon/modifiers/item_used.png"));
                    break;
                case obtain:
                    this.addTopLeft(Identifier.of("blockout", "goalicon/modifiers/item_picked_up.png"));
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
            try {
                matrices.translate(x+3, y+3, 0);
                matrices.scale((float) (width -6)/16, (float) (height -6)/16, 1);
                ctx.drawItemWithoutEntity(stack, 0, 0);
            }
            catch (Exception e){
                BlockoutLogger.log("Could not render ItemStackModifier for item " + stack.getItem().getName().getString() + ": " + e.getMessage());
            }
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
            ctx.drawText(MinecraftClient.getInstance().textRenderer, text, 0, 0, 0xFFFFFF, false);
            matrices.pop();
        }

    }



}
