package net.abrikoos.blockout;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(Blockout.MOD_ID)
public class BlockoutNeoForge {
    public BlockoutNeoForge() {
        Blockout.init();
        if (FMLEnvironment.dist == Dist.CLIENT) {
            BlockoutNeoForgeClient.init();
        }
    }
}
