package net.bearster.bearsterschessmod.block.custom;

import net.bearster.bearsterschessmod.BearstersChessMod;
import net.bearster.bearsterschessmod.block.ModBlocks;
import net.bearster.bearsterschessmod.block.entity.custom.*;
import net.bearster.bearsterschessmod.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AttackableSquareBlock extends Block implements EntityBlock {
    public static final VoxelShape SHAPE = Block.box(4, 4, 4, 12, 12, 12);

    public AttackableSquareBlock(Properties properties) {
        super(properties);
    }


    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new AttackableSquareBlockEntity(pPos, pState);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if (!pLevel.isClientSide) {
            BearstersChessMod.LOGGER.info("1");

            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof AttackableSquareBlockEntity attackableSquareBlockEntity) {
                BlockPos storedPos = attackableSquareBlockEntity.getPiecePosition();
                BlockState newBlockstate = pLevel.getBlockState(storedPos);

                BlockEntity takeKing = pLevel.getBlockEntity(pPos.offset(0,-1,0));
                if (takeKing instanceof ChessPieceBlockEntity chessPieceBlockEntity) {
                    if (pLevel.getBlockState(pPos.offset(0,-1,0)).is(ModBlocks.KING.get())) {
                        if (newBlockstate.getValue(ChessPieceBlock.COLOUR)) {
                            pPlayer.sendSystemMessage(Component.translatable("message.bearsterschessmod.win_game_white"));
                        } else {
                            pPlayer.sendSystemMessage(Component.translatable("message.bearsterschessmod.win_game_black"));
                        }
                    }
                }

                BearstersChessMod.LOGGER.info("2");

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

                BearstersChessMod.LOGGER.info("3");

                if (attackableSquareBlockEntity.isEnPassant()) {
                    pLevel.setBlock(pPos.offset(0, -1, 0), Blocks.AIR.defaultBlockState(), 3);
                    pLevel.setBlock(attackableSquareBlockEntity.getEnPassantPosition(), newBlockstate,3);
                } else {
                    pLevel.setBlock(pPos.offset(0, -1, 0), newBlockstate, 3);
                }

                BearstersChessMod.LOGGER.info("4");


                BlockEntity blockEntity2 = pLevel.getBlockEntity(pPos.offset(0, -1, 0));
                if (attackableSquareBlockEntity.isEnPassant()) {
                    blockEntity2 = pLevel.getBlockEntity(attackableSquareBlockEntity.getEnPassantPosition());
                }

                if (blockEntity2 instanceof ChessPieceBlockEntity chessPieceBlockEntity) {
                    chessPieceBlockEntity.setHasMoved(true);

                    if (attackableSquareBlockEntity.isEnPassant()) {
                        chessPieceBlockEntity.setLastPieceMoved(pLevel.getBlockState(attackableSquareBlockEntity.getEnPassantPosition()).getBlock().toString());
                        chessPieceBlockEntity.setLastPieceMovedColour(pLevel.getBlockState(attackableSquareBlockEntity.getEnPassantPosition()).getValue(ChessPieceBlock.COLOUR));
                        if (chessPieceBlockEntity.getLastDoublePiecePos() == pPos && attackableSquareBlockEntity.isEnPassant()) {
                            chessPieceBlockEntity.setLastDoublePiecePos(BlockPos.ZERO);
                        }
                    } else {
                        chessPieceBlockEntity.setLastPieceMoved(pLevel.getBlockState(pPos.offset(0, -1, 0)).getBlock().toString());
                        chessPieceBlockEntity.setLastPieceMovedColour(pLevel.getBlockState(pPos.offset(0, -1, 0)).getValue(ChessPieceBlock.COLOUR));
                        if (chessPieceBlockEntity.getLastDoublePiecePos() == pPos && attackableSquareBlockEntity.isEnPassant()) {
                            chessPieceBlockEntity.setLastDoublePiecePos(BlockPos.ZERO);
                        }
                    }

                    BearstersChessMod.LOGGER.info("5");

                    BlockState blockState = pLevel.getBlockState(pPos.offset(0,-1,0));
                    if (blockState.is(ModBlocks.PAWN.get())) {
                        chessPieceBlockEntity.setPawnHasDoubleMoved(true);

                        BearstersChessMod.LOGGER.info("6");

                        Direction facing = blockState.getValue(PawnBlock.FACING);

                        if (!pLevel.getBlockState(pPos.offset(0,-2,0).relative(facing)).is(ModTags.Blocks.IS_CHESS_BOARD)) {
                            pLevel.setBlock(pPos.offset(0,-1,0),
                                    ModBlocks.QUEEN.get().defaultBlockState()
                                            .setValue(QueenBlock.COLOUR, newBlockstate.getValue(PawnBlock.COLOUR))
                                            .setValue(QueenBlock.FACING,newBlockstate.getValue(PawnBlock.FACING))
                                    ,3);
                        }
                    }

                    BearstersChessMod.LOGGER.info("7");

                    chessPieceBlockEntity.setWhosTurnIsIt(!chessPieceBlockEntity.getWhosTurnIsIt());
                }

                BearstersChessMod.LOGGER.info("8");


                pLevel.setBlockAndUpdate(storedPos, Blocks.AIR.defaultBlockState());
                pLevel.setBlockAndUpdate(pPos, Blocks.AIR.defaultBlockState());

                BearstersChessMod.LOGGER.info("9");

            }

        }
        return InteractionResult.SUCCESS;
    }

}
