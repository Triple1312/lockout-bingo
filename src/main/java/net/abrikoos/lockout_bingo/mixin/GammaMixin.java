package net.abrikoos.lockout_bingo.mixin;

import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SimpleOption.class)
public class GammaMixin<T> {

    @Shadow Text text;

    @Shadow private T value;

    @Inject(at = @At("RETURN"), method = "setValue")
    public void setValue(T value, CallbackInfo ci) {
        try {
            if (this.text.getString().equals("Brightness")) {
                if (this.value.equals(Double.valueOf(1.0))) {
                    this.value = (T) Double.valueOf(4.0);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }



}
