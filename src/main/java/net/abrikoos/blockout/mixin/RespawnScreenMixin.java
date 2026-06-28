package net.abrikoos.blockout.mixin;

import net.abrikoos.blockout.client.gui.widget.TeammateRespawnButton;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.MessageScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(DeathScreen.class)
public abstract class RespawnScreenMixin {

    MinecraftClient a_client;

    @Shadow private ButtonWidget titleScreenButton;

    @Shadow private void onTitleScreenButtonClicked() {
        throw new AssertionError();
    }

    @Shadow @Final private List<ButtonWidget> buttons;

    @Inject(method = "init", at = @At("TAIL") )
    private void DeathScreenInit(CallbackInfo ci) {
        this.a_client = MinecraftClient.getInstance();
        ((ScreenMixin)(Object)this).invokeRemove(titleScreenButton);
        this.buttons.removeLast();
        titleScreenButton = new TeammateRespawnButton(titleScreenButton.getX(), titleScreenButton.getY(), titleScreenButton.getWidth(), titleScreenButton.getHeight(),
                button -> this.a_client.getAbuseReportContext().tryShowDraftScreen(this.a_client, (DeathScreen) (Object) this, this::onTitleScreenButtonClicked, true)
                );
        ((ScreenMixin)(Object)this).invokeAddDrawableChild(titleScreenButton);
        this.buttons.add(titleScreenButton);
        titleScreenButton.active = false;
//        titleScreenButton.active = true;
//        this.buttons.add(titleScreenButton);
    }

//    private void a_quitLevel() {
//        if (this.a_client.world != null) {
//            this.a_client.world.disconnect();
//        }
//
//        this.a_client.disconnect(new MessageScreen(Text.translatable("menu.savingLevel")));
//        this.a_client.setScreen(new TitleScreen());
//    }
//
//    private void a_onTitleScreenButtonClicked() {
//
//            ConfirmScreen confirmScreen = new DeathScreen.TitleScreenConfirmScreen(confirmed -> {
//                if (confirmed) {
//                    this.a_quitLevel();
//                } else {
//                    this.a_client.player.requestRespawn();
//                    this.a_client.setScreen(null);
//                }
//            }, Text.translatable("deathScreen.quit.confirm"), ScreenTexts.EMPTY, Text.translatable("deathScreen.titleScreen"), Text.translatable("deathScreen.respawn"));
//            this.a_client.setScreen(confirmScreen);
//            confirmScreen.disableButtons(20);
//
//    }




}
