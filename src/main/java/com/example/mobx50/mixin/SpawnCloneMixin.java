package com.example.mobx50.mixin;

import com.example.mobx50.MobX50;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public abstract class SpawnCloneMixin {
    @Inject(method = "initialize", at = @At("RETURN"))
    private void mobx50$clone(ServerWorldAccess world, LocalDifficulty difficulty,
                              SpawnReason spawnReason, EntityData entityData,
                              CallbackInfoReturnable<EntityData> cir) {
        if (spawnReason != SpawnReason.NATURAL || MobX50.cloning) return;
        MobEntity self = (MobEntity) (Object) this;
        ServerWorld sw = world.toServerWorld();
        MobX50.cloning = true;
        try {
            for (int i = 0; i < MobX50.CLONES; i++) {
                Entity e = self.getType().create(sw, SpawnReason.NATURAL);
                if (!(e instanceof MobEntity clone)) continue;
                clone.setPosition(self.getX(), self.getY(), self.getZ());
                clone.setYaw(self.getYaw());
                clone.initialize(sw, difficulty, SpawnReason.NATURAL, null);
                sw.spawnEntity(clone);
            }
        } finally {
            MobX50.cloning = false;
        }
    }
}
