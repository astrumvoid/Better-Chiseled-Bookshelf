package aboe.bcbs.mixin;

import aboe.bcbs.util.EnchantmentPowerUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentTableBlock.class)
public abstract class EnchantmentTableBlockMixin extends BaseEntityBlock {

    protected EnchantmentTableBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "isValidBookShelf", at = @At(value = "HEAD"), cancellable = true)
    private static void checkValidEnchantProvider(Level world, BlockPos blockPos, BlockPos offset, CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(EnchantmentPowerUtils.isValidEnchantmentSource(world, blockPos, offset));
    }

}
