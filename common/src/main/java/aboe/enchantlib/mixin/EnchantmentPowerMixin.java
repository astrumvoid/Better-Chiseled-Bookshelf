package aboe.enchantlib.mixin;

import aboe.enchantlib.util.EnchantmentPowerUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

import static aboe.enchantlib.config.DefaultConfig.NEW_BOOKSHELF_OFFSETS;

@Mixin(EnchantmentMenu.class)
public abstract class EnchantmentPowerMixin extends AbstractContainerMenu {

    @Shadow @Final private Container enchantSlots;
    @Shadow @Final private ContainerLevelAccess access;
    @Shadow @Final private RandomSource random;

    @Shadow @Final private DataSlot enchantmentSeed;

    @Shadow @Final public int[] costs;
    @Shadow @Final public int[] enchantClue;
    @Shadow @Final public int[] levelClue;

    @Shadow protected abstract List<EnchantmentInstance> getEnchantmentList(ItemStack arg, int i, int j);

    protected EnchantmentPowerMixin(@Nullable MenuType<?> menuType, int i) {
        super(menuType, i);
    }

    @Override
    public void slotsChanged(Container container) {
        if (container == this.enchantSlots) {
            ItemStack itemStack = container.getItem(0);
            if (!itemStack.isEmpty() && itemStack.isEnchantable()) {
                this.access.execute((world, originBlockPos) -> {
                    int enchantPower = (int)EnchantmentPowerUtils.getEnchantmentPower(world, originBlockPos,
                            NEW_BOOKSHELF_OFFSETS, EnchantmentPowerUtils.PathCheckMode.FULL);

                    this.random.setSeed(this.enchantmentSeed.get());

                    int j;
                    for(j = 0; j < 3; ++j) {
                        this.costs[j] = EnchantmentHelper.getEnchantmentCost(this.random, j, enchantPower, itemStack);
                        this.enchantClue[j] = -1;
                        this.levelClue[j] = -1;
                        if (this.costs[j] < j + 1)
                            this.costs[j] = 0;
                    }

                    for(j = 0; j < 3; ++j) {
                        if (this.costs[j] > 0) {
                            List<EnchantmentInstance> list = this.getEnchantmentList(itemStack, j, this.costs[j]);
                            if (list != null && !list.isEmpty()) {
                                EnchantmentInstance enchantmentInstance = list.get(this.random.nextInt(list.size()));
                                this.enchantClue[j] = BuiltInRegistries.ENCHANTMENT.getId(enchantmentInstance.enchantment);
                                this.levelClue[j] = enchantmentInstance.level;
                            }
                        }
                    }

                    this.broadcastChanges();
                });
            } else {
                for(int i = 0; i < 3; ++i) {
                    this.costs[i] = 0;
                    this.enchantClue[i] = -1;
                    this.levelClue[i] = -1;
                }
            }
        }

    }
}
