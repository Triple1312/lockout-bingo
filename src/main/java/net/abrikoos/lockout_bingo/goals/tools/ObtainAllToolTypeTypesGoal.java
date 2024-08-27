package net.abrikoos.lockout_bingo.goals.tools;

import net.abrikoos.lockout_bingo.goals.LockoutGoal;
import net.abrikoos.lockout_bingo.goals.LockoutGoalEvent;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolItem;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Objects;

// always {wood, iron, gold, diamond, netherite}

public class ObtainAllToolTypeTypesGoal extends LockoutGoal {
    ToolItem tooltype;

    public ObtainAllToolTypeTypesGoal(int id, ToolItem tooltype) {
        super(id);
        this.tooltype = tooltype;

        ServerTickEvents.START_SERVER_TICK.register(this::checkCompletion);
    }

    public void checkCompletion(MinecraftServer minecraftServer) { // todo not tested
        if (completed != null) { return; }
        for (ServerPlayerEntity player : minecraftServer.getPlayerManager().getPlayerList()) {
            PlayerInventory inventory = player.getInventory();
            boolean wood = false;
            boolean iron = false;
            boolean gold = false;
            boolean diamond = false;
            boolean netherite = false;
            for ( ItemStack item : inventory.main) {
                if (item.getItem() instanceof ToolItem mitem) {
                    if (mitem.getName() == tooltype.getName()) {
                        if (mitem.getMaterial().equals(net.minecraft.item.ToolMaterials.WOOD)) {
                            wood = true;
                        }
                        if (mitem.getMaterial().equals(net.minecraft.item.ToolMaterials.IRON)) {
                            iron = true;
                        }
                        if (mitem.getMaterial().equals(net.minecraft.item.ToolMaterials.GOLD)) {
                            gold = true;
                        }
                        if (mitem.getMaterial().equals(net.minecraft.item.ToolMaterials.DIAMOND)) {
                            diamond = true;
                        }
                        if (mitem.getMaterial().equals(net.minecraft.item.ToolMaterials.NETHERITE)) {
                            netherite = true;
                        }
                    }
                }
            }
            if (wood && iron && gold && diamond && netherite) {
                this.notifyListeners(new LockoutGoalEvent(player.getUuidAsString(), "ally", this.id));
                completed = player;
            }
        }
    }
}
