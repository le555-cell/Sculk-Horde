package com.github.sculkhorde.core;

import com.github.sculkhorde.common.blockentity.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityRegistry {

    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SculkHorde.MOD_ID);

    public static RegistryObject<BlockEntityType<SculkMassBlockEntity>> SCULK_MASS_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("sculk_mass_block_entity", () -> BlockEntityType.Builder.of(
                    SculkMassBlockEntity::new, BlockRegistry.SCULK_MASS.get()).build(null));

    public static RegistryObject<BlockEntityType<SculkNodeBlockEntity>> SCULK_NODE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("sculk_node_block_entity", () -> BlockEntityType.Builder.of(
                    SculkNodeBlockEntity::new, BlockRegistry.SCULK_NODE_BLOCK.get()).build(null));

    public static RegistryObject<BlockEntityType<SculkBeeNestBlockEntity>> SCULK_BEE_NEST_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("sculk_bee_nest_block_entity", () -> BlockEntityType.Builder.of(
                    SculkBeeNestBlockEntity::new, BlockRegistry.SCULK_BEE_NEST_BLOCK.get()).build(null));

    public static RegistryObject<BlockEntityType<SculkBeeNestCellBlockEntity>> SCULK_BEE_NEST_CELL_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("sculk_bee_nest_cell_block_entity", () -> BlockEntityType.Builder.of(
                    SculkBeeNestCellBlockEntity::new, BlockRegistry.SCULK_BEE_NEST_CELL_BLOCK.get()).build(null));

    public static RegistryObject<BlockEntityType<SculkSummonerBlockEntity>> SCULK_SUMMONER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("sculk_summoner_block_entity", () -> BlockEntityType.Builder.of(
                    SculkSummonerBlockEntity::new, BlockRegistry.SCULK_SUMMONER_BLOCK.get()).build(null));

    public static RegistryObject<BlockEntityType<SculkLivingRockRootBlockEntity>> SCULK_LIVING_ROCK_ROOT_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("sculk_living_rock_root_block_entity", () -> BlockEntityType.Builder.of(
                    SculkLivingRockRootBlockEntity::new, BlockRegistry.SCULK_LIVING_ROCK_ROOT_BLOCK.get()).build(null));

    public static RegistryObject<BlockEntityType<DevStructureTesterBlockEntity>> DEV_STRUCTURE_TESTER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("dev_structure_tester_block_entity", () -> BlockEntityType.Builder.of(
                    DevStructureTesterBlockEntity::new, BlockRegistry.DEV_STRUCTURE_TESTER_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
