package net.bearster.bearsterschessmod.util;

import net.bearster.bearsterschessmod.BearstersChessMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;

public class ModTags {

    public static class Items {

        public static final TagKey<Item> BEARSTERS_CHESS_ITEMS = createTag("bearsters_chess_items");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(BearstersChessMod.loc(name));
        }

    }

    public static class Blocks {

        public static final TagKey<Block> IS_CHESS_BOARD = createTag("is_chess_board");
        public static final TagKey<Block> IS_CHESS_PIECE = createTag("is_chess_piece");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(BearstersChessMod.loc(name));
        }
    }

    public class Biomes {
        public static final TagKey<Biome> HAS_CHESS_BOARD = createTag("has_structure/chess_board");

        private static TagKey<Biome> createTag(String name) {
            return BiomeTags.create(BearstersChessMod.loc(name));
        }

    }

}
