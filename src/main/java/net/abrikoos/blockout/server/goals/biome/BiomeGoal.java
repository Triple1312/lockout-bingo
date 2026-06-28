package net.abrikoos.blockout.server.goals.biome;

import net.abrikoos.blockout.server.goals.advancement.AdvancementGoal;
import net.minecraft.util.Identifier;

public class BiomeGoal extends AdvancementGoal {

    public BiomeGoal(int id, String criterionName) {
        super(id, Identifier.of("minecraft", "adventure/adventuring_time"), "ally", criterionName);
    }
}
