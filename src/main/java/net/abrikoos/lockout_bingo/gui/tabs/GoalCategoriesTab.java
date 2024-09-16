package net.abrikoos.lockout_bingo.gui.tabs;

import net.minecraft.client.gui.tab.GridScreenTab;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.text.Text;

public class GoalCategoriesTab extends GridScreenTab {

    public GoalCategoriesTab(Text title) {
        super(title);
        GridWidget.Adder adder = this.grid.createAdder(2);
        CyclingButtonWidget<Boolean> cbd1 = adder.add(CyclingButtonWidget.onOffBuilder().build(Text.of("End goals"), (button, val) -> {}));
        CyclingButtonWidget<Boolean> cbd2 = adder.add(CyclingButtonWidget.onOffBuilder().build(Text.of("Nether goals"), (button, val) -> {}));
        CyclingButtonWidget<Boolean> cbd3 = adder.add(CyclingButtonWidget.onOffBuilder().build(Text.of("Redstone goals"), (button, val) -> {}));
        CyclingButtonWidget<Boolean> cbd4 = adder.add(CyclingButtonWidget.onOffBuilder().build(Text.of("Death goals"), (button, val) -> {}));
        CyclingButtonWidget<Boolean> cbd5 = adder.add(CyclingButtonWidget.onOffBuilder().build(Text.of("Opponent goals"), (button, val) -> {}));
        CyclingButtonWidget<Boolean> cbd6 = adder.add(CyclingButtonWidget.onOffBuilder().build(Text.of("Biome goals"), (button, val) -> {}));
        CyclingButtonWidget<Boolean> cbd7 = adder.add(CyclingButtonWidget.onOffBuilder().build(Text.of("Food goals"), (button, val) -> {}));
        CyclingButtonWidget<Boolean> cbd8 = adder.add(CyclingButtonWidget.onOffBuilder().build(Text.of("Kill goals"), (button, val) -> {}));
        CyclingButtonWidget<Boolean> cbd9 = adder.add(CyclingButtonWidget.onOffBuilder().build(Text.of("Move goals"), (button, val) -> {}));
    }
}
