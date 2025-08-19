package aboe.enchantlib.mixin;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static aboe.enchantlib.util.EnchantGen.getValidEnchantments;
import static aboe.enchantlib.util.EnchantGen.selectEnchantment;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
//This is just a test, it's useless.
    @Inject(method = "getAvailableEnchantmentResults", at = @At("HEAD"), cancellable = true)
    private static void injectList(int powerLevel, ItemStack itemStack, boolean allowTreasure, CallbackInfoReturnable<List<EnchantmentInstance>> cir){
        cir.setReturnValue(getValidEnchantments(powerLevel, itemStack, allowTreasure));
    }

    @Inject(method = "selectEnchantment", at = @At("HEAD"), cancellable = true)
    private static void injectSelected(RandomSource randomSource, ItemStack itemStack, int powerLevel, boolean allowTreasure,
                                       CallbackInfoReturnable<List<EnchantmentInstance>> cir){
        cir.setReturnValue(selectEnchantment(randomSource, powerLevel, itemStack, allowTreasure));
    }
}
