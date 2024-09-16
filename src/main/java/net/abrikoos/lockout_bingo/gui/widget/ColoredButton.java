package net.abrikoos.lockout_bingo.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

public class ColoredButton extends ButtonWidget {
    private static final ButtonTextures TEXTURES = new ButtonTextures(
            Identifier.ofVanilla("widget/button"), Identifier.ofVanilla("widget/button_disabled"), Identifier.ofVanilla("widget/button_highlighted")
    );

    private float red;
    private float green;
    private float blue;
    private int alph;


    public ColoredButton(int x, int y, int width, int height, Text message, ButtonWidget.PressAction onPress, int color) {
        super(x, y, width, height, message, (ButtonWidget.PressAction) onPress, ColoredButton.DEFAULT_NARRATION_SUPPLIER);
        this.red = (float) (color >> 16 & 255) / 255.0F;
        this.green = (float) (color >> 8 & 255) / 255.0F;
        this.blue = (float) (color & 255) / 255.0F;
        this.alph = color >> 24 & 255;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        context.setShaderColor(this.red, this.green, this.blue, this.alpha* this.alph);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        context.drawGuiTexture(TEXTURES.get(this.active, this.isSelected()), this.getX(), this.getY(), this.getWidth(), this.getHeight());
        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = this.active ? 16777215 : 10526880;
        this.drawMessage(context, minecraftClient.textRenderer, i | MathHelper.ceil(this.alpha * 255.0F) << 24);
    }

    public void setColor(int color) {
        this.red = (float) (color >> 16 & 255) / 255.0F;
        this.green = (float) (color >> 8 & 255) / 255.0F;
        this.blue = (float) (color & 255) / 255.0F;
        this.alph = color >> 24 & 255;
    }

    public static ColoredButton.Builder builderc(Text message, ColoredButton.PressAction onPress) {
        return new ColoredButton.Builder(message, onPress);
    }

    @Environment(EnvType.CLIENT)
    public static class Builder {
        private final Text message;
        private final ColoredButton.PressAction onPress;
        @Nullable
        private Tooltip tooltip;
        private int x;
        private int y;
        private int width = 150;
        private int height = 20;
        private int color = 0xFFFFFFFF;
        private ColoredButton.NarrationSupplier narrationSupplier = ColoredButton.DEFAULT_NARRATION_SUPPLIER;

        public Builder(Text message, ColoredButton.PressAction onPress) {
            this.message = message;
            this.onPress = onPress;
        }

        public ColoredButton.Builder position(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public ColoredButton.Builder width(int width) {
            this.width = width;
            return this;
        }

        public ColoredButton.Builder size(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public ColoredButton.Builder dimensions(int x, int y, int width, int height) {
            return this.position(x, y).size(width, height);
        }

        public ColoredButton.Builder tooltip(@Nullable Tooltip tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        public ColoredButton.Builder narrationSupplier(ColoredButton.NarrationSupplier narrationSupplier) {
            this.narrationSupplier = narrationSupplier;
            return this;
        }

        public ColoredButton build() {
            ColoredButton buttonWidget = new ColoredButton(this.x, this.y, this.width, this.height, this.message, this.onPress, this.color);
            buttonWidget.setTooltip(this.tooltip);
            return buttonWidget;
        }

        public ColoredButton.Builder color(int color) {
            this.color = color;
            return this;
        }
    }

//    @Environment(EnvType.CLIENT)
//    public interface PressAction {
//        void onPress(ColoredButton button);
//    }

}
