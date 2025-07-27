package net.bearster.bearsterschessmod.block.custom;

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
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class RookBlock extends ChessPieceBlock {
    public static final VoxelShape SHAPE = Block.box(3, 0, 3, 13, 15, 13);

    public RookBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        super.useItemOn(pStack, pState, pLevel, pPos, pPlayer, pHand, pHitResult);

        if (!pLevel.isClientSide()) {
            if (pStack.is(Blocks.AIR.asItem())) {
                BlockEntity be = pLevel.getBlockEntity(pPos);
                if (be instanceof ChessPieceBlockEntity cPBE) {
                    if (cPBE.getWhosTurnIsIt() == pLevel.getBlockState(pPos).getValue(COLOUR) || !cPBE.getForcedTurnTaking()) {

                        BlockPos offsetPos = pPos.offset(0, 0, -1);

                        while (pLevel.getBlockState(offsetPos.offset(0, -1, 0)).is(ModTags.Blocks.IS_CHESS_BOARD)) {
                            if (pLevel.getBlockState(offsetPos).is(Blocks.AIR) || pLevel.getBlockState(offsetPos).is(ModTags.Blocks.IS_CHESS_PIECE)) {
                                if (pLevel.getBlockState(offsetPos).is(ModTags.Blocks.IS_CHESS_PIECE) &
                                        pLevel.getBlockState(offsetPos.offset(0, 1, 0)).is(Blocks.AIR)) {

                                    if (pLevel.getBlockState(offsetPos).getValue(COLOUR) == !pLevel.getBlockState(pPos).getValue(COLOUR)) {

                                        pLevel.setBlockAndUpdate(offsetPos.offset(0, 1, 0), ModBlocks.ATTACKABLE_SQUARE.get().defaultBlockState());

                                        BlockEntity blockEntity = pLevel.getBlockEntity(offsetPos.offset(0, 1, 0));
                                        if (blockEntity instanceof AttackableSquareBlockEntity attackableSquareBlockEntity) {
                                            attackableSquareBlockEntity.setPiecePosition(pPos);
                                        }

                                        BlockEntity blockEntity0 = pLevel.getBlockEntity(pPos);
                                        if (blockEntity0 instanceof ChessPieceBlockEntity chessPieceBlockEntity) {
                                            chessPieceBlockEntity.addToList(offsetPos.offset(0, 1, 0));
                                        }
                                    }

                                    break;
                                } else {
                                    pLevel.setBlockAndUpdate(offsetPos, ModBlocks.MOVEABLE_SQUARE.get().defaultBlockState());

                                    BlockEntity blockEntity = pLevel.getBlockEntity(offsetPos);
                                    if (blockEntity instanceof MoveableSquareBlockEntity moveableSquareBlockEntity) {
                                        moveableSquareBlockEntity.setPiecePosition(pPos);
                                        BlockEntity blockEntity0 = pLevel.getBlockEntity(pPos);

                                        if (blockEntity0 instanceof ChessPieceBlockEntity chessPieceBlockEntity) {
                                            chessPieceBlockEntity.addToList(offsetPos);
                                        }
                                    }

                                    offsetPos = offsetPos.offset(0, 0, -1);
                                }
                            } else {
                                break;
                            }
                        }

                        offsetPos = pPos.offset(1, 0, 0);

                        while (pLevel.getBlockState(offsetPos.offset(0, -1, 0)).is(ModTags.Blocks.IS_CHESS_BOARD)) {
                            if (pLevel.getBlockState(offsetPos).is(Blocks.AIR) || pLevel.getBlockState(offsetPos).is(ModTags.Blocks.IS_CHESS_PIECE)) {
                                if (pLevel.getBlockState(offsetPos).is(ModTags.Blocks.IS_CHESS_PIECE) &
                                        pLevel.getBlockState(offsetPos.offset(0, 1, 0)).is(Blocks.AIR)) {

                                    if (pLevel.getBlockState(offsetPos).getValue(COLOUR) == !pLevel.getBlockState(pPos).getValue(COLOUR)) {

                                        pLevel.setBlockAndUpdate(offsetPos.offset(0, 1, 0), ModBlocks.ATTACKABLE_SQUARE.get().defaultBlockState());

                                        BlockEntity blockEntity = pLevel.getBlockEntity(offsetPos.offset(0, 1, 0));
                                        if (blockEntity instanceof AttackableSquareBlockEntity attackableSquareBlockEntity) {
                                            attackableSquareBlockEntity.setPiecePosition(pPos);
                                        }

                                        BlockEntity blockEntity0 = pLevel.getBlockEntity(pPos);
                                        if (blockEntity0 instanceof ChessPieceBlockEntity chessPieceBlockEntity) {
                                            chessPieceBlockEntity.addToList(offsetPos.offset(0, 1, 0));
                                        }
                                    }

                                    break;
                                } else {
                                    pLevel.setBlockAndUpdate(offsetPos, ModBlocks.MOVEABLE_SQUARE.get().defaultBlockState());

                                    BlockEntity blockEntity = pLevel.getBlockEntity(offsetPos);
                                    if (blockEntity instanceof MoveableSquareBlockEntity moveableSquareBlockEntity) {
                                        moveableSquareBlockEntity.setPiecePosition(pPos);
                                        BlockEntity blockEntity0 = pLevel.getBlockEntity(pPos);

                                        if (blockEntity0 instanceof ChessPieceBlockEntity chessPieceBlockEntity) {
                                            chessPieceBlockEntity.addToList(offsetPos);
                                        }
                                    }

                                    offsetPos = offsetPos.offset(1, 0, 0);
                                }
                            } else {
                                break;
                            }
                        }

                        offsetPos = pPos.offset(0, 0, 1);

                        while (pLevel.getBlockState(offsetPos.offset(0, -1, 0)).is(ModTags.Blocks.IS_CHESS_BOARD)) {
                            if (pLevel.getBlockState(offsetPos).is(Blocks.AIR) || pLevel.getBlockState(offsetPos).is(ModTags.Blocks.IS_CHESS_PIECE)) {
                                if (pLevel.getBlockState(offsetPos).is(ModTags.Blocks.IS_CHESS_PIECE) &
                                        pLevel.getBlockState(offsetPos.offset(0, 1, 0)).is(Blocks.AIR)) {

                                    if (pLevel.getBlockState(offsetPos).getValue(COLOUR) == !pLevel.getBlockState(pPos).getValue(COLOUR)) {

                                        pLevel.setBlockAndUpdate(offsetPos.offset(0, 1, 0), ModBlocks.ATTACKABLE_SQUARE.get().defaultBlockState());

                                        BlockEntity blockEntity = pLevel.getBlockEntity(offsetPos.offset(0, 1, 0));
                                        if (blockEntity instanceof AttackableSquareBlockEntity attackableSquareBlockEntity) {
                                            attackableSquareBlockEntity.setPiecePosition(pPos);
                                        }

                                        BlockEntity blockEntity0 = pLevel.getBlockEntity(pPos);
                                        if (blockEntity0 instanceof ChessPieceBlockEntity chessPieceBlockEntity) {
                                            chessPieceBlockEntity.addToList(offsetPos.offset(0, 1, 0));
                                        }
                                    }

                                    break;
                                } else {
                                    pLevel.setBlockAndUpdate(offsetPos, ModBlocks.MOVEABLE_SQUARE.get().defaultBlockState());

                                    BlockEntity blockEntity = pLevel.getBlockEntity(offsetPos);
                                    if (blockEntity instanceof MoveableSquareBlockEntity moveableSquareBlockEntity) {
                                        moveableSquareBlockEntity.setPiecePosition(pPos);
                                        BlockEntity blockEntity0 = pLevel.getBlockEntity(pPos);

                                        if (blockEntity0 instanceof ChessPieceBlockEntity chessPieceBlockEntity) {
                                            chessPieceBlockEntity.addToList(offsetPos);
                                        }
                                    }

                                    offsetPos = offsetPos.offset(0, 0, 1);
                                }
                            } else {
                                break;
                            }
                        }

                        offsetPos = pPos.offset(-1, 0, 0);

                        while (pLevel.getBlockState(offsetPos.offset(0, -1, 0)).is(ModTags.Blocks.IS_CHESS_BOARD)) {
                            if (pLevel.getBlockState(offsetPos).is(Blocks.AIR) || pLevel.getBlockState(offsetPos).is(ModTags.Blocks.IS_CHESS_PIECE)) {
                                if (pLevel.getBlockState(offsetPos).is(ModTags.Blocks.IS_CHESS_PIECE) &
                                        pLevel.getBlockState(offsetPos.offset(0, 1, 0)).is(Blocks.AIR)) {

                                    if (pLevel.getBlockState(offsetPos).getValue(COLOUR) == !pLevel.getBlockState(pPos).getValue(COLOUR)) {

                                        pLevel.setBlockAndUpdate(offsetPos.offset(0, 1, 0), ModBlocks.ATTACKABLE_SQUARE.get().defaultBlockState());

                                        BlockEntity blockEntity = pLevel.getBlockEntity(offsetPos.offset(0, 1, 0));
                                        if (blockEntity instanceof AttackableSquareBlockEntity attackableSquareBlockEntity) {
                                            attackableSquareBlockEntity.setPiecePosition(pPos);
                                        }

                                        BlockEntity blockEntity0 = pLevel.getBlockEntity(pPos);
                                        if (blockEntity0 instanceof ChessPieceBlockEntity chessPieceBlockEntity) {
                                            chessPieceBlockEntity.addToList(offsetPos.offset(0, 1, 0));
                                        }
                                    }

                                    break;
                                } else {
                                    pLevel.setBlockAndUpdate(offsetPos, ModBlocks.MOVEABLE_SQUARE.get().defaultBlockState());

                                    BlockEntity blockEntity = pLevel.getBlockEntity(offsetPos);
                                    if (blockEntity instanceof MoveableSquareBlockEntity moveableSquareBlockEntity) {
                                        moveableSquareBlockEntity.setPiecePosition(pPos);
                                        BlockEntity blockEntity0 = pLevel.getBlockEntity(pPos);

                                        if (blockEntity0 instanceof ChessPieceBlockEntity chessPieceBlockEntity) {
                                            chessPieceBlockEntity.addToList(offsetPos);
                                        }
                                    }

                                    offsetPos = offsetPos.offset(-1, 0, 0);
                                }
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }
        return ItemInteractionResult.SUCCESS;
    }
}

