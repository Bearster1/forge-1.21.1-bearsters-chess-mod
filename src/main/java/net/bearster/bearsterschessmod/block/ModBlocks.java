package net.bearster.bearsterschessmod.block;

import net.bearster.bearsterschessmod.BearstersChessMod;
import net.bearster.bearsterschessmod.block.custom.*;
import net.bearster.bearsterschessmod.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, BearstersChessMod.MOD_ID);

    public static final RegistryObject<Block> WHITE_SQUARE = registerBlock("white_square",
            ()-> new Block(BlockBehaviour.Properties.of().strength(1f)));

    public static final RegistryObject<Block> BLACK_SQUARE = registerBlock("black_square",
            ()-> new Block(BlockBehaviour.Properties.of().strength(1f)));

    public static final RegistryObject<Block> KNIGHT = registerBlock("knight",
            ()-> new KnightBlock(BlockBehaviour.Properties.of().strength(1f)));

    public static final RegistryObject<Block> PAWN = registerBlock("pawn",
            ()-> new PawnBlock(BlockBehaviour.Properties.of().strength(1f)));

    public static final RegistryObject<Block> ROOK = registerBlock("rook",
            ()-> new RookBlock(BlockBehaviour.Properties.of().strength(1f)));

    public static final RegistryObject<Block> BISHOP = registerBlock("bishop",
            ()-> new BishopBlock(BlockBehaviour.Properties.of().strength(1f)));

    public static final RegistryObject<Block> QUEEN = registerBlock("queen",
            ()-> new QueenBlock(BlockBehaviour.Properties.of().strength(1f)));

    public static final RegistryObject<Block> KING = registerBlock("king",
            ()-> new KingBlock(BlockBehaviour.Properties.of().strength(1f)));

    public static final RegistryObject<Block> MOVEABLE_SQUARE = registerBlock("moveable_square",
            ()-> new MoveableSquareBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK).noOcclusion()));





    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
