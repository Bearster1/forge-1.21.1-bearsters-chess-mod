package net.bearster.bearsterschessmod.block.custom;

import net.bearster.bearsterschessmod.BearstersChessMod;
import net.bearster.bearsterschessmod.block.ModBlocks;
import net.bearster.bearsterschessmod.block.entity.custom.*;
import net.bearster.bearsterschessmod.screen.custom.PromotionScreen;
import net.bearster.bearsterschessmod.util.ModTags;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
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
        if (!pLevel.isClientSide()) {

            BlockState promotionBlock = pLevel.getBlockState(pPos);
            BlockEntity promotionBE = pLevel.getBlockEntity(pPos);

            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof MoveableSquareBlockEntity moveableSquareBlockEntity) {

                BlockPos storedPos = moveableSquareBlockEntity.getPiecePosition();
                BlockState newBlockstate = pLevel.getBlockState(storedPos);
                BlockState castleBlockstate = pLevel.getBlockState(moveableSquareBlockEntity.getCastlePosition());

                if (newBlockstate.is(ModBlocks.PAWN.get())) {

                    Direction facing = newBlockstate.getValue(PawnBlock.FACING);

                    if (pLevel.getBlockState(pPos.relative(facing)).is(ModBlocks.PROMOTION_BLOCK.get())) {
                        promotionBlock = pLevel.getBlockState(pPos.relative(facing));
                        promotionBE = pLevel.getBlockEntity(pPos.relative(facing));
                    }
                }

                BlockEntity typeChessPiece = pLevel.getBlockEntity(storedPos);
                if (typeChessPiece instanceof ChessPieceBlockEntity chessPieceBlockEntity) {
                    List<Integer> xList = chessPieceBlockEntity.getXList();
                    List<Integer> yList = chessPieceBlockEntity.getYList();
                    List<Integer> zList = chessPieceBlockEntity.getZList();

                    for (int i = 0; i <
                            (xList.size() + yList.size() + zList.size()) / 3;
                         i++) {
                        pLevel.setBlockAndUpdate(new BlockPos(xList.get(i),yList.get(i),zList.get(i)), Blocks.AIR.defaultBlockState());
                    }

                    chessPieceBlockEntity.resetList();
                }

                BlockEntity blockEntity1 = pLevel.getBlockEntity(storedPos);
                if (blockEntity1 instanceof ChessPieceBlockEntity chessPieceBlockEntity) {
                    if (pLevel.getBlockState(storedPos).is(ModBlocks.KING.get())) {
                        if (moveableSquareBlockEntity.getCastlePosition() != null && moveableSquareBlockEntity.getCastleDirection() != null) {
                            if (moveableSquareBlockEntity.getCastleDirection().equals("true")) {
                                pLevel.setBlock(pPos.relative(pLevel.getBlockState(storedPos).getValue(ChessPieceBlock.FACING).getCounterClockWise()), castleBlockstate, 3);
                            } else if (moveableSquareBlockEntity.getCastleDirection().equals("false")){
                                pLevel.setBlock(pPos.relative(pLevel.getBlockState(storedPos).getValue(ChessPieceBlock.FACING).getClockWise()), castleBlockstate, 3);
                            }
                            pLevel.setBlock(moveableSquareBlockEntity.getCastlePosition(), Blocks.AIR.defaultBlockState(), 3);
                        }
                    }
                }

                pLevel.setBlock(pPos, newBlockstate, 3);

                BlockEntity blockEntity2 = pLevel.getBlockEntity(pPos);
                if (blockEntity2 instanceof ChessPieceBlockEntity chessPieceBlockEntity) {
                    chessPieceBlockEntity.setHasMoved(true);
                    chessPieceBlockEntity.setLastPieceMoved(pLevel.getBlockState(pPos).getBlock().toString());
                    chessPieceBlockEntity.setLastPieceMovedColour(pLevel.getBlockState(pPos).getValue(COLOUR));

                    BlockState blockState = pLevel.getBlockState(pPos);
                    if (blockState.is(ModBlocks.PAWN.get())) {
                        if (moveableSquareBlockEntity.getDoubleMovedWithPawnLast()) {
                            chessPieceBlockEntity.setLastDoublePiecePos(pPos);
                        } else {
                            chessPieceBlockEntity.setLastDoublePiecePos(BlockPos.ZERO);
                        }

                        chessPieceBlockEntity.setPawnHasDoubleMoved(true);
                        Direction facing = blockState.getValue(PawnBlock.FACING);

                        if (promotionBlock.is(ModBlocks.PROMOTION_BLOCK.get())) {

                            if (promotionBE instanceof ChessPieceBlockEntity chessPieceBlockEntity1) {


                                if (chessPieceBlockEntity1.getPieceToPromoteTo().equals(ModBlocks.KNIGHT.get().toString())) {
                                    pLevel.setBlock(pPos,
                                            ModBlocks.KNIGHT.get().defaultBlockState()
                                                    .setValue(COLOUR, promotionBlock.getValue(COLOUR))
                                                    .setValue(KnightBlock.FACING, promotionBlock.getValue(FACING))
                                            , 3);
                                } else if (chessPieceBlockEntity1.getPieceToPromoteTo().equals(ModBlocks.BISHOP.get().toString())) {
                                    pLevel.setBlock(pPos,
                                            ModBlocks.BISHOP.get().defaultBlockState()
                                                    .setValue(COLOUR, promotionBlock.getValue(COLOUR))
                                                    .setValue(KnightBlock.FACING, promotionBlock.getValue(FACING))
                                            , 3);
                                } else if (chessPieceBlockEntity1.getPieceToPromoteTo().equals(ModBlocks.ROOK.get().toString())) {
                                    pLevel.setBlock(pPos,
                                            ModBlocks.ROOK.get().defaultBlockState()
                                                    .setValue(COLOUR, promotionBlock.getValue(COLOUR))
                                                    .setValue(KnightBlock.FACING, promotionBlock.getValue(FACING))
                                            , 3);
                                } else if (chessPieceBlockEntity1.getPieceToPromoteTo().equals(ModBlocks.QUEEN.get().toString())) {
                                    pLevel.setBlock(pPos,
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

            }

        }
        return InteractionResult.SUCCESS;
    }
}
