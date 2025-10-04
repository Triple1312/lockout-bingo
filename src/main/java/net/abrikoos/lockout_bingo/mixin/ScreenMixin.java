package net.abrikoos.lockout_bingo.mixin;

import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Screen.class)
public interface ScreenMixin {

    @Invoker("remove")
    public void invokeRemove(Element child);


    @Invoker("addDrawableChild")
    public <T extends Element> T invokeAddDrawableChild(T element);

}
