package net.abrikoos.lockout_bingo.goals.craft;

import net.abrikoos.lockout_bingo.goals.stats.StatGoal;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;

public class DuplicateTemplateGoal extends StatGoal {

    public DuplicateTemplateGoal(int id) {
        super(id);
    }

    @Override
    protected boolean validateProgress(ServerPlayerEntity player) {
        if (player.getStatHandler().getStat(Stats.CRAFTED.getOrCreateStat(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE)) > 0) {
            return true;
        }
        else if (player.getStatHandler().getStat(Stats.CRAFTED.getOrCreateStat(Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE)) > 0) {
            return true;
        }
        else if (player.getStatHandler().getStat(Stats.CRAFTED.getOrCreateStat(Items.VEX_ARMOR_TRIM_SMITHING_TEMPLATE)) > 0) {
            return true;
        }
        else if (player.getStatHandler().getStat(Stats.CRAFTED.getOrCreateStat(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE)) > 0) {
            return true;
        }
        else if (player.getStatHandler().getStat(Stats.CRAFTED.getOrCreateStat(Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE)) >0) {
            return true;
        }
        else if (player.getStatHandler().getStat(Stats.CRAFTED.getOrCreateStat(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE)) > 0) {
            return true;
        }
        else if (player.getStatHandler().getStat(Stats.CRAFTED.getOrCreateStat(Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE)) > 0) {
            return true;
        }
        else if (player.getStatHandler().getStat(Stats.CRAFTED.getOrCreateStat(Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE)) > 0) {
            return true;
        }
        else if (player.getStatHandler().getStat(Stats.CRAFTED.getOrCreateStat(Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE)) > 0) {
            return true;
        }
        else if (player.getStatHandler().getStat(Stats.CRAFTED.getOrCreateStat(Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE)) > 0) {
            return true;
        }
        else if (player.getStatHandler().getStat(Stats.CRAFTED.getOrCreateStat(Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE)) > 0) {
            return true;
        }
        else if (player.getStatHandler().getStat(Stats.CRAFTED.getOrCreateStat(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE)) > 0) {
            return true;
        }
        else if (player.getStatHandler().getStat(Stats.CRAFTED.getOrCreateStat(Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE)) > 0) {
            return true;
        }
        else if (player.getStatHandler().getStat(Stats.CRAFTED.getOrCreateStat(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE)) > 0) {
            return true;
        }
        else if (player.getStatHandler().getStat(Stats.CRAFTED.getOrCreateStat(Items.RIB_ARMOR_TRIM_SMITHING_TEMPLATE)) > 0) {
            return true;
        }
        else if (player.getStatHandler().getStat(Stats.CRAFTED.getOrCreateStat(Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE)) > 0) {
            return true;
        }
        else return player.getStatHandler().getStat(Stats.CRAFTED.getOrCreateStat(Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE)) > 0;

    }
}
