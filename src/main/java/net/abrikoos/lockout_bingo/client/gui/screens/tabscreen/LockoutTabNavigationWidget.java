package net.abrikoos.lockout_bingo.client.gui.screens.tabscreen;



import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.navigation.GuiNavigation;
import net.minecraft.client.gui.navigation.GuiNavigationPath;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.tab.Tab;
import net.minecraft.client.gui.tab.TabManager;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.client.gui.widget.TabButtonWidget;
import net.minecraft.client.gui.widget.TabNavigationWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class LockoutTabNavigationWidget extends AbstractParentElement implements Drawable, Selectable {
    private static final int field_42489 = -1;
    private static final int field_43076 = 400;
    private static final int field_43077 = 24;
    private static final int field_43078 = 14;
    private static final Text USAGE_NARRATION_TEXT = Text.translatable("narration.tab_navigation.usage");
    private final DirectionalLayoutWidget grid = DirectionalLayoutWidget.horizontal();
    private int tabNavWidth;
    private final LockoutTabManager tabManager;
    private final List<Tab> tabs = new ArrayList<>();
    private final List<TabButtonWidget> tabButtons;

    LockoutTabNavigationWidget(int x, LockoutTabManager tabManager, Iterable<Tab> tabs) {
        this.tabNavWidth = x;
        this.tabManager = tabManager;
        tabs.forEach(this.tabs::add);
        this.grid.getMainPositioner().alignHorizontalCenter();
        List<TabButtonWidget> builder = new ArrayList<>();

        for (Tab tab : tabs) {
            builder.add(this.grid.add(new TabButtonWidget(tabManager, tab, 0, 24)));
        }

        this.tabButtons = builder;
    }

    public void addTab(Tab tab) {
        this.tabs.add(tab);
        this.tabButtons.add(this.grid.add(new TabButtonWidget(this.tabManager, tab, 0, 24)));
    }

    public void removeTab(Tab tab) {
        int index = this.tabs.indexOf(tab);
        if (index != -1) {
            this.tabs.remove(index);
            this.tabButtons.remove(index);
        }
    }

    public void setActiveTab(Tab tab, boolean clickSound) {
        tabManager.setCurrentTab(tab, clickSound);
    }

    public void setActiveTab(int index, boolean clickSound) {
        assert index >= 0 && index < tabs.size();
        tabManager.setCurrentTab(tabs.get(index), clickSound);
    }

    public Tab getActiveTab() {
        return tabManager.getCurrentTab();
    }

    public int getActiveTabIndex() {
        return tabs.indexOf(tabManager.getCurrentTab());
    }


    public static LockoutTabNavigationWidget.Builder builder(LockoutTabManager tabManager, int width) {
        return new LockoutTabNavigationWidget.Builder(tabManager, width);
    }

    public void setWidth(int width) {
        this.tabNavWidth = width;
    }

    @Override
    public void setFocused(boolean focused) {
        super.setFocused(focused);
        if (this.getFocused() != null) {
            this.getFocused().setFocused(focused);
        }
    }

    @Override
    public void setFocused(@Nullable Element focused) {
        super.setFocused(focused);
        if (focused instanceof TabButtonWidget tabButtonWidget) {
            this.tabManager.setCurrentTab(tabButtonWidget.getTab(), true);
        }
    }

    @Nullable
    @Override
    public GuiNavigationPath getNavigationPath(GuiNavigation navigation) {
        if (!this.isFocused()) {
            TabButtonWidget tabButtonWidget = this.getCurrentTabButton();
            if (tabButtonWidget != null) {
                return GuiNavigationPath.of(this, GuiNavigationPath.of(tabButtonWidget));
            }
        }

        return navigation instanceof GuiNavigation.Tab ? null : super.getNavigationPath(navigation);
    }

    @Override
    public List<? extends Element> children() {
        return this.tabButtons;
    }

    @Override
    public Selectable.SelectionType getType() {
        return (Selectable.SelectionType)this.tabButtons.stream().map(ClickableWidget::getType).max(Comparator.naturalOrder()).orElse(Selectable.SelectionType.NONE);
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {
        Optional<TabButtonWidget> optional = this.tabButtons
                .stream()
                .filter(ClickableWidget::isHovered)
                .findFirst()
                .or(() -> Optional.ofNullable(this.getCurrentTabButton()));
        optional.ifPresent(button -> {
            this.appendNarrations(builder.nextMessage(), button);
            button.appendNarrations(builder);
        });
        if (this.isFocused()) {
            builder.put(NarrationPart.USAGE, USAGE_NARRATION_TEXT);
        }
    }

    protected void appendNarrations(NarrationMessageBuilder builder, TabButtonWidget button) {
        if (this.tabs.size() > 1) {
            int i = this.tabButtons.indexOf(button);
            if (i != -1) {
                builder.put(NarrationPart.POSITION, Text.translatable("narrator.position.tab", i + 1, this.tabs.size()));
            }
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        RenderSystem.enableBlend();
        context.drawTexture(
                Screen.HEADER_SEPARATOR_TEXTURE, 0, this.grid.getY() + this.grid.getHeight() - 2, 0.0F, 0.0F, ((TabButtonWidget)this.tabButtons.get(0)).getX(), 2, 32, 2
        );
        int i = ((TabButtonWidget)this.tabButtons.get(this.tabButtons.size() - 1)).getRight();
        context.drawTexture(Screen.HEADER_SEPARATOR_TEXTURE, i, this.grid.getY() + this.grid.getHeight() - 2, 0.0F, 0.0F, this.tabNavWidth, 2, 32, 2);
        RenderSystem.disableBlend();

        for (TabButtonWidget tabButtonWidget : this.tabButtons) {
            tabButtonWidget.render(context, mouseX, mouseY, delta);
        }
    }

    @Override
    public ScreenRect getNavigationFocus() {
        return this.grid.getNavigationFocus();
    }

    public void init() {
        int i = Math.min(400, this.tabNavWidth) - 28;
        int j = MathHelper.roundUpToMultiple(i / this.tabs.size(), 2);

        for (TabButtonWidget tabButtonWidget : this.tabButtons) {
            tabButtonWidget.setWidth(j);
        }

        this.grid.refreshPositions();
        this.grid.setX(MathHelper.roundUpToMultiple((this.tabNavWidth - i) / 2, 2));
        this.grid.setY(0);
    }

    public void selectTab(int index, boolean clickSound) {
        if (this.isFocused()) {
            this.setFocused((Element)this.tabButtons.get(index));
        } else {
            this.tabManager.setCurrentTab((Tab)this.tabs.get(index), clickSound);
        }
    }

    public boolean trySwitchTabsWithKey(int keyCode) {
        if (Screen.hasControlDown()) {
            int i = this.getTabForKey(keyCode);
            if (i != -1) {
                this.selectTab(MathHelper.clamp(i, 0, this.tabs.size() - 1), true);
                return true;
            }
        }

        return false;
    }

    private int getTabForKey(int keyCode) {
        if (keyCode >= 49 && keyCode <= 57) {
            return keyCode - 49;
        } else {
            if (keyCode == 258) {
                int i = this.getCurrentTabIndex();
                if (i != -1) {
                    int j = Screen.hasShiftDown() ? i - 1 : i + 1;
                    return Math.floorMod(j, this.tabs.size());
                }
            }

            return -1;
        }
    }

    private int getCurrentTabIndex() {
        Tab tab = this.tabManager.getCurrentTab();
        int i = this.tabs.indexOf(tab);
        return i != -1 ? i : -1;
    }

    @Nullable
    private TabButtonWidget getCurrentTabButton() {
        int i = this.getCurrentTabIndex();
        return i != -1 ? (TabButtonWidget)this.tabButtons.get(i) : null;
    }

    @Environment(EnvType.CLIENT)
    public static class Builder {
        private final int width;
        private final LockoutTabManager tabManager;
        private final List<Tab> tabs = new ArrayList();

        Builder(LockoutTabManager tabManager, int width) {
            this.tabManager = tabManager;
            this.width = width;
        }

        public LockoutTabNavigationWidget.Builder tabs(Tab... tabs) {
            Collections.addAll(this.tabs, tabs);
            return this;
        }

        public LockoutTabNavigationWidget.Builder tabs(Iterable<Tab> tabs) {
            tabs.forEach(this.tabs::add);
            return this;
        }

        public LockoutTabNavigationWidget build() {
            return new LockoutTabNavigationWidget(this.width, this.tabManager, this.tabs);
        }
    }
}
