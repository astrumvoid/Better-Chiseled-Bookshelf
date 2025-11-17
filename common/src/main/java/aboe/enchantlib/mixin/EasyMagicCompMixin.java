package aboe.enchantlib.mixin;

import aboe.enchantlib.EnchantLib;
import aboe.enchantlib.config.ConfigGetter;
import aboe.enchantlib.config.Configs;
import aboe.enchantlib.util.EnchantmentPowerUtil;
import fuzs.easymagic.world.inventory.ModEnchantmentMenu;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static aboe.enchantlib.config.Configs.XZSize;

@Mixin(ModEnchantmentMenu.class)
public class EasyMagicCompMixin {

    @Inject(method = "getEnchantingPower", at = @At("HEAD"), cancellable = true)
    private void getEnchantingPower(World world, net.minecraft.util.math.BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        if (XZSize > 15) EnchantLib.logger.warn("Enchantment Table is set to a size of: " + XZSize + ". Performance might be hurt!" );
        cir.setReturnValue((int)EnchantmentPowerUtil.getPowerFromArea(world, pos, ConfigGetter.getTableSize(), Configs.obType, Configs.getMoreShelves));
    }
}
