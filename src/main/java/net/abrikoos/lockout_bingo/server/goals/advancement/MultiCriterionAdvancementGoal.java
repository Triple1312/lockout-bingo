package net.abrikoos.lockout_bingo.server.goals.advancement;

import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.util.Identifier;

public class MultiCriterionAdvancementGoal extends AdvancementGoal {
    public final int count;

    public MultiCriterionAdvancementGoal(int id, Identifier advancementid, int count) {
        super(id, advancementid, "ally", "");
        this.count = count;
    }

    @Override
    protected boolean validateProgress(AdvancementProgress progress, String criterionName) {
        int progress_count = 0;
        Iterable<String> it = progress.getObtainedCriteria();
        for (String ignored : progress.getObtainedCriteria()) {
            progress_count++;
        }
        return progress_count +1 >= this.count;
    }
}
