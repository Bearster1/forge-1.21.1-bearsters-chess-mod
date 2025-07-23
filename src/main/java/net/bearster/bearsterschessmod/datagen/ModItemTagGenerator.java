package net.bearster.bearsterschessmod.datagen;

import net.bearster.bearsterschessmod.BearstersChessMod;
import net.bearster.bearsterschessmod.block.ModBlocks;
import net.bearster.bearsterschessmod.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture,
                               CompletableFuture<TagLookup<Block>> tagLookupCompletableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, completableFuture, tagLookupCompletableFuture, BearstersChessMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        tag(ModTags.Items.BEARSTERS_CHESS_ITEMS)
                .add(ModBlocks.BLACK_SQUARE.get().asItem())
                .add(ModBlocks.WHITE_SQUARE.get().asItem())
                .add(ModBlocks.KNIGHT.get().asItem())
                .add(ModBlocks.PAWN.get().asItem())
                .add(ModBlocks.ROOK.get().asItem())
                .add(ModBlocks.BISHOP.get().asItem())
                .add(ModBlocks.QUEEN.get().asItem())
                .add(ModBlocks.KING.get().asItem())
                .add(ModBlocks.MOVEABLE_SQUARE.get().asItem());

    }
}
