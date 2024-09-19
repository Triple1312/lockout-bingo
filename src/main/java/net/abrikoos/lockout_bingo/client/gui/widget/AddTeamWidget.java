package net.abrikoos.lockout_bingo.client.gui.widget;

import net.abrikoos.lockout_bingo.network.team.LockoutAddTeamPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.Positioner;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class AddTeamWidget extends GridWidget {

    public AddTeamWidget() {
        super();
        this.init();

    }

    protected void init() {
        MinecraftClient client = MinecraftClient.getInstance();
        GridWidget.Adder adder = this.setRowSpacing(8).setColumnSpacing(10).createAdder(2);
        Positioner positioner = adder.copyPositioner();
        TextFieldWidget tfw = new TextFieldWidget(client.textRenderer, 120, 20, Text.of("Team Name"));
        adder.add(
                ButtonWidget.builder(Text.of("Add"), (btn) -> {
                    ClientPlayNetworking.send(new LockoutAddTeamPacket(tfw.getText()));
                    tfw.setText("");
                }).width(45).build(),
                positioner.alignRight()
        );
        adder.add(tfw);


    }

}
