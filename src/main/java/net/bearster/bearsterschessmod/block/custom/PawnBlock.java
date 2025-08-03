package net.bearster.bearsterschessmod.block.custom;

import net.bearster.bearsterschessmod.BearstersChessMod;
import net.bearster.bearsterschessmod.block.ModBlocks;
import net.bearster.bearsterschessmod.block.entity.custom.AttackableSquareBlockEntity;
import net.bearster.bearsterschessmod.block.entity.custom.ChessPieceBlockEntity;
import net.bearster.bearsterschessmod.block.entity.custom.MoveableSquareBlockEntity;
import net.bearster.bearsterschessmod.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PawnBlock extends ChessPieceBlock {
    public static final VoxelShape SHAPE = Block.box(3, 0, 3, 13, 14, 13);

    public PawnBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    protected void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        if (!pLevel.isClientSide) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof ChessPieceBlockEntity chessPieceBlockEntity) {
                if (pLevel.getBlockState(pPos).is(ModBlocks.PAWN.get())) {
                    chessPieceBlockEntity.setPawnHasDoubleMoved(false);
                }
            }
        }
        super.onPlace(pState, pLevel, pPos, pOldState, pMovedByPiston);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        super.useItemOn(pStack, pState, pLevel, pPos, pPlayer, pHand, pHitResult);
        if (!pLevel.isClientSide()) {
            if (pStack.is(Blocks.AIR.asItem())) {
                BlockEntity be = pLevel.getBlockEntity(pPos);
                if (be instanceof ChessPieceBlockEntity cPBE) {
                    if (ChessPieceBlockEntity.getWhosTurnIsIt() == pLevel.getBlockState(pPos).getValue(COLOUR) || !ChessPieceBlockEntity.getForcedTurnTaking()) {

                        boolean firstSquareIsChessBoard = true;
                        BlockPos offsetPos = BlockPos.ZERO;

                        offsetPos = pPos.relative(pState.getValue(FACING));
                        if (pLevel.getBlockState(offsetPos.offset(0, -1, 0)).is(ModTags.Blocks.IS_CHESS_BOARD) &
                                pLevel.getBlockState(offsetPos).is(Blocks.AIR)) {

                            pLevel.setBlockAndUpdate(offsetPos, ModBlocks.MOVEABLE_SQUARE.get().defaultBlockState());
                            BlockEntity blockEntity = pLevel.getBlockEntity(offsetPos);
                            if (blockEntity instanceof MoveableSquareBlockEntity moveableSquareBlockEntity) {
                                moveableSquareBlockEntity.setPiecePosition(pPos);
                                BlockEntity typeChessPiece = pLevel.getBlockEntity(pPos);
                                if (typeChessPiece instanceof ChessPieceBlockEntity chessPieceBlockEntity) {
                                    if (!pLevel.getBlockState(offsetPos.relative(pState.getValue(FACING),1).offset(0,-1,0)).is(ModTags.Blocks.IS_CHESS_BOARD)) {

                                        pLevel.setBlockAndUpdate(offsetPos.relative(pState.getValue(FACING),1), ModBlocks.PROMOTION_BLOCK.get().defaultBlockState()
                                                .setValue(COLOUR, pLevel.getBlockState(pPos).getValue(COLOUR)).setValue(FACING, pLevel.getBlockState(pPos).getValue(FACING)));

                                        chessPieceBlockEntity.addToList(offsetPos.relative(pState.getValue(FACING),1));
                                    }
                                    chessPieceBlockEntity.addToList(offsetPos);
                                }
                            }
                        } else {
                            firstSquareIsChessBoard = false;
                        }

                        BlockEntity chessPieceEntity = pLevel.getBlockEntity(pPos);
                        if (chessPieceEntity instanceof ChessPieceBlockEntity chessPieceBlockEntity) {

                            if (!chessPieceBlockEntity.getPawnHasDoubleMoved()) {
                                if (firstSquareIsChessBoard) {

                                    offsetPos = pPos.relative(pState.getValue(FACING), 2);

                                    if (pLevel.getBlockState(offsetPos.offset(0, -1, 0)).is(ModTags.Blocks.IS_CHESS_BOARD) &
                                            pLevel.getBlockState(offsetPos).is(Blocks.AIR)) {

                                        pLevel.setBlockAndUpdate(offsetPos, ModBlocks.MOVEABLE_SQUARE.get().defaultBlockState());
                                        BlockEntity blockEntity = pLevel.getBlockEntity(offsetPos);
                                        if (blockEntity instanceof MoveableSquareBlockEntity moveableSquareBlockEntity) {
                                            moveableSquareBlockEntity.setPiecePosition(pPos);
                                            moveableSquareBlockEntity.setDoubleMovedWithPawnLast(true);
                                            if (!pLevel.getBlockState(offsetPos.relative(pState.getValue(FACING),1).offset(0,-1,0)).is(ModTags.Blocks.IS_CHESS_BOARD)) {

                                                pLevel.setBlockAndUpdate(offsetPos.relative(pState.getValue(FACING),1), ModBlocks.PROMOTION_BLOCK.get().defaultBlockState()
                                                        .setValue(COLOUR, pLevel.getBlockState(pPos).getValue(COLOUR)).setValue(FACING, pLevel.getBlockState(pPos).getValue(FACING)));

                                                chessPieceBlockEntity.addToList(offsetPos.relative(pState.getValue(FACING),1));
                                            }
                                            chessPieceBlockEntity.addToList(offsetPos);
                                        }
                                    }
                                }
                            }
                        }

                        offsetPos = switch (pState.getValue(FACING)) {
                            case NORTH -> pPos.offset(-1, 1, -1);
                            case SOUTH -> pPos.offset(1, 1, 1);
                            case EAST -> pPos.offset(1, 1, -1);
                            case WEST -> pPos.offset(-1, 1, 1);
                            default -> BlockPos.ZERO;
                        };

                        if (pLevel.getBlockState(offsetPos.offset(0, -1, 0)).is(ModTags.Blocks.IS_CHESS_PIECE) &
                                pLevel.getBlockState(offsetPos).is(Blocks.AIR)) {

                            if (pLevel.getBlockState(offsetPos.offset(0, -1, 0)).getValue(COLOUR) == !pLevel.getBlockState(pPos).getValue(COLOUR)) {

                                pLevel.setBlockAndUpdate(offsetPos, ModBlocks.ATTACKABLE_SQUARE.get().defaultBlockState());

                                BlockEntity blockEntity = pLevel.getBlockEntity(offsetPos);
                                if (blockEntity instanceof AttackableSquareBlockEntity attackableSquareBlockEntity) {
                                    attackableSquareBlockEntity.setPiecePosition(pPos);
                                }

                                BlockEntity typeChessPiece = pLevel.getBlockEntity(pPos);
                                if (typeChessPiece instanceof ChessPieceBlockEntity chessPieceBlockEntity) {
                                    if (!pLevel.getBlockState(offsetPos.relative(pState.getValue(FACING),1).offset(0,-2,0)).is(ModTags.Blocks.IS_CHESS_BOARD)) {

                                        pLevel.setBlockAndUpdate(offsetPos.relative(pState.getValue(FACING),1).offset(0,-1,0), ModBlocks.PROMOTION_BLOCK.get().defaultBlockState()
                                                .setValue(COLOUR, pLevel.getBlockState(pPos).getValue(COLOUR)).setValue(FACING, pLevel.getBlockState(pPos).getValue(FACING)));

                                        chessPieceBlockEntity.addToList(offsetPos.relative(pState.getValue(FACING),1).offset(0,-1,0));
                                    }
                                    chessPieceBlockEntity.addToList(offsetPos);
                                }
                            }
                        }

                        offsetPos = switch (pState.getValue(FACING)) {
                            case NORTH -> pPos.offset(1, 1, -1);
                            case SOUTH -> pPos.offset(-1, 1, 1);
                            case EAST -> pPos.offset(1, 1, 1);
                            case WEST -> pPos.offset(-1, 1, -1);
                            default -> BlockPos.ZERO;
                        };


                        if (pLevel.getBlockState(offsetPos.offset(0, -1, 0)).is(ModTags.Blocks.IS_CHESS_PIECE) &
                                pLevel.getBlockState(offsetPos).is(Blocks.AIR)) {

                            if (pLevel.getBlockState(offsetPos.offset(0, -1, 0)).getValue(COLOUR) == !pLevel.getBlockState(pPos).getValue(COLOUR)) {

                                pLevel.setBlockAndUpdate(offsetPos, ModBlocks.ATTACKABLE_SQUARE.get().defaultBlockState());

                                BlockEntity blockEntity = pLevel.getBlockEntity(offsetPos);
                                if (blockEntity instanceof AttackableSquareBlockEntity attackableSquareBlockEntity) {
                                    attackableSquareBlockEntity.setPiecePosition(pPos);
                                }

                                BlockEntity typeChessPiece = pLevel.getBlockEntity(pPos);
                                if (typeChessPiece instanceof ChessPieceBlockEntity chessPieceBlockEntity) {
                                    if (!pLevel.getBlockState(offsetPos.relative(pState.getValue(FACING),1).offset(0,-2,0)).is(ModTags.Blocks.IS_CHESS_BOARD)) {

                                        pLevel.setBlockAndUpdate(offsetPos.relative(pState.getValue(FACING),1).offset(0,-1,0), ModBlocks.PROMOTION_BLOCK.get().defaultBlockState()
                                                .setValue(COLOUR, pLevel.getBlockState(pPos).getValue(COLOUR)).setValue(FACING, pLevel.getBlockState(pPos).getValue(FACING)));

                                        chessPieceBlockEntity.addToList(offsetPos.relative(pState.getValue(FACING),1).offset(0,-1,0));
                                    }
                                    chessPieceBlockEntity.addToList(offsetPos);
                                }
                            }
                        }


                        // En passant


                        offsetPos = switch (pState.getValue(FACING)) {
                            case NORTH -> pPos.offset(-1, 0, -1);
                            case SOUTH -> pPos.offset(1, 0, 1);
                            case EAST -> pPos.offset(1, 0, -1);
                            case WEST -> pPos.offset(-1, 0, 1);
                            default -> BlockPos.ZERO;
                        };

                        BlockPos posToTheLeft = pPos.relative(pLevel.getBlockState(pPos)
                                .getValue(PawnBlock.FACING).getCounterClockWise(), 1);


                        if (pLevel.getBlockState(offsetPos).is(Blocks.AIR) &&
                                pLevel.getBlockState(posToTheLeft).is(ModBlocks.PAWN.get())) {

                            if (pLevel.getBlockState(posToTheLeft)
                                    .getValue(COLOUR) == !pLevel.getBlockState(pPos).getValue(COLOUR)) {
                                BlockEntity typeChessPiece = pLevel.getBlockEntity(pPos);
                                if (typeChessPiece instanceof ChessPieceBlockEntity chessPieceBlockEntity) {


                                    if (ChessPieceBlockEntity.getLastPieceMoved().equals(ModBlocks.PAWN.get().toString()) &&
                                            ChessPieceBlockEntity.getLastDoublePiecePos().equals(posToTheLeft) &&
                                            ChessPieceBlockEntity.getLastPieceMovedColour() == !pLevel.getBlockState(pPos).getValue(COLOUR)) {
                                        pLevel.setBlockAndUpdate(posToTheLeft.offset(0, 1, 0), ModBlocks.ATTACKABLE_SQUARE.get().defaultBlockState());

                                        BlockEntity blockEntity = pLevel.getBlockEntity(posToTheLeft.offset(0, 1, 0));
                                        if (blockEntity instanceof AttackableSquareBlockEntity attackableSquareBlockEntity) {
                                            attackableSquareBlockEntity.setPiecePosition(pPos);
                                            attackableSquareBlockEntity.setEnPassant(true);
                                            attackableSquareBlockEntity.setEnPassantPosition(offsetPos);
                                        }

                                        chessPieceBlockEntity.addToList(posToTheLeft.offset(0,1,0));
                                    }
                                }
                            }
                        }


                        offsetPos = switch (pState.getValue(FACING)) {
                            case NORTH -> pPos.offset(1, 0, -1);
                            case SOUTH -> pPos.offset(-1, 0, 1);
                            case EAST -> pPos.offset(1, 0, 1);
                            case WEST -> pPos.offset(-1, 0, -1);
                            default -> BlockPos.ZERO;
                        };

                        BlockPos posToTheRight = pPos.relative(pLevel.getBlockState(pPos)
                                .getValue(PawnBlock.FACING).getClockWise(), 1);

                        if (pLevel.getBlockState(offsetPos).is(Blocks.AIR) &&
                                pLevel.getBlockState(posToTheRight).is(ModBlocks.PAWN.get())) {

                            if (pLevel.getBlockState(posToTheRight)
                                    .getValue(COLOUR) == !pLevel.getBlockState(pPos).getValue(COLOUR)) {

                                BlockEntity typeChessPiece = pLevel.getBlockEntity(pPos);
                                if (typeChessPiece instanceof ChessPieceBlockEntity chessPieceBlockEntity) {
                                    if (ChessPieceBlockEntity.getLastPieceMoved().equals(ModBlocks.PAWN.get().toString()) &&
                                            ChessPieceBlockEntity.getLastDoublePiecePos().equals(posToTheRight) && ChessPieceBlockEntity.getLastPieceMovedColour() == !pLevel.getBlockState(pPos).getValue(COLOUR))

                                        pLevel.setBlockAndUpdate(posToTheRight.offset(0, 1, 0), ModBlocks.ATTACKABLE_SQUARE.get().defaultBlockState());

                                    BlockEntity blockEntity = pLevel.getBlockEntity(posToTheRight.offset(0, 1, 0));
                                    if (blockEntity instanceof AttackableSquareBlockEntity attackableSquareBlockEntity) {
                                        attackableSquareBlockEntity.setPiecePosition(pPos);
                                        attackableSquareBlockEntity.setEnPassant(true);
                                        attackableSquareBlockEntity.setEnPassantPosition(offsetPos);
                                    }

                                    chessPieceBlockEntity.addToList(posToTheRight.offset(0,1,0));
                                }
                            }
                        }

                    }
                }
            }
        }

        return ItemInteractionResult.SUCCESS;
    }
}
