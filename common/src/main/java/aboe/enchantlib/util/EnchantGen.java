package aboe.enchantlib.util;

import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

import java.util.*;

import static net.minecraft.world.item.enchantment.EnchantmentHelper.filterCompatibleEnchantments;

public class EnchantGen {

    //slightly modified version of the original enchantment table code.
    public static List<EnchantmentInstance> selectEnchantment(RandomSource randomSource, int powerLevel, ItemStack itemStack, Boolean allowTreasureEnchantment) {
        List<EnchantmentInstance> selectedEnchantments = new ArrayList<>();

        int enchantability = itemStack.getItem().getEnchantmentValue();

        if (enchantability <= 0) return selectedEnchantments;

        int dividedEnchantability = (enchantability >> 2) + 1;
        float variation = (randomSource.nextFloat() + randomSource.nextFloat() - 1f) * 0.15f;

        powerLevel += randomSource.nextInt(dividedEnchantability) + randomSource.nextInt(dividedEnchantability) + 1;
        powerLevel = Mth.clamp(Math.round(powerLevel + powerLevel * variation), 1, Integer.MAX_VALUE);

        List<EnchantmentInstance> availableEnchantments = getValidEnchantments(powerLevel, itemStack, allowTreasureEnchantment);

        //I should probably mod this, but I have no clue how...
        Optional<EnchantmentInstance> enchantment = WeightedRandom.getRandomItem(randomSource, availableEnchantments);
        enchantment.ifPresent(selectedEnchantments::add);

        while(randomSource.nextInt(50) <= powerLevel) {
            if (!selectedEnchantments.isEmpty()) {
                filterCompatibleEnchantments(availableEnchantments, Util.lastOf(selectedEnchantments));
            }

            if (availableEnchantments.isEmpty()) {
                break;
            }

            enchantment = WeightedRandom.getRandomItem(randomSource, availableEnchantments);
            enchantment.ifPresent(selectedEnchantments::add);
            powerLevel = powerLevel >> 1;
        }

        return selectedEnchantments;
    }


    public static List<EnchantmentInstance> getValidEnchantments(int powerLevel, ItemStack itemToEnchant, Boolean allowTreasure){
        List<EnchantmentInstance> ValidEnchantments = new ArrayList<>();
        Item item = itemToEnchant.getItem();
        boolean isBook = itemToEnchant.is(Items.BOOK);

        for (Enchantment enchantment : BuiltInRegistries.ENCHANTMENT) {
            if (!enchantment.category.canEnchant(item) && !isBook) continue;

            if (!enchantment.isDiscoverable()) continue;

            if (enchantment.isTreasureOnly() && !allowTreasure) continue;

            int minLevel = enchantment.getMinLevel() - 1;

            for (int j = enchantment.getMaxLevel(); j > minLevel; --j) {
                if (powerLevel >= enchantment.getMinCost(j) && powerLevel <= enchantment.getMaxCost(j)) {
                    ValidEnchantments.add(new EnchantmentInstance(enchantment, j));
                    break;
                }
            }
        }

        return ValidEnchantments;
    }
}
