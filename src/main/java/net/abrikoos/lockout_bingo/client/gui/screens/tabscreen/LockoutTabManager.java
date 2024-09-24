package net.abrikoos.lockout_bingo.client.gui.screens.tabscreen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.tab.Tab;
import net.minecraft.client.gui.tab.TabManager;
import net.minecraft.client.gui.widget.ClickableWidget;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class LockoutTabManager extends TabManager {

    public List<Consumer<Tab>> listeners = new ArrayList<>();

    public LockoutTabManager(Consumer<ClickableWidget> tabLoadConsumer, Consumer<ClickableWidget> tabUnloadConsumer) {
        super(tabLoadConsumer, tabUnloadConsumer);
    }

    public void setCurrentTab(Tab tab, boolean clickSound) {
        super.setCurrentTab(tab, clickSound);
        listeners.forEach(consumer -> consumer.accept(tab));
    }

    public void subscribeTabChangeEvent(Consumer<Tab> consumer) {
        listeners.add(consumer);
    }


}
