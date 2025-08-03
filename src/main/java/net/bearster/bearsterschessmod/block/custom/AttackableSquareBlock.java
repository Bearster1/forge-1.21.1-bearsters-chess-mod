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

import static net.bearster.bearsterschessmod.block.custom.ChessPieceBlock.COLOUR;
import static net.bearster.bearsterschessmod.block.custom.RotationalBlock.FACING;

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

            BlockState promotionBlock = pLevel.getBlockState(pPos);
            BlockEntity promotionBE = pLevel.getBlockEntity(pPos);


            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof AttackableSquareBlockEntity attackableSquareBlockEntity) {
                BlockPos storedPos = attackableSquareBlockEntity.getPiecePosition();
                BlockState newBlockstate = pLevel.getBlockState(storedPos);

                if (newBlockstate.is(ModBlocks.PAWN.get())) {

                    Direction facing = newBlockstate.getValue(PawnBlock.FACING);

                    if (pLevel.getBlockState(pPos.relative(facing).offset(0,-1,0)).is(ModBlocks.PROMOTION_BLOCK.get())) {
                        promotionBlock = pLevel.getBlockState(pPos.relative(facing).offset(0,-1,0));
                        promotionBE = pLevel.getBlockEntity(pPos.relative(facing).offset(0,-1,0));

                        BearstersChessMod.LOGGER.info("OK TESTING "+promotionBlock+" "+promotionBE);
                    }
                }

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


                if (attackableSquareBlockEntity.isEnPassant()) {
                    pLevel.setBlock(pPos.offset(0, -1, 0), Blocks.AIR.defaultBlockState(), 3);
                    pLevel.setBlock(attackableSquareBlockEntity.getEnPassantPosition(), newBlockstate,3);
                } else {
                    pLevel.setBlock(pPos.offset(0, -1, 0), newBlockstate, 3);
                }



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


                    BlockState blockState = pLevel.getBlockState(pPos.offset(0,-1,0));
                    if (blockState.is(ModBlocks.PAWN.get())) {
                        chessPieceBlockEntity.setPawnHasDoubleMoved(true);


                        Direction facing = blockState.getValue(PawnBlock.FACING);

                        if (promotionBlock.is(ModBlocks.PROMOTION_BLOCK.get())) {

                            if (promotionBE instanceof ChessPieceBlockEntity chessPieceBlockEntity1) {

                                BearstersChessMod.LOGGER.info("2 "+chessPieceBlockEntity1.getPieceToPromoteTo());

                                if (chessPieceBlockEntity1.getPieceToPromoteTo().equals(ModBlocks.KNIGHT.get().toString())) {
                                    pLevel.setBlock(pPos.offset(0,-1,0),
                                            ModBlocks.KNIGHT.get().defaultBlockState()
                                                    .setValue(COLOUR, promotionBlock.getValue(COLOUR))
                                                    .setValue(KnightBlock.FACING, promotionBlock.getValue(FACING))
                                            , 3);
                                } else if (chessPieceBlockEntity1.getPieceToPromoteTo().equals(ModBlocks.BISHOP.get().toString())) {
                                    pLevel.setBlock(pPos.offset(0,-1,0),
                                            ModBlocks.BISHOP.get().defaultBlockState()
                                                    .setValue(COLOUR, promotionBlock.getValue(COLOUR))
                                                    .setValue(KnightBlock.FACING, promotionBlock.getValue(FACING))
                                            , 3);
                                } else if (chessPieceBlockEntity1.getPieceToPromoteTo().equals(ModBlocks.ROOK.get().toString())) {
                                    pLevel.setBlock(pPos.offset(0,-1,0),
                                            ModBlocks.ROOK.get().defaultBlockState()
                                                    .setValue(COLOUR, promotionBlock.getValue(COLOUR))
                                                    .setValue(KnightBlock.FACING, promotionBlock.getValue(FACING))
                                            , 3);
                                } else if (chessPieceBlockEntity1.getPieceToPromoteTo().equals(ModBlocks.QUEEN.get().toString())) {
                                    pLevel.setBlock(pPos.offset(0,-1,0),
                                            ModBlocks.QUEEN.get().defaultBlockState()
                                                    .setValue(COLOUR, promotionBlock.getValue(COLOUR))
                                                    .setValue(KnightBlock.FACING, promotionBlock.getValue(FACING))
                                            , 3);
                                }

                            }
                        }
                    }


                    chessPieceBlockEntity.setWhosTurnIsIt(!chessPieceBlockEntity.getWhosTurnIsIt());
                }



                pLevel.setBlockAndUpdate(storedPos, Blocks.AIR.defaultBlockState());
                pLevel.setBlockAndUpdate(pPos, Blocks.AIR.defaultBlockState());


            }

        }
        return InteractionResult.SUCCESS;
    }

}
