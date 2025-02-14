package aboe.EnchantLib.mixin;

import aboe.EnchantLib.util.EnchantmentPowerUtils;
import fuzs.easymagic.world.inventory.ModEnchantmentMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static aboe.EnchantLib.config.DefaultConfig.NEW_BOOKSHELF_OFFSETS;
import static aboe.EnchantLib.util.EnchantmentPowerUtils.getEnchantmentPower;

@Mixin(ModEnchantmentMenu.class)
public class EasyMagicComp {

    @Inject(method = "getEnchantingPower", at = @At("HEAD"), cancellable = true)
    private void getEnchantingPower(Level world, BlockPos pos, CallbackInfoReturnable<Integer> cir){
        cir.setReturnValue((int)getEnchantmentPower(world, pos, NEW_BOOKSHELF_OFFSETS, EnchantmentPowerUtils.PathCheckMode.FULL));
    }

}
