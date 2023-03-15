package com.bigchickenstudios.potatofood;

import com.mojang.serialization.Codec;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
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
        bus.addListener(this::onCreativeModeTabBuildContents);
    }

    private static class Foods {
        private static final FoodProperties APPLE_PIE = getBuilder(8, 0.3F).build();
        private static final FoodProperties BACON_EGG = getBuilder(10, 0.7F).build();
        private static final FoodProperties BEEF_BURGER = getBuilder(10, 0.8F).build();
        private static final FoodProperties BEETROOT_BURGER = getBuilder(10, 0.8F).build();
        private static final FoodProperties CARROT_POTATO_SOUP = getBuilder(6, 0.8F).build();
        private static final FoodProperties CHEESE = getBuilder(3, 0.6F).build();
        private static final FoodProperties CHEESEBURGER = getBuilder(12, 0.6F).build();
        private static final FoodProperties CHOCOLATE_ICE_CREAM = getBuilder(5, 0.6F).build();
        private static final FoodProperties COD_NIGIRI = getBuilder(4, 0.3F).build();
        private static final FoodProperties COD_SANDWICH = getBuilder(8, 0.6F).build();
        private static final FoodProperties FISH_AND_CHIPS = getBuilder(11, 0.7F).build();
        private static final FoodProperties FRIED_EGG = getBuilder(3, 0.6F).build();
        private static final FoodProperties HAM_CHEESE_SANDWICH = getBuilder(12, 0.6F).build();
        private static final FoodProperties HAM_SANDWICH = getBuilder(10, 0.7F).build();
        private static final FoodProperties MEAT_PIZZA = getBuilder(16, 0.625F).build();
        private static final FoodProperties MUESLI = getBuilder(11, 0.7F).build();
        private static final FoodProperties PAELLA = getBuilder(11, 0.7F).build();
        private static final FoodProperties PIZZA = getBuilder(12, 0.6F).build();
        private static final FoodProperties RICE = getBuilder(1, 0.3F).build();
        private static final FoodProperties SALMON_MAKI = getBuilder(10, 0.7F).build();
        private static final FoodProperties SALMON_NIGIRI = getBuilder(5, 0.3F).build();
        private static final FoodProperties SALMON_URAMAKI = getBuilder(8, 0.6F).build();
        private static final FoodProperties SWEET_BERRY_PIE = getBuilder(8, 0.3F).build();
        private static final FoodProperties YOGHURT = getBuilder(5, 0.6F).build();

        private static FoodProperties.Builder getBuilder(int nutrition, float saturationMod) {
            return (new FoodProperties.Builder()).nutrition(nutrition).saturationMod(saturationMod);
        }
    }

    public static class Blocks {
        private static final UnaryOperator<Block.Properties> IBP = UnaryOperator.identity();
        private static final DeferredRegister<Block> BLOCK_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

        public static final RegistryObject<CakeBlock> CHOCOLATE_CAKE = BLOCK_DEFERRED_REGISTER.register("chocolate_cake", () -> new CakeBlock(Block.Properties.of(Material.CAKE).strength(0.5F).sound(SoundType.WOOL)));
        public static final RegistryObject<RiceBlock> RICE = BLOCK_DEFERRED_REGISTER.register("rice", () -> new RiceBlock(Block.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    }

    public static class Items {
        private static final UnaryOperator<Item.Properties> IIP = UnaryOperator.identity();
        private static final DeferredRegister<Item> ITEM_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

        public static final RegistryObject<Item> APPLE_PIE = registerFood("apple_pie", Foods.APPLE_PIE, IIP);
        public static final RegistryObject<Item> BACON_EGG = registerFood("bacon_egg", Foods.BACON_EGG, IIP);
        public static final RegistryObject<Item> BEEF_BURGER = registerFood("beef_burger", Foods.BEEF_BURGER, IIP);
        public static final RegistryObject<Item> BEETROOT_BURGER = registerFood("beetroot_burger", Foods.BEETROOT_BURGER, IIP);
        public static final RegistryObject<BowlFoodItem> CARROT_POTATO_SOUP = registerSoup("carrot_potato_soup", Foods.CARROT_POTATO_SOUP, (p) -> p.stacksTo(1));
        public static final RegistryObject<Item> CHEESE = registerFood("cheese", Foods.CHEESE, IIP);
        public static final RegistryObject<Item> CHEESEBURGER = registerFood("cheeseburger", Foods.CHEESEBURGER, (p) -> p.rarity(Rarity.UNCOMMON));
        public static final RegistryObject<BlockItem> CHOCOLATE_CAKE = registerBlock("chocolate_cake", Blocks.CHOCOLATE_CAKE, (p) -> p.stacksTo(1));
        public static final RegistryObject<Item> CHOCOLATE_ICE_CREAM = registerFood("chocolate_ice_cream", Foods.CHOCOLATE_ICE_CREAM, IIP);
        public static final RegistryObject<Item> COD_NIGIRI = registerFood("cod_nigiri", Foods.COD_NIGIRI, IIP);
        public static final RegistryObject<Item> COD_SANDWICH = registerFood("cod_sandwich", Foods.COD_SANDWICH, IIP);
        public static final RegistryObject<Item> FISH_AND_CHIPS = registerFood("fish_and_chips", Foods.FISH_AND_CHIPS, IIP);
        public static final RegistryObject<Item> FRIED_EGG = registerFood("fried_egg", Foods.FRIED_EGG, IIP);
        public static final RegistryObject<Item> HAM_CHEESE_SANDWICH = registerFood("ham_cheese_sandwich", Foods.HAM_CHEESE_SANDWICH, (p) -> p.rarity(Rarity.UNCOMMON));
        public static final RegistryObject<Item> HAM_SANDWICH = registerFood("ham_sandwich", Foods.HAM_SANDWICH, IIP);
        public static final RegistryObject<Item> MEAT_PIZZA = registerFood("meat_pizza", Foods.MEAT_PIZZA, (p) -> p.rarity(Rarity.RARE));
        public static final RegistryObject<BowlFoodItem> MUESLI = registerSoup("muesli", Foods.MUESLI, IIP);
        public static final RegistryObject<Item> PAELLA = registerFood("paella", Foods.PAELLA, IIP);
        public static final RegistryObject<Item> PIZZA = registerFood("pizza", Foods.PIZZA, (p) -> p.rarity(Rarity.UNCOMMON));
        public static final RegistryObject<BlockItem> RICE = registerBlock("rice", Blocks.RICE, (p) -> p.food(Foods.RICE));
        public static final RegistryObject<Item> SALMON_MAKI = registerFood("salmon_maki", Foods.SALMON_MAKI, IIP);
        public static final RegistryObject<Item> SALMON_NIGIRI = registerFood("salmon_nigiri", Foods.SALMON_NIGIRI, IIP);
        public static final RegistryObject<Item> SALMON_URAMAKI = registerFood("salmon_uramaki", Foods.SALMON_URAMAKI, IIP);
        public static final RegistryObject<Item> SWEET_BERRY_PIE = registerFood("sweet_berry_pie", Foods.SWEET_BERRY_PIE, IIP);
        public static final RegistryObject<BowlFoodItem> YOGHURT = registerSoup("yoghurt", Foods.YOGHURT, IIP);

        private static RegistryObject<Item> registerFood(String name, FoodProperties food, UnaryOperator<Item.Properties> pMod) {
            return ITEM_DEFERRED_REGISTER.register(name, () -> new Item(pMod.apply(new Item.Properties()).food(food)));
        }
        private static RegistryObject<BowlFoodItem> registerSoup(String name, FoodProperties food, UnaryOperator<Item.Properties> pMod) {
            return ITEM_DEFERRED_REGISTER.register(name, () -> new BowlFoodItem(pMod.apply(new Item.Properties()).food(food)));
        }
        private static RegistryObject<BlockItem> registerBlock(String name, Supplier<? extends Block> block, UnaryOperator<Item.Properties> pMod) {
            return ITEM_DEFERRED_REGISTER.register(name, () -> new BlockItem(block.get(), pMod.apply(new Item.Properties())));
        }
    }

    public static class LootModifierSerializers {
        private static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZER_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MODID);

        public static final RegistryObject<Codec<ChestLootModifier>> CHEST_LOOT_MODIFIER = LOOT_MODIFIER_SERIALIZER_DEFERRED_REGISTER.register("chest_loot", ChestLootModifier.CODEC);
    }

    public void onCreativeModeTabBuildContents(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.FOOD_AND_DRINKS) {
            Items.ITEM_DEFERRED_REGISTER.getEntries().forEach(event::accept);
        }
    }
}
