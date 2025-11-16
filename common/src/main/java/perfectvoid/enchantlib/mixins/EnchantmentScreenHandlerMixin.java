package perfectvoid.enchantlib.mixins;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.*;
import net.minecraft.util.collection.IndexedIterable;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import perfectvoid.enchantlib.EnchantLib;
import perfectvoid.enchantlib.config.ConfigGetter;
import perfectvoid.enchantlib.config.Configs;
import perfectvoid.enchantlib.util.EnchantmentPowerUtil;

import java.util.List;

import static perfectvoid.enchantlib.config.Configs.XZSize;

@Mixin(value = EnchantmentScreenHandler.class, priority = 100)
public abstract class EnchantmentScreenHandlerMixin extends ScreenHandler {
    @Shadow @Final private Inventory inventory;
    @Shadow @Final private ScreenHandlerContext context;
    @Shadow @Final private Random random;
    @Shadow @Final private Property seed;
    @Shadow @Final public int[] enchantmentPower;
    @Shadow @Final public int[] enchantmentId;
    @Shadow @Final public int[] enchantmentLevel;

    @Shadow
    protected abstract List<EnchantmentLevelEntry> generateEnchantments(DynamicRegistryManager registryManager, ItemStack stack, int slot, int level);

    protected EnchantmentScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        if (inventory == this.inventory) {
            ItemStack itemStack = inventory.getStack(0);
            if (!itemStack.isEmpty() && itemStack.isEnchantable()) {
                this.context.run((world, pos) -> {

                    IndexedIterable<RegistryEntry<Enchantment>> indexedIterable = world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT).getIndexedEntries();

                    if (XZSize > 15)
                        EnchantLib.logger.warn("Enchantment Table is set to a size of: " + XZSize + ". Performance might be hurt!");
                    int powerLevel = (int) EnchantmentPowerUtil.getPowerFromArea(world, pos, ConfigGetter.getTableSize(), Configs.obType, Configs.getMoreShelves);

                    this.random.setSeed((this.seed.get()));

                    for (int j = 0; j < 3; ++j) {
                        this.enchantmentPower[j] = EnchantmentHelper.calculateRequiredExperienceLevel(this.random, j, powerLevel, itemStack);
                        this.enchantmentId[j] = -1;
                        this.enchantmentLevel[j] = -1;
                        if (this.enchantmentPower[j] < j + 1) {
                            this.enchantmentPower[j] = 0;
                        }
                    }

                    for (int j = 0; j < 3; ++j) {
                        if (this.enchantmentPower[j] > 0) {
                            List<EnchantmentLevelEntry> list = this.generateEnchantments(world.getRegistryManager(), itemStack, j, this.enchantmentPower[j]);
                            if (list != null && !list.isEmpty()) {
                                EnchantmentLevelEntry enchantmentLevelEntry = list.get(this.random.nextInt(list.size()));
                                this.enchantmentId[j] = indexedIterable.getRawId(enchantmentLevelEntry.enchantment());
                                this.enchantmentLevel[j] = enchantmentLevelEntry.level();
                            }
                        }
                    }

                    this.sendContentUpdates();
                });
            } else {
                for (int i = 0; i < 3; ++i) {
                    this.enchantmentPower[i] = 0;
                    this.enchantmentId[i] = -1;
                    this.enchantmentLevel[i] = -1;
                }
            }
        }
    }
}

