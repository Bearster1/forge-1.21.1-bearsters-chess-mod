package net.bearster.bearsterschessmod.datagen;

import net.bearster.bearsterschessmod.BearstersChessMod;
import net.bearster.bearsterschessmod.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBiomesTagsProvider extends TagsProvider<Biome> {
    protected ModBiomesTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, Registries.BIOME, pLookupProvider, BearstersChessMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        this.tag(BiomeTags.IS_OCEAN)
                .addTag(BiomeTags.IS_RIVER);

        this.tag(ModTags.Biomes.HAS_CHESS_BOARD)
                .addTag(BiomeTags.IS_OVERWORLD)
                .remove(BiomeTags.IS_OCEAN);

    }
}
