package net.bearster.bearsterschessmod.block.custom;

import net.bearster.bearsterschessmod.BearstersChessMod;
import net.bearster.bearsterschessmod.block.ModBlocks;
import net.bearster.bearsterschessmod.block.entity.custom.*;
import net.bearster.bearsterschessmod.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MoveableSquareBlock extends Block implements EntityBlock {
    public static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 1, 15);

    public MoveableSquareBlock(Properties properties) {
        super(properties);
    }


    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new MoveableSquareBlockEntity(pPos, pState);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if (!pLevel.isClientSide) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof MoveableSquareBlockEntity moveableSquareBlockEntity) {
                BlockPos storedPos = moveableSquareBlockEntity.getPiecePosition();
                BlockState newBlockstate = pLevel.getBlockState(storedPos);

                BlockEntity typeChessPiece = pLevel.getBlockEntity(storedPos);
                if (typeChessPiece instanceof ChessPieceBlockEntity chessPieceBlockEntity) {
                    List<Integer> xList = chessPieceBlockEntity.getXList();
                    List<Integer> yList = chessPieceBlockEntity.getYList();
                    List<Integer> zList = chessPieceBlockEntity.getZList();

                    BearstersChessMod.LOGGER.info("x: "+xList);
                    BearstersChessMod.LOGGER.info("y: "+yList);
                    BearstersChessMod.LOGGER.info("z: "+zList);

                    for (int i = 0; i <
                            (xList.size() + yList.size() + zList.size()) / 3;
                         i++) {
                        pLevel.setBlockAndUpdate(new BlockPos(xList.get(i),yList.get(i),zList.get(i)), Blocks.AIR.defaultBlockState());
                    }

                    chessPieceBlockEntity.resetList();
                }

                pLevel.setBlock(pPos, newBlockstate, 3);

                BlockEntity blockEntity2 = pLevel.getBlockEntity(pPos);
                if (blockEntity2 instanceof ChessPieceBlockEntity chessPieceBlockEntity) {
                    chessPieceBlockEntity.setLastPieceMoved(pLevel.getBlockState(pPos).getBlock().toString());
                    chessPieceBlockEntity.setLastPieceMovedColour(pLevel.getBlockState(pPos).getValue(ChessPieceBlock.COLOUR));

                    BlockState blockState = pLevel.getBlockState(pPos);
                    if (blockState.is(ModBlocks.PAWN.get())) {
                        if (moveableSquareBlockEntity.getDoubleMovedWithPawnLast()) {
                            chessPieceBlockEntity.setLastPieceMovedWasDouble(true);
                        } else {
                            chessPieceBlockEntity.setLastPieceMovedWasDouble(false);
                        }

                        chessPieceBlockEntity.setPawnHasDoubleMoved(true);
                        Direction facing = blockState.getValue(PawnBlock.FACING);

                        if (!pLevel.getBlockState(pPos.offset(0,-1,0).relative(facing)).is(ModTags.Blocks.IS_CHESS_BOARD)) {
                            pLevel.setBlock(pPos,
                                    ModBlocks.QUEEN.get().defaultBlockState()
                                            .setValue(QueenBlock.COLOUR, newBlockstate.getValue(PawnBlock.COLOUR))
                                            .setValue(QueenBlock.FACING,newBlockstate.getValue(PawnBlock.FACING))
                                    ,3);
                        }
                    }

                    chessPieceBlockEntity.setWhosTurnIsIt(!chessPieceBlockEntity.getWhosTurnIsIt());
                }

                pLevel.setBlockAndUpdate(storedPos, Blocks.AIR.defaultBlockState());

            }

        }
        return InteractionResult.SUCCESS;
    }
}
