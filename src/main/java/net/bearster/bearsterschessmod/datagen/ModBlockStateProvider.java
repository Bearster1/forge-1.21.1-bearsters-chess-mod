package net.bearster.bearsterschessmod.datagen;

import net.bearster.bearsterschessmod.BearstersChessMod;
import net.bearster.bearsterschessmod.block.ModBlocks;
import net.bearster.bearsterschessmod.block.custom.*;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output,  ExistingFileHelper exFileHelper) {
        super(output, BearstersChessMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.WHITE_SQUARE);
        blockWithItem(ModBlocks.BLACK_SQUARE);
        blockWithItem(ModBlocks.OPTION);

        rotatedBlockWithChessPieceModel(
                ModBlocks.PAWN.get(),
                models().getExistingFile(BearstersChessMod.loc("block/white_pawn")),
                models().getExistingFile(BearstersChessMod.loc("block/black_pawn")),
                PawnBlock.COLOUR);

        rotatedBlockWithChessPieceModel(
                ModBlocks.KNIGHT.get(),
                models().getExistingFile(BearstersChessMod.loc("block/white_knight")),
                models().getExistingFile(BearstersChessMod.loc("block/black_knight")),
                KnightBlock.COLOUR);

        rotatedBlockWithChessPieceModel(
                ModBlocks.ROOK.get(),
                models().getExistingFile(BearstersChessMod.loc("block/white_rook")),
                models().getExistingFile(BearstersChessMod.loc("block/black_rook")),
                RookBlock.COLOUR);

        rotatedBlockWithChessPieceModel(
                ModBlocks.BISHOP.get(),
                models().getExistingFile(BearstersChessMod.loc("block/white_bishop")),
                models().getExistingFile(BearstersChessMod.loc("block/black_bishop")),
                BishopBlock.COLOUR);

        rotatedBlockWithChessPieceModel(
                ModBlocks.QUEEN.get(),
                models().getExistingFile(BearstersChessMod.loc("block/white_queen")),
                models().getExistingFile(BearstersChessMod.loc("block/black_queen")),
                QueenBlock.COLOUR);

        rotatedBlockWithChessPieceModel(
                ModBlocks.KING.get(),
                models().getExistingFile(BearstersChessMod.loc("block/white_king")),
                models().getExistingFile(BearstersChessMod.loc("block/black_king")),
                KingBlock.COLOUR);

        simpleBlockWithItem(ModBlocks.MOVEABLE_SQUARE.get(), models().getExistingFile(BearstersChessMod.loc("block/moveable_square")));
        simpleBlockWithItem(ModBlocks.ATTACKABLE_SQUARE.get(), models().getExistingFile(BearstersChessMod.loc("block/attackable_square")));
        simpleBlockWithItem(ModBlocks.PROMOTION_BLOCK.get(), models().getExistingFile(BearstersChessMod.loc("block/promotion_block")));
        
    }
    
    private void rotatedBlockWithChessPieceModel(Block block, ModelFile whiteModel, ModelFile blackModel, BooleanProperty booleanProperty) {
        getVariantBuilder(block).forAllStates(state -> {
            if(state.getValue(booleanProperty)) {
                Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);

                int yRot = switch (facing) {
                    case SOUTH -> 180;
                    case WEST -> 270;
                    case EAST -> 90;
                    default -> 0;
                };

                return ConfiguredModel.builder()
                        .modelFile(whiteModel)
                        .rotationY(yRot)
                        .build();
            } else {
                Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);

                int yRot = switch (facing) {
                    case SOUTH -> 180;
                    case WEST -> 270;
                    case EAST -> 90;
                    default -> 0;
                };

                return ConfiguredModel.builder()
                        .modelFile(blackModel)
                        .rotationY(yRot)
                        .build();
                }

        });
        simpleBlockItem(block, whiteModel);
    }

    private void rotatedBlockWithModel(Block block, ModelFile model) {
        getVariantBuilder(block)
                .forAllStates(state -> {
                    Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
                    int yRot = switch (facing) {
                        case SOUTH -> 180;
                        case WEST -> 270;
                        case EAST -> 90;
                        default -> 0;
                    };

                    return ConfiguredModel.builder()
                            .modelFile(model)
                            .rotationY(yRot)
                            .build();
                });

        simpleBlockItem(block, model);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
