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

    public static final RegistryObject<BlockEntityType<ChessPieceBlockEntity>> CHESS_PIECE_BE =
            BLOCK_ENTITIES.register("chess_piece_be", () -> BlockEntityType.Builder.of(
                    ChessPieceBlockEntity::new,
                    ModBlocks.PAWN.get(), ModBlocks.KNIGHT.get(), ModBlocks.BISHOP.get(),
                    ModBlocks.ROOK.get(), ModBlocks.QUEEN.get(), ModBlocks.KING.get(),
                    ModBlocks.OPTION.get(), ModBlocks.PROMOTION_BLOCK.get()
            ).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
