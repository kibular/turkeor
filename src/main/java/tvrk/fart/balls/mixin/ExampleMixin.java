package tvrk.fart.balls.mixin;


import tvrk.fart.balls.AddonTemplate;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// ASMaxxer vs Mixincel

@Mixin(MinecraftClient.class)
public abstract class ExampleMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onGameLoaded(RunArgs args, CallbackInfo ci) {
        AddonTemplate.LOG.info("Hello from ExampleM*xin!");
    }
}
