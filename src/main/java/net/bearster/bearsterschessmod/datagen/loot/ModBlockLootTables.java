package net.bearster.bearsterschessmod.datagen.loot;

import net.bearster.bearsterschessmod.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables(HolderLookup.Provider pRegistries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), pRegistries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.WHITE_SQUARE.get());
        dropSelf(ModBlocks.BLACK_SQUARE.get());
        dropSelf(ModBlocks.WHITE_KNIGHT.get());
        dropSelf(ModBlocks.BLACK_KNIGHT.get());

        /*
        dropSelf(ModBlocks.WHITE_PAWN.get());
        dropSelf(ModBlocks.BLACK_PAWN.get());

         */
        dropSelf(ModBlocks.PAWN.get());
        dropSelf(ModBlocks.WHITE_ROOK.get());
        dropSelf(ModBlocks.BLACK_ROOK.get());
        dropSelf(ModBlocks.WHITE_BISHOP.get());
        dropSelf(ModBlocks.BLACK_BISHOP.get());
        dropSelf(ModBlocks.WHITE_QUEEN.get());
        dropSelf(ModBlocks.BLACK_QUEEN.get());
        dropSelf(ModBlocks.WHITE_KING.get());
        dropSelf(ModBlocks.BLACK_KING.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
