package aboe.enchantlib.mixin;

import aboe.enchantlib.util.EnchantmentPowerUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import snownee.jade.util.CommonProxy;

import static aboe.enchantlib.util.EnchantmentPowerUtils.getValidEnchantmentPowerFromBlock;

@Mixin(CommonProxy.class)
public class EnchPowerJade {

    @Inject(method = "getEnchantPowerBonus", at = @At("HEAD"), cancellable = true)
    private static void enchPower(BlockState state, Level world, BlockPos pos, CallbackInfoReturnable<Float> cir){
        cir.setReturnValue(getValidEnchantmentPowerFromBlock(world, pos, new BlockPos(0, 0,0), EnchantmentPowerUtils.PathCheckMode.NONE));
    }
}
