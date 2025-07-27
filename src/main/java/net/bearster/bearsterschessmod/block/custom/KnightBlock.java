package net.bearster.bearsterschessmod.block.custom;

import net.bearster.bearsterschessmod.BearstersChessMod;
import net.bearster.bearsterschessmod.block.ModBlocks;
import net.bearster.bearsterschessmod.block.entity.custom.*;
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
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class KnightBlock extends ChessPieceBlock implements EntityBlock {
    public static final VoxelShape SHAPE = Block.box(3, 0, 3, 13, 15, 13);

    public KnightBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new KnightBlockEntity(pPos, pState);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        super.useItemOn(pStack, pState, pLevel, pPos, pPlayer, pHand, pHitResult);

        if (!pLevel.isClientSide()) {
            if (pStack.is(Blocks.AIR.asItem())) {

                BlockPos offsetPos = BlockPos.ZERO;

                for (int i = 0; i < 4; i++) {

                    if (i == 0) {
                        offsetPos = pPos.offset(-1, 0, -2);
                    } else if (i == 1) {
                        offsetPos = pPos.offset(2, 0, -1);
                    } else if (i == 2) {
                        offsetPos = pPos.offset(1, 0, 2);
                    } else {
                        offsetPos = pPos.offset(-2, 0, 1);
                    }

                    if (pLevel.getBlockState(offsetPos.offset(0,-1,0)).is(ModTags.Blocks.IS_CHESS_BOARD)) {

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
                                    if (blockEntity0 instanceof KnightBlockEntity knightBlockEntity) {
                                        knightBlockEntity.addToList(offsetPos.offset(0, 1, 0));
                                    }
                                }

                            } else {
                                pLevel.setBlockAndUpdate(offsetPos, ModBlocks.MOVEABLE_SQUARE.get().defaultBlockState());

                                BlockEntity blockEntity = pLevel.getBlockEntity(offsetPos);
                                if (blockEntity instanceof MoveableSquareBlockEntity moveableSquareBlockEntity) {
                                    moveableSquareBlockEntity.setPiecePosition(pPos);
                                    BlockEntity blockEntity0 = pLevel.getBlockEntity(pPos);

                                    if (blockEntity0 instanceof KnightBlockEntity knightBlockEntity) {
                                        knightBlockEntity.addToList(offsetPos);
                                    }
                                }

                            }
                        }
                    }
                }

                offsetPos = pPos.offset(1, 0, -2);

                for (int i = 0; i < 4; i++) {

                    if (i == 0) {
                        offsetPos = pPos.offset(1, 0, -2);
                    } else if (i == 1) {
                        offsetPos = pPos.offset(2, 0, 1);
                    } else if (i == 2) {
                        offsetPos = pPos.offset(-1, 0, 2);
                    } else {
                        offsetPos = pPos.offset(-2, 0, -1);
                    }

                    if (pLevel.getBlockState(offsetPos.offset(0,-1,0)).is(ModTags.Blocks.IS_CHESS_BOARD)) {

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
                                    if (blockEntity0 instanceof KnightBlockEntity knightBlockEntity) {
                                        knightBlockEntity.addToList(offsetPos.offset(0, 1, 0));
                                    }
                                }

                            } else {
                                pLevel.setBlockAndUpdate(offsetPos, ModBlocks.MOVEABLE_SQUARE.get().defaultBlockState());

                                BlockEntity blockEntity = pLevel.getBlockEntity(offsetPos);
                                if (blockEntity instanceof MoveableSquareBlockEntity moveableSquareBlockEntity) {
                                    moveableSquareBlockEntity.setPiecePosition(pPos);
                                    BlockEntity blockEntity0 = pLevel.getBlockEntity(pPos);

                                    if (blockEntity0 instanceof KnightBlockEntity knightBlockEntity) {
                                        knightBlockEntity.addToList(offsetPos);
                                    }
                                }

                            }

                            offsetPos = offsetPos.rotate(Rotation.CLOCKWISE_90);
                        }
                    }
                }
            }
        }
        return ItemInteractionResult.SUCCESS;
    }
}
