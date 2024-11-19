package xyz.amymialee.guardeez.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.GuardianEntityRenderer;
import net.minecraft.client.render.entity.state.GuardianEntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.amymialee.guardeez.GuardeezClient;
import xyz.amymialee.guardeez.util.YawHolder;

@Mixin(GuardianEntityRenderer.class)
public class GuardianEntityRendererMixin {
    @WrapOperation(method = "render(Lnet/minecraft/client/render/entity/state/GuardianEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/MobEntityRenderer;render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"))
    public void guardeez$betterAngles(GuardianEntityRenderer instance, @NotNull LivingEntityRenderState state, @NotNull MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, @NotNull Operation<Void> original) {
        var pitch = state.pitch;
        var yaw = ((GuardianEntityRenderState) state).guardeez$getYaw();
        matrixStack.push();
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-yaw));
        matrixStack.translate(0f, -Math.abs(pitch / 90f), pitch / 90f);
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(yaw));
        original.call(instance, state, matrixStack, vertexConsumerProvider, i);
        matrixStack.pop();
    }

    @Inject(method = "updateRenderState(Lnet/minecraft/entity/mob/GuardianEntity;Lnet/minecraft/client/render/entity/state/GuardianEntityRenderState;F)V", at = @At(value = "TAIL"))
    private void guardeez$head(@NotNull GuardianEntity guardianEntity, @NotNull GuardianEntityRenderState state, float f, CallbackInfo ci) {
        state.guardeez$setYaw(guardianEntity.headYaw);
    }
}