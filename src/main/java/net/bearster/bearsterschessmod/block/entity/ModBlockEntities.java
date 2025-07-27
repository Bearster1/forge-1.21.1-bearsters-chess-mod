package net.bearster.bearsterschessmod.block.entity;

import net.bearster.bearsterschessmod.BearstersChessMod;
import net.bearster.bearsterschessmod.block.ModBlocks;
import net.bearster.bearsterschessmod.block.entity.custom.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BearstersChessMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<MoveableSquareBlockEntity>> MOVEABLE_SQUARE_BE =
            BLOCK_ENTITIES.register("moveable_square_be", () -> BlockEntityType.Builder.of(
                    MoveableSquareBlockEntity::new, ModBlocks.MOVEABLE_SQUARE.get()).build(null));

    public static final RegistryObject<BlockEntityType<AttackableSquareBlockEntity>> ATTACKABLE_SQUARE_BE =
            BLOCK_ENTITIES.register("attackable_square_be", () -> BlockEntityType.Builder.of(
                    AttackableSquareBlockEntity::new, ModBlocks.ATTACKABLE_SQUARE.get()).build(null));

    public static final RegistryObject<BlockEntityType<PawnBlockEntity>> PAWN_BE =
            BLOCK_ENTITIES.register("pawn_be", () -> BlockEntityType.Builder.of(
                    PawnBlockEntity::new, ModBlocks.PAWN.get()).build(null));

    public static final RegistryObject<BlockEntityType<BishopBlockEntity>> BISHOP_BE =
            BLOCK_ENTITIES.register("bishop_be", () -> BlockEntityType.Builder.of(
                    BishopBlockEntity::new, ModBlocks.BISHOP.get()).build(null));

    public static final RegistryObject<BlockEntityType<QueenBlockEntity>> QUEEN_BE =
            BLOCK_ENTITIES.register("queen_be", () -> BlockEntityType.Builder.of(
                    QueenBlockEntity::new, ModBlocks.QUEEN.get()).build(null));

    public static final RegistryObject<BlockEntityType<RookBlockEntity>> ROOK_BE =
            BLOCK_ENTITIES.register("rook_be", () -> BlockEntityType.Builder.of(
                    RookBlockEntity::new, ModBlocks.ROOK.get()).build(null));

    public static final RegistryObject<BlockEntityType<KnightBlockEntity>> KNIGHT_BE =
            BLOCK_ENTITIES.register("knight_be", () -> BlockEntityType.Builder.of(
                    KnightBlockEntity::new, ModBlocks.KNIGHT.get()).build(null));

    public static final RegistryObject<BlockEntityType<KingBlockEntity>> KING_BE =
            BLOCK_ENTITIES.register("king_be", () -> BlockEntityType.Builder.of(
                    KingBlockEntity::new, ModBlocks.KING.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
