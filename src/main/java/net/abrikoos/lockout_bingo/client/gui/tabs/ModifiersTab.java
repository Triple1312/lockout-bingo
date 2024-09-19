package net.abrikoos.lockout_bingo.client.gui.tabs;

import net.minecraft.client.gui.tab.GridScreenTab;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.text.Text;

public class ModifiersTab extends GridScreenTab {
    public boolean poisonWater = true;
    public boolean fireHeal = true;
    public boolean speedy = true;
    public boolean randomEffectTimer = true;

    public ModifiersTab(Text title) {
        super(title);
        GridWidget.Adder adder = this.grid.createAdder(3);
        CyclingButtonWidget cbw = adder.add(CyclingButtonWidget.onOffBuilder(Text.of("PoisonWater: ON"), Text.of("PoisonWater: OFF")).build(Text.of("PoisonWater"), (buttonWidget, val) -> {
            this.poisonWater = val;
        }));
        CyclingButtonWidget cbw2 = adder.add(CyclingButtonWidget.onOffBuilder(Text.of("FireHeal: ON"), Text.of("FireHeal: OFF")).build(Text.of("FireHeal"), (buttonWidget, val) -> {
            this.fireHeal = val;
        }));
        CyclingButtonWidget cbw3 = adder.add(CyclingButtonWidget.onOffBuilder(Text.of("Speedy: ON"), Text.of("Speedy: OFF")).build(Text.of("Speedy"), (buttonWidget, val) -> {
            this.speedy = val;
        }));
        CyclingButtonWidget cbw4 = adder.add(CyclingButtonWidget.onOffBuilder(Text.of("RandomEffectTimer: ON"), Text.of("RandomEffectTimer: OFF")).build(Text.of("RandomEffectTimer"), (buttonWidget, val) -> {
            this.randomEffectTimer = val;
        }));
    }
}
