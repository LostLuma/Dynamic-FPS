package dynamic_fps.impl.mixin.debug;

import dynamic_fps.impl.util.Logging;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.SoundEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SoundEngine.class)
public class SoundEngineMixin {
	@Inject(method = "updateCategoryVolume", at = @At("HEAD"))
	private void updateCategoryVolume(CallbackInfo callbackInfo) {
		dynamic_fps$logOffThreadUsage("updateCategoryVolume");
	}

	@Inject(method = "stop", at = @At("HEAD"))
	private void stop(CallbackInfo callbackInfo) {
		dynamic_fps$logOffThreadUsage("stop");
	}

	@Inject(method = "stopAll", at = @At("HEAD"))
	private void stopAll(CallbackInfo callbackInfo) {
		dynamic_fps$logOffThreadUsage("stopAll");
	}

	@Inject(method = "tickNonPaused", at = @At("HEAD"))
	private void tickNonPaused(CallbackInfo callbackInfo) {
		dynamic_fps$logOffThreadUsage("tickNonPaused");
	}

	@Inject(method = "isActive", at = @At("HEAD"))
	private void isActive(CallbackInfoReturnable<Boolean> cir) {
		dynamic_fps$logOffThreadUsage("isActive");
	}

	@Inject(method = "play", at = @At("HEAD"))
	private void play(CallbackInfo callbackInfo) {
		dynamic_fps$logOffThreadUsage("play");
	}

	@Inject(method = "playDelayed", at = @At("HEAD"))
	private void playDelayed(CallbackInfo callbackInfo) {
		dynamic_fps$logOffThreadUsage("playDelayed");
	}

	@Unique
	private void dynamic_fps$logOffThreadUsage(String method) {
		if (!Minecraft.getInstance().isSameThread()) {
			Logging.getLogger().warn("Potentially dangerous call to SoundEngine.{} from non-main thread!", method, new Throwable());
		}
	}
}
