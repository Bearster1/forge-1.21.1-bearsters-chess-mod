package net.bearster.bearsterschessmod.datagen;

import net.bearster.bearsterschessmod.BearstersChessMod;
import net.bearster.bearsterschessmod.block.ModBlocks;
import net.bearster.bearsterschessmod.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, BearstersChessMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.WHITE_SQUARE.get())
                .add(ModBlocks.BLACK_SQUARE.get())
                .add(ModBlocks.KNIGHT.get())
                .add(ModBlocks.ROOK.get())
                .add(ModBlocks.BISHOP.get())
                .add(ModBlocks.QUEEN.get())
                .add(ModBlocks.KING.get())
                .add(ModBlocks.PAWN.get());

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.WHITE_SQUARE.get())
                .add(ModBlocks.BLACK_SQUARE.get())
                .add(ModBlocks.KNIGHT.get())
                .add(ModBlocks.PAWN.get())
                .add(ModBlocks.ROOK.get())
                .add(ModBlocks.BISHOP.get())
                .add(ModBlocks.QUEEN.get())
                .add(ModBlocks.KING.get());

        this.tag(ModTags.Blocks.IS_CHESS_BOARD)
                .add(ModBlocks.WHITE_SQUARE.get())
                .add(ModBlocks.BLACK_SQUARE.get());

    }
}
