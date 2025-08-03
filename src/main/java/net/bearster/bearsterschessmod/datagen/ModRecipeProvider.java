package net.bearster.bearsterschessmod.datagen;

import net.bearster.bearsterschessmod.block.ModBlocks;
import net.bearster.bearsterschessmod.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.WHITE_SQUARE.get())
                .pattern("PPP")
                .pattern("PPP")
                .pattern("PPP")
                .define('P', Items.OAK_PLANKS)
                .unlockedBy("has_oak_planks",has(Blocks.OAK_PLANKS)).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.BLACK_SQUARE.get())
                .pattern("PPP")
                .pattern("PPP")
                .pattern("PPP")
                .define('P', Items.DARK_OAK_PLANKS)
                .unlockedBy("has_dark_oak_planks",has(Blocks.DARK_OAK_PLANKS)).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.OPTION.get())
                .pattern("WWB")
                .pattern("WRB")
                .pattern("WBB")
                .define('R', Items.RED_CONCRETE)
                .define('W',ModBlocks.WHITE_SQUARE.get())
                .define('B',ModBlocks.BLACK_SQUARE.get())
                .unlockedBy("has_white_square",has(ModBlocks.WHITE_SQUARE.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.PAWN.get())
                .pattern(" W ")
                .pattern(" W ")
                .pattern("WWW")
                .define('W', ModBlocks.WHITE_SQUARE.get())
                .unlockedBy("has_white_square",has(ModBlocks.WHITE_SQUARE.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.KNIGHT.get())
                .pattern("WW ")
                .pattern(" W ")
                .pattern("WWW")
                .define('W', ModBlocks.WHITE_SQUARE.get())
                .unlockedBy("has_white_square",has(ModBlocks.WHITE_SQUARE.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.BISHOP.get())
                .pattern("BW ")
                .pattern(" W ")
                .pattern("WWW")
                .define('W', ModBlocks.WHITE_SQUARE.get())
                .define('B',ModBlocks.BLACK_SQUARE.get())
                .unlockedBy("has_white_square",has(ModBlocks.WHITE_SQUARE.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ROOK.get())
                .pattern("WWW")
                .pattern(" W ")
                .pattern("WWW")
                .define('W', ModBlocks.WHITE_SQUARE.get())
                .unlockedBy("has_white_square",has(ModBlocks.WHITE_SQUARE.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.QUEEN.get())
                .pattern(" R ")
                .pattern(" B ")
                .pattern("WWW")
                .define('W', ModBlocks.WHITE_SQUARE.get())
                .define('R',ModBlocks.ROOK.get())
                .define('B',ModBlocks.BISHOP.get())
                .unlockedBy("has_white_square",has(ModBlocks.WHITE_SQUARE.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.KING.get())
                .pattern("KPO")
                .pattern("RQB")
                .pattern("WWW")
                .define('W', ModBlocks.WHITE_SQUARE.get())
                .define('K',ModBlocks.KNIGHT.get())
                .define('P',ModBlocks.PAWN.get())
                .define('O',ModBlocks.OPTION.get())
                .define('R',ModBlocks.ROOK.get())
                .define('Q',ModBlocks.QUEEN.get())
                .define('B',ModBlocks.BISHOP.get())
                .unlockedBy("has_white_square",has(ModBlocks.WHITE_SQUARE.get())).save(pRecipeOutput);

    }
}
