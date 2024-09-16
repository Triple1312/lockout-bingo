package net.abrikoos.lockout_bingo.gui.widget.listwidgets;

import net.abrikoos.lockout_bingo.gui.widget.TeamSettingsWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.ParentElement;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.option.ControlsListWidget;
import net.minecraft.client.gui.widget.ElementListWidget;

import java.util.List;


@Environment(EnvType.CLIENT)
public class TeamListWidget {


    @Environment(EnvType.CLIENT)
    public abstract static class Entry extends ElementListWidget.Entry<ControlsListWidget.Entry> {
        abstract void update();
    }


    @Environment(EnvType.CLIENT)
    public static class TeamSelectWidget extends Entry implements ParentElement {
        TeamSettingsWidget wdgt;
        public TeamSelectWidget() {

        }

        @Override
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {

        }

        @Override
        public List<? extends Selectable> selectableChildren() {
            return List.of();
        }

        @Override
        public void update() {
        }

        @Override
        public List<? extends Element> children() {
            return List.of();
        }
    }














}
