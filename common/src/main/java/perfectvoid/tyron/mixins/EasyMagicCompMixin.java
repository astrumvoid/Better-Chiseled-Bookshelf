package perfectvoid.tyron.mixins;

import fuzs.easymagic.world.inventory.ModEnchantmentMenu;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import perfectvoid.tyron.EnchantLib;
import perfectvoid.tyron.config.ConfigGetter;
import perfectvoid.tyron.config.Configs;
import perfectvoid.tyron.util.EnchantmentPowerUtil;

import static perfectvoid.tyron.config.Configs.XZSize;

@Mixin(ModEnchantmentMenu.class)
public abstract class EasyMagicCompMixin {

    @Inject(method = "getEnchantingPower", at = @At("HEAD"), cancellable = true)
    private void getEnchantingPower(World world, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        if (XZSize > 15) EnchantLib.logger.warn("Enchantment Table is set to a size of: " + XZSize + ". Performance might be hurt!" );
        cir.setReturnValue((int)EnchantmentPowerUtil.getPowerFromValidProviders(world, pos, ConfigGetter.getTableSize(), Configs.obType));
    }
}
