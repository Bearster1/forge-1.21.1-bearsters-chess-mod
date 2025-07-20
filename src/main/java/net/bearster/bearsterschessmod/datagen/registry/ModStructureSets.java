package net.bearster.bearsterschessmod.datagen.registry;

import net.bearster.bearsterschessmod.BearstersChessMod;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

public interface ModStructureSets {
    ResourceKey<StructureSet> CHESS_BOARD = create("chess_board");

    static ResourceKey<StructureSet> create(String name) {
        return ResourceKey.create(Registries.STRUCTURE_SET, BearstersChessMod.loc(name));
    }

    static void bootstrap(BootstrapContext<StructureSet> context) {
        HolderGetter<Structure> structures = context.lookup(Registries.STRUCTURE);

        context.register(CHESS_BOARD, new StructureSet(
                structures.getOrThrow(ModStructures.CHESS_BOARD),
                new RandomSpreadStructurePlacement(
                        12, 4, RandomSpreadType.LINEAR, 654987123
                ))
        );
    }
}
