package com.bigchickenstudios.potatofood;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Supplier;

public final class ChestLootModifier extends LootModifier {

    public static final Supplier<Codec<ChestLootModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create(inst -> LootModifier.codecStart(inst).and(ItemRoll.CODEC.listOf().fieldOf("items").forGetter(o -> o.itemRolls)).apply(inst, ChestLootModifier::new)));

    private final List<ItemRoll> itemRolls;

    protected ChestLootModifier(LootItemCondition[] conditionsIn, List<ItemRoll> itemRollsIn) {
        super(conditionsIn);
        this.itemRolls = itemRollsIn;
    }

    @Nonnull
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        for (ItemRoll itemRoll : this.itemRolls) {
            itemRoll.roll(generatedLoot, context.getRandom().fork());
        }
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }

    private static final class ItemRoll {
        public static final Codec<ItemRoll> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                Codec.FLOAT.fieldOf("chance").forGetter(o -> o.chance),
                Codec.INT.fieldOf("min").forGetter(o -> o.min),
                Codec.INT.fieldOf("max").forGetter(o -> o.max),
                ForgeRegistries.ITEMS.getCodec().fieldOf("item").forGetter(o -> o.item)
            ).apply(inst, ItemRoll::new)
        );

        private final float chance;
        private final int min;
        private final int max;
        private final Item item;

        public ItemRoll(float chanceIn, int minIn, int maxIn, Item itemIn) {
            this.chance = chanceIn;
            this.min = minIn;
            this.max = maxIn;
            this.item = itemIn;
        }

        public void roll(List<ItemStack> listIn, RandomSource randomIn) {
            if (randomIn.nextFloat() < this.chance) {
                listIn.add(new ItemStack(item, this.min + randomIn.nextInt((this.max - this.min) + 1)));
            }
        }
    }
}
