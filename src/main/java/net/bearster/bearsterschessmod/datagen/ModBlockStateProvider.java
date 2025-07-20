package net.bearster.bearsterschessmod.datagen;

import net.bearster.bearsterschessmod.BearstersChessMod;
import net.bearster.bearsterschessmod.block.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
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
        rotatedBlockWithChessPieceModel(ModBlocks.WHITE_KNIGHT.get(), models().getExistingFile(BearstersChessMod.loc("block/white_knight")));
        rotatedBlockWithChessPieceModel(ModBlocks.BLACK_KNIGHT.get(), models().getExistingFile(BearstersChessMod.loc("block/black_knight")));
    //    rotatedBlockWithChessPieceModel(ModBlocks.WHITE_PAWN.get(), models().getExistingFile(BearstersChessMod.loc("block/white_pawn")));
    //    rotatedBlockWithChessPieceModel(ModBlocks.BLACK_PAWN.get(), models().getExistingFile(BearstersChessMod.loc("block/black_pawn")));
        rotatedBlockWithChessPieceModel(ModBlocks.WHITE_ROOK.get(), models().getExistingFile(BearstersChessMod.loc("block/white_rook")));
        rotatedBlockWithChessPieceModel(ModBlocks.BLACK_ROOK.get(), models().getExistingFile(BearstersChessMod.loc("block/black_rook")));
        rotatedBlockWithChessPieceModel(ModBlocks.WHITE_BISHOP.get(), models().getExistingFile(BearstersChessMod.loc("block/white_bishop")));
        rotatedBlockWithChessPieceModel(ModBlocks.BLACK_BISHOP.get(), models().getExistingFile(BearstersChessMod.loc("block/black_bishop")));
        rotatedBlockWithChessPieceModel(ModBlocks.WHITE_QUEEN.get(), models().getExistingFile(BearstersChessMod.loc("block/white_queen")));
        rotatedBlockWithChessPieceModel(ModBlocks.BLACK_QUEEN.get(), models().getExistingFile(BearstersChessMod.loc("block/black_queen")));
        rotatedBlockWithChessPieceModel(ModBlocks.WHITE_KING.get(), models().getExistingFile(BearstersChessMod.loc("block/white_king")));
        rotatedBlockWithChessPieceModel(ModBlocks.BLACK_KING.get(), models().getExistingFile(BearstersChessMod.loc("block/black_king")));
        simpleBlockWithItem(ModBlocks.MOVEABLE_SQUARE.get(), models().getExistingFile(BearstersChessMod.loc("block/moveable_square")));
    }

    private void rotatedBlockWithChessPieceModel(Block block, ModelFile model) {
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
