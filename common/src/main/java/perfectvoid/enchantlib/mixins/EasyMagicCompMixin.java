package perfectvoid.enchantlib.mixins;

import fuzs.easymagic.world.inventory.ModEnchantmentMenu;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import perfectvoid.enchantlib.EnchantLib;
import perfectvoid.enchantlib.config.ConfigGetter;
import perfectvoid.enchantlib.config.Configs;
import perfectvoid.enchantlib.util.EnchantmentPowerUtil;

import static perfectvoid.enchantlib.config.Configs.XZSize;

@Mixin(value = ModEnchantmentMenu.class)
public abstract class EasyMagicCompMixin {

    @Inject(method = "getEnchantingPower", at = @At("HEAD"), cancellable = true)
    private void getEnchantingPower(World world, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        if (XZSize > 15) EnchantLib.logger.warn("Enchantment Table is set to a size of: " + XZSize + ". Performance might be hurt!" );
        cir.setReturnValue((int)EnchantmentPowerUtil.getPowerFromArea(world, pos, ConfigGetter.getTableSize(), Configs.obType, Configs.getMoreShelves));
    }
}
