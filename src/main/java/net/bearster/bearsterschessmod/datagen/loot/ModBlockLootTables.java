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
        dropSelf(ModBlocks.KNIGHT.get());
        dropSelf(ModBlocks.PAWN.get());
        dropSelf(ModBlocks.ROOK.get());
        dropSelf(ModBlocks.BISHOP.get());
        dropSelf(ModBlocks.QUEEN.get());
        dropSelf(ModBlocks.KING.get());
        dropSelf(ModBlocks.OPTION.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
