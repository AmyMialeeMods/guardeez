package xyz.amymialee.guardeez.mixin;

import net.minecraft.client.render.entity.state.GuardianEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import xyz.amymialee.guardeez.util.YawHolder;

@Mixin(GuardianEntityRenderState.class)
public class GuardianEntityRenderStateMixin implements YawHolder {
    private @Unique float guardeez$yaw;

    public @Override float guardeez$getYaw() {
        return this.guardeez$yaw;
    }

    public @Override void guardeez$setYaw(float yaw) {
        this.guardeez$yaw = yaw;
    }
}