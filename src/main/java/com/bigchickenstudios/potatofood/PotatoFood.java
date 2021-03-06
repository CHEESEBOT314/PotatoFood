package com.bigchickenstudios.potatofood;


import net.minecraft.block.Block;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.*;
import net.minecraft.loot.*;
import net.minecraft.loot.functions.SetCount;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@Mod(PotatoFood.MODID)
public class PotatoFood {
    private static final Logger LOGGER = LogManager.getLogger();

    public static final String MODID = "potatofood";

    public PotatoFood() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        Blocks.BLOCK_DEFERRED_REGISTER.register(bus);
        Items.ITEM_DEFERRED_REGISTER.register(bus);
        LootModifierSerializers.LOOT_MODIFIER_SERIALIZER_DEFERRED_REGISTER.register(bus);

        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> PotatoFoodClient::init);
    }

    private static class Foods {
        private static final Food APPLE_PIE = (new Food.Builder()).hunger(8).saturation(0.3F).build();
        private static final Food BACON_EGG = (new Food.Builder()).hunger(10).saturation(0.7F).build();
        private static final Food BEEF_BURGER = (new Food.Builder()).hunger(10).saturation(0.8F).build();
        private static final Food BEETROOT_BURGER = (new Food.Builder()).hunger(10).saturation(0.8F).build();
        private static final Food CARROT_POTATO_SOUP = (new Food.Builder()).hunger(6).saturation(0.8F).build();
        private static final Food CHEESE = (new Food.Builder()).hunger(3).saturation(0.6F).build();
        private static final Food CHEESEBURGER = (new Food.Builder()).hunger(12).saturation(0.6F).build();
        private static final Food CHOCOLATE_ICE_CREAM = (new Food.Builder()).hunger(5).saturation(0.6F).build();
        private static final Food COD_NIGIRI = (new Food.Builder()).hunger(4).saturation(0.3F).build();
        private static final Food COD_SANDWICH = (new Food.Builder()).hunger(8).saturation(0.6F).build();
        private static final Food FISH_AND_CHIPS = (new Food.Builder()).hunger(11).saturation(0.7F).build();
        private static final Food FRIED_EGG = (new Food.Builder()).hunger(3).saturation(0.6F).build();
        private static final Food HAM_CHEESE_SANDWICH = (new Food.Builder()).hunger(12).saturation(0.6F).build();
        private static final Food HAM_SANDWICH = (new Food.Builder()).hunger(10).saturation(0.7F).build();
        private static final Food MEAT_PIZZA = (new Food.Builder()).hunger(16).saturation(0.625F).build();
        private static final Food MUESLI = (new Food.Builder()).hunger(11).saturation(0.7F).build();
        private static final Food PAELLA = (new Food.Builder()).hunger(11).saturation(0.7F).build();
        private static final Food PIZZA = (new Food.Builder()).hunger(12).saturation(0.6F).build();
        private static final Food RICE = (new Food.Builder()).hunger(1).saturation(0.3F).build();
        private static final Food SALMON_MAKI = (new Food.Builder()).hunger(10).saturation(0.7F).build();
        private static final Food SALMON_NIGIRI = (new Food.Builder()).hunger(5).saturation(0.3F).build();
        private static final Food SALMON_URAMAKI = (new Food.Builder()).hunger(8).saturation(0.6F).build();
        private static final Food SWEET_BERRY_PIE = (new Food.Builder()).hunger(8).saturation(0.3F).build();
        private static final Food YOGHURT = (new Food.Builder()).hunger(5).saturation(0.6F).build();
    }

    public static class Blocks {
        private static final UnaryOperator<Block.Properties> IBP = UnaryOperator.identity();
        private static final DeferredRegister<Block> BLOCK_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

        public static final RegistryObject<CakeBlock> CHOCOLATE_CAKE = BLOCK_DEFERRED_REGISTER.register("chocolate_cake", () -> new CakeBlock(Block.Properties.create(Material.CAKE).hardnessAndResistance(0.5F).sound(SoundType.CLOTH)));
        public static final RegistryObject<RiceBlock> RICE = BLOCK_DEFERRED_REGISTER.register("rice", () -> new RiceBlock(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.CROP)));
    }

    public static class Items {
        private static final UnaryOperator<Item.Properties> IIP = UnaryOperator.identity();
        private static final DeferredRegister<Item> ITEM_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

        public static final RegistryObject<Item> APPLE_PIE = registerFood("apple_pie", Foods.APPLE_PIE, IIP);
        public static final RegistryObject<Item> BACON_EGG = registerFood("bacon_egg", Foods.BACON_EGG, IIP);
        public static final RegistryObject<Item> BEEF_BURGER = registerFood("beef_burger", Foods.BEEF_BURGER, IIP);
        public static final RegistryObject<Item> BEETROOT_BURGER = registerFood("beetroot_burger", Foods.BEETROOT_BURGER, IIP);
        public static final RegistryObject<SoupItem> CARROT_POTATO_SOUP = registerSoup("carrot_potato_soup", Foods.CARROT_POTATO_SOUP, (p) -> p.maxStackSize(1));
        public static final RegistryObject<Item> CHEESE = registerFood("cheese", Foods.CHEESE, IIP);
        public static final RegistryObject<Item> CHEESEBURGER = registerFood("cheeseburger", Foods.CHEESEBURGER, (p) -> p.rarity(Rarity.UNCOMMON));
        public static final RegistryObject<BlockItem> CHOCOLATE_CAKE = registerCake("chocolate_cake", Blocks.CHOCOLATE_CAKE, (p) -> p.maxStackSize(1));
        public static final RegistryObject<Item> CHOCOLATE_ICE_CREAM = registerFood("chocolate_ice_cream", Foods.CHOCOLATE_ICE_CREAM, IIP);
        public static final RegistryObject<Item> COD_NIGIRI = registerFood("cod_nigiri", Foods.COD_NIGIRI, IIP);
        public static final RegistryObject<Item> COD_SANDWICH = registerFood("cod_sandwich", Foods.COD_SANDWICH, IIP);
        public static final RegistryObject<Item> FISH_AND_CHIPS = registerFood("fish_and_chips", Foods.FISH_AND_CHIPS, IIP);
        public static final RegistryObject<Item> FRIED_EGG = registerFood("fried_egg", Foods.FRIED_EGG, IIP);
        public static final RegistryObject<Item> HAM_CHEESE_SANDWICH = registerFood("ham_cheese_sandwich", Foods.HAM_CHEESE_SANDWICH, (p) -> p.rarity(Rarity.UNCOMMON));
        public static final RegistryObject<Item> HAM_SANDWICH = registerFood("ham_sandwich", Foods.HAM_SANDWICH, IIP);
        public static final RegistryObject<Item> MEAT_PIZZA = registerFood("meat_pizza", Foods.MEAT_PIZZA, (p) -> p.rarity(Rarity.RARE));
        public static final RegistryObject<SoupItem> MUESLI = registerSoup("muesli", Foods.MUESLI, IIP);
        public static final RegistryObject<Item> PAELLA = registerFood("paella", Foods.PAELLA, IIP);
        public static final RegistryObject<Item> PIZZA = registerFood("pizza", Foods.PIZZA, (p) -> p.rarity(Rarity.UNCOMMON));
        public static final RegistryObject<BlockItem> RICE = ITEM_DEFERRED_REGISTER.register("rice", () -> new BlockItem(Blocks.RICE.get(), (new Item.Properties()).food(Foods.RICE).group(ItemGroup.FOOD)));
        public static final RegistryObject<Item> SALMON_MAKI = registerFood("salmon_maki", Foods.SALMON_MAKI, IIP);
        public static final RegistryObject<Item> SALMON_NIGIRI = registerFood("salmon_nigiri", Foods.SALMON_NIGIRI, IIP);
        public static final RegistryObject<Item> SALMON_URAMAKI = registerFood("salmon_uramaki", Foods.SALMON_URAMAKI, IIP);
        public static final RegistryObject<Item> SWEET_BERRY_PIE = registerFood("sweet_berry_pie", Foods.SWEET_BERRY_PIE, IIP);
        public static final RegistryObject<SoupItem> YOGHURT = registerSoup("yoghurt", Foods.YOGHURT, IIP);

        private static RegistryObject<Item> registerFood(String name, Food food, UnaryOperator<Item.Properties> pMod) {
            return ITEM_DEFERRED_REGISTER.register(name, () -> new Item(pMod.apply(new Item.Properties()).food(food).group(ItemGroup.FOOD)));
        }
        private static RegistryObject<SoupItem> registerSoup(String name, Food food, UnaryOperator<Item.Properties> pMod) {
            return ITEM_DEFERRED_REGISTER.register(name, () -> new SoupItem(pMod.apply(new Item.Properties()).food(food).group(ItemGroup.FOOD)));
        }
        private static RegistryObject<BlockItem> registerCake(String name, Supplier<? extends Block> block, UnaryOperator<Item.Properties> pMod) {
            return ITEM_DEFERRED_REGISTER.register(name, () -> new BlockItem(block.get(), pMod.apply(new Item.Properties()).group(ItemGroup.FOOD)));
        }
    }

    public static class LootModifierSerializers {
        private static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIER_SERIALIZER_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, MODID);

        public static final RegistryObject<GlobalLootModifierSerializer<ChestLootModifier>> CHEST_LOOT_MODIFIER = LOOT_MODIFIER_SERIALIZER_DEFERRED_REGISTER.register("chest_loot", ChestLootModifier.Serializer::new);
    }
}
