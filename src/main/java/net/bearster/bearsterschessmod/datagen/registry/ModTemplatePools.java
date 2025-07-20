package net.bearster.bearsterschessmod.datagen.registry;

import com.mojang.datafixers.util.Pair;
import net.bearster.bearsterschessmod.BearstersChessMod;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

import java.util.List;

public interface ModTemplatePools {
    ResourceKey<StructureTemplatePool> CHESS_BOARD = create("chess_board");

    private static ResourceKey<StructureTemplatePool> create(String name) {
        return ResourceKey.create(Registries.TEMPLATE_POOL, BearstersChessMod.loc(name));
    }

    static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);

        context.register(CHESS_BOARD, new StructureTemplatePool(
                templatePools.getOrThrow(Pools.EMPTY),
                List.of(
                        Pair.of(StructurePoolElement.single("bearsterschessmod:chess_board"), 1)
                ),
                StructureTemplatePool.Projection.RIGID
        ));
    }
}
