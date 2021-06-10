package com.bigchickenstudios.potatofood;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public final class ChestLootModifier extends LootModifier {

    private final ResourceLocation table;
    private final ItemRoll[] itemRolls;

    protected ChestLootModifier(ILootCondition[] conditionsIn, ResourceLocation tableIn, ItemRoll[] itemRollsIn) {
        super(conditionsIn);
        this.table = tableIn;
        this.itemRolls = itemRollsIn;
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        if (context.getQueriedLootTableId().equals(this.table)) {
            for (ItemRoll itemRoll : this.itemRolls) {
                itemRoll.roll(generatedLoot, context.getRandom());
            }
        }
        return generatedLoot;
    }

    public static final class Serializer extends GlobalLootModifierSerializer<ChestLootModifier> {

        @Override
        public ChestLootModifier read(ResourceLocation location, JsonObject object, ILootCondition[] ailootcondition) {
            ResourceLocation table = new ResourceLocation(JSONUtils.getString(object, "table"));
            JsonArray itemJsonArray = JSONUtils.getJsonArray(object, "items");
            ItemRoll[] itemRolls = new ItemRoll[itemJsonArray.size()];
            for (int i = 0; i < itemJsonArray.size(); i++) {
                JsonObject itemJson = JSONUtils.getJsonObject(itemJsonArray.get(i), "item");
                itemRolls[i] = new ItemRoll(JSONUtils.getFloat(itemJson, "chance"), JSONUtils.getInt(itemJson, "min"), JSONUtils.getInt(itemJson, "max"), JSONUtils.getItem(itemJson, "item"));

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

        public void roll(List<ItemStack> listIn, Random randomIn) {
            if (randomIn.nextFloat() < this.chance) {
                listIn.add(new ItemStack(item, this.min + randomIn.nextInt((this.max - this.min) + 1)));
            }
        }
    }
}
