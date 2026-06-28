package net.abrikoos.blockout.mixin;

import net.abrikoos.blockout.chunkgenerators.CustomWorldManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {

    @Overwrite
    public long getSeed() {
        ServerWorld self = (ServerWorld) (Object) this;
        RegistryKey<World> key = self.getRegistryKey();
        Long customSeed = CustomWorldManager.getCustomSeed(key);
        if (customSeed != null) return customSeed;
        return self.getServer().getSaveProperties().getGeneratorOptions().getSeed();
    }
}
