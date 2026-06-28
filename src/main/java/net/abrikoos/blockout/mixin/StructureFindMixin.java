package net.abrikoos.blockout.mixin;


import net.abrikoos.blockout.server.gamestate.GameState;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StructureStart.class)
public class StructureFindMixin {

//    @Inject(method= "place", at = @At("TAIL"))
//    private void onPlace(
//            StructureWorldAccess world,
//            StructureAccessor structureAccessor,
//            ChunkGenerator chunkGenerator,
//            Random random,
//            BlockBox chunkBox,
//            ChunkPos chunkPos,
//            CallbackInfo ci) {
//        GameState.registerStructure((StructureStart) (Object) this);
//    }



}
