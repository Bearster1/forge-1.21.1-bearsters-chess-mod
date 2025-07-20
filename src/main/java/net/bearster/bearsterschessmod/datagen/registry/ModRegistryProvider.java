package net.bearster.bearsterschessmod.datagen.registry;

import net.bearster.bearsterschessmod.BearstersChessMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModRegistryProvider extends DatapackBuiltinEntriesProvider {
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.TEMPLATE_POOL, ModTemplatePools::bootstrap)
            .add(Registries.STRUCTURE_SET, ModStructureSets::bootstrap)
            .add(Registries.STRUCTURE, ModStructures::bootstrap);

    public ModRegistryProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(BearstersChessMod.MOD_ID));
    }
}
