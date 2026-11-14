package com.example.mobx50.mixin;

import com.example.mobx50.MobX50;
import net.minecraft.entity.SpawnGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpawnGroup.class)
public abstract class SpawnGroupMixin {
    @Inject(method = "getCapacity", at = @At("RETURN"), cancellable = true)
    private void mobx50$multiplyCap(CallbackInfoReturnable<Integer> cir) {
        if (cir.getReturnValue() > 0) {
            cir.setReturnValue(cir.getReturnValue() * MobX50.MULTIPLIER);
        }
    }
}
