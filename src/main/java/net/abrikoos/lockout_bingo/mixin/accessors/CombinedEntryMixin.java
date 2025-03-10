package net.abrikoos.lockout_bingo.mixin.accessors;

import net.minecraft.loot.entry.CombinedEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(CombinedEntry.class)
public interface CombinedEntryMixin {


    @Accessor("children")
    List<LootPoolEntry> getChildren();
}
