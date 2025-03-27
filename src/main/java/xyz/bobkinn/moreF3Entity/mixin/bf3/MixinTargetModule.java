package xyz.bobkinn.moreF3Entity.mixin.bf3;

import com.llamalad7.mixinextras.sugar.Local;
import me.cominixo.betterf3.modules.TargetModule;
import me.cominixo.betterf3.utils.DebugLine;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TargetModule.class)
public class MixinTargetModule {

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 10, shift = At.Shift.AFTER))
    public void onInit(CallbackInfo ci) {
        TargetModule self = (TargetModule) (Object) this;
        self.lines().add(new DebugLine("targeted_entity_ids"));
    }

    @Inject(method = "update", at = @At(value = "INVOKE", remap = false, target = "Lme/cominixo/betterf3/utils/DebugLine;value(Ljava/lang/Object;)V", ordinal = 4, shift = At.Shift.AFTER))
    public void onSetEntityType(Minecraft client, CallbackInfo ci, @Local(name = "entity") Entity entity) {
        TargetModule self = (TargetModule) (Object) this;
        self.line("targeted_entity_ids").value(entity.getId()+"/"+entity.getUUID());
    }

    @Inject(method = "update", at = @At(value = "FIELD", remap = false, target = "Lme/cominixo/betterf3/utils/DebugLine;active:Z", shift = At.Shift.AFTER, args = ""))
    public void onUnsetEntityType(Minecraft client, CallbackInfo ci) {
        TargetModule self = (TargetModule) (Object) this;
        self.line("targeted_entity_ids").active = false;
    }
}
