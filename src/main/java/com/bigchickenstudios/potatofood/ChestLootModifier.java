package com.bigchickenstudios.potatofood;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.common.util.JsonUtils;

import javax.annotation.Nonnull;
import java.util.List;

public final class ChestLootModifier extends LootModifier {

    private final ResourceLocation table;
    private final ItemRoll[] itemRolls;

    protected ChestLootModifier(LootItemCondition[] conditionsIn, ResourceLocation tableIn, ItemRoll[] itemRollsIn) {
        super(conditionsIn);
        this.table = tableIn;
        this.itemRolls = itemRollsIn;
    }

    @Nonnull
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (context.getQueriedLootTableId().equals(this.table)) {
            for (ItemRoll itemRoll : this.itemRolls) {
                itemRoll.roll(generatedLoot, context.getRandom().fork());
            }
        }
        return generatedLoot;
    }

    public static final class Serializer extends GlobalLootModifierSerializer<ChestLootModifier> {

        @Override
        public ChestLootModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] ailootcondition) {
            ResourceLocation table = new ResourceLocation(GsonHelper.getAsString(object, "table"));
            JsonArray itemJsonArray = GsonHelper.getAsJsonArray(object, "items");
            ItemRoll[] itemRolls = new ItemRoll[itemJsonArray.size()];
            for (int i = 0; i < itemJsonArray.size(); i++) {
                JsonObject itemJson = GsonHelper.convertToJsonObject(itemJsonArray.get(i), "item");
                itemRolls[i] = new ItemRoll(GsonHelper.getAsFloat(itemJson, "chance"), GsonHelper.getAsInt(itemJson, "min"), GsonHelper.getAsInt(itemJson, "max"), GsonHelper.getAsItem(itemJson, "item"));

            }
            return new ChestLootModifier(ailootcondition, table, itemRolls);
        }

        @Override
        public JsonObject write(ChestLootModifier instance) {
            return null;
        }
    }

    private static final class ItemRoll {

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
