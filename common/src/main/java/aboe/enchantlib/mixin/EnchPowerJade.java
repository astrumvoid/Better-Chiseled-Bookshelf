package aboe.enchantlib.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import snownee.jade.util.CommonProxy;

import static aboe.enchantlib.util.EnchantmentPowerUtils.getCurrentBlockPower;

@Mixin(CommonProxy.class)
public abstract class EnchPowerJade {

    @Inject(method = "getEnchantPowerBonus", at = @At("HEAD"), cancellable = true)
    private static void enchPower(BlockState state, Level world, BlockPos pos, CallbackInfoReturnable<Float> cir){
        cir.setReturnValue(getCurrentBlockPower(world, pos, state));
    }
}
