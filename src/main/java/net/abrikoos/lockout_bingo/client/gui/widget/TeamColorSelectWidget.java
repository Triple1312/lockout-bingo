package net.abrikoos.lockout_bingo.client.gui.widget;

import net.abrikoos.lockout_bingo.network.team.ChangeTeamIdPacket;
import net.abrikoos.lockout_bingo.team.Colors;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class TeamColorSelectWidget extends ButtonWidget {

    int oldIndex;
    int newIndex;

    public TeamColorSelectWidget(int x, int y, int oldIndex, int newIndex) {
        super(x, y, 20, 20, Text.of(""), e -> ClientPlayNetworking.send(new ChangeTeamIdPacket(oldIndex, newIndex)), ButtonWidget.DEFAULT_NARRATION_SUPPLIER);
        this.oldIndex = oldIndex;
        this.newIndex = newIndex;
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderWidget(context, mouseX, mouseY, delta);
        context.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, Colors.get(this.newIndex));
    }

}
