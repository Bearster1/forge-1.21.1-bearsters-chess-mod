package net.bearster.bearsterschessmod.block.custom;

import net.bearster.bearsterschessmod.BearstersChessMod;
import net.bearster.bearsterschessmod.block.ModBlocks;
import net.bearster.bearsterschessmod.block.entity.custom.MoveableSquareBlockEntity;
import net.bearster.bearsterschessmod.block.entity.custom.PawnBlockEntity;
import net.bearster.bearsterschessmod.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PawnBlock extends RotationalBlock implements EntityBlock {
    public static final VoxelShape SHAPE = Block.box(3, 0, 3, 13, 14, 13);
    public static final BooleanProperty COLOUR = BooleanProperty.create("colour");

    public PawnBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(COLOUR, true));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(COLOUR);
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new PawnBlockEntity(pPos, pState);
    }

    @Override
    protected void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        if (!pLevel.isClientSide) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof PawnBlockEntity pawnBlockEntity) {
                pawnBlockEntity.setHasDoubleMoved(false);
            }
        }
        super.onPlace(pState, pLevel, pPos, pOldState, pMovedByPiston);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (!pLevel.isClientSide()) {
            if (pStack.is(ModBlocks.WHITE_SQUARE.get().asItem())) {
                this.stateDefinition.any().setValue(COLOUR, true);
            } else if (pStack.is(ModBlocks.BLACK_SQUARE.get().asItem())) {
                this.stateDefinition.any().setValue(COLOUR, false);
            }
        }

        return ItemInteractionResult.SUCCESS;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {

        if (!pLevel.isClientSide) {
            boolean firstSquareIsChessBoard = true;

            if (pState.getValue(FACING) == Direction.NORTH) {
                BlockPos offsetPos = pPos.offset(0,0,-1);

                if (pLevel.getBlockState(offsetPos.offset(0,-1,0)).is(ModTags.Blocks.IS_CHESS_BOARD) &
                        pLevel.getBlockState(offsetPos).is(Blocks.AIR)) {

                    pLevel.setBlock(offsetPos, ModBlocks.MOVEABLE_SQUARE.get().defaultBlockState(), 3);
                    BlockEntity blockEntity = pLevel.getBlockEntity(offsetPos);
                    if (blockEntity instanceof MoveableSquareBlockEntity moveableSquareBlockEntity) {
                        moveableSquareBlockEntity.setPiecePosition(pPos);
                    }
                } else {
                    firstSquareIsChessBoard = false;
                }
            } else if (pState.getValue(FACING) == Direction.SOUTH) {
                BlockPos offsetPos = pPos.offset(0,0,1);

                if (pLevel.getBlockState(offsetPos.offset(0,-1,0)).is(ModTags.Blocks.IS_CHESS_BOARD) &
                        pLevel.getBlockState(offsetPos).is(Blocks.AIR)) {

                    pLevel.setBlock(offsetPos, ModBlocks.MOVEABLE_SQUARE.get().defaultBlockState(), 3);
                    BlockEntity blockEntity = pLevel.getBlockEntity(offsetPos);
                    if (blockEntity instanceof MoveableSquareBlockEntity moveableSquareBlockEntity) {
                        moveableSquareBlockEntity.setPiecePosition(pPos);
                    }
                } else {
                    firstSquareIsChessBoard = false;
                }
            } else if (pState.getValue(FACING) == Direction.EAST) {
                BlockPos offsetPos = pPos.offset(1,0,0);

                if (pLevel.getBlockState(offsetPos.offset(0,-1,0)).is(ModTags.Blocks.IS_CHESS_BOARD) &
                        pLevel.getBlockState(offsetPos).is(Blocks.AIR)) {

                    pLevel.setBlock(offsetPos, ModBlocks.MOVEABLE_SQUARE.get().defaultBlockState(), 3);
                    BlockEntity blockEntity = pLevel.getBlockEntity(offsetPos);
                    if (blockEntity instanceof MoveableSquareBlockEntity moveableSquareBlockEntity) {
                        moveableSquareBlockEntity.setPiecePosition(pPos);
                    }
                } else {
                    firstSquareIsChessBoard = false;
                }
            } else if (pState.getValue(FACING) == Direction.WEST) {
                BlockPos offsetPos = pPos.offset(-1,0,0);

                if (pLevel.getBlockState(offsetPos.offset(0,-1,0)).is(ModTags.Blocks.IS_CHESS_BOARD) &
                        pLevel.getBlockState(offsetPos).is(Blocks.AIR)) {

                    pLevel.setBlock(offsetPos, ModBlocks.MOVEABLE_SQUARE.get().defaultBlockState(), 3);
                    BlockEntity blockEntity = pLevel.getBlockEntity(offsetPos);
                    if (blockEntity instanceof MoveableSquareBlockEntity moveableSquareBlockEntity) {
                        moveableSquareBlockEntity.setPiecePosition(pPos);
                    }
                } else {
                    firstSquareIsChessBoard = false;
                }

            }

            BlockEntity pawnEntity = pLevel.getBlockEntity(pPos);
            if (pawnEntity instanceof PawnBlockEntity pawnBlockEntity) {

                if (!pawnBlockEntity.getHasDoubleMoved()) {
                    if (firstSquareIsChessBoard) {
                        if (pState.getValue(FACING) == Direction.NORTH) {
                            BlockPos offsetPos = pPos.offset(0, 0, -2);

                            if (pLevel.getBlockState(offsetPos.offset(0, -1, 0)).is(ModTags.Blocks.IS_CHESS_BOARD) &
                                    pLevel.getBlockState(offsetPos).is(Blocks.AIR)) {

                                pLevel.setBlock(offsetPos, ModBlocks.MOVEABLE_SQUARE.get().defaultBlockState(), 3);
                                BlockEntity blockEntity = pLevel.getBlockEntity(offsetPos);
                                if (blockEntity instanceof MoveableSquareBlockEntity moveableSquareBlockEntity) {
                                    moveableSquareBlockEntity.setPiecePosition(pPos);
                                }
                            }
                        } else if (pState.getValue(FACING) == Direction.SOUTH) {
                            BlockPos offsetPos = pPos.offset(0, 0, 2);

                            if (pLevel.getBlockState(offsetPos.offset(0, -1, 0)).is(ModTags.Blocks.IS_CHESS_BOARD) &
                                    pLevel.getBlockState(offsetPos).is(Blocks.AIR)) {

                                pLevel.setBlock(offsetPos, ModBlocks.MOVEABLE_SQUARE.get().defaultBlockState(), 3);
                                BlockEntity blockEntity = pLevel.getBlockEntity(offsetPos);
                                if (blockEntity instanceof MoveableSquareBlockEntity moveableSquareBlockEntity) {
                                    moveableSquareBlockEntity.setPiecePosition(pPos);
                                }
                            }
                        } else if (pState.getValue(FACING) == Direction.EAST) {
                            BlockPos offsetPos = pPos.offset(2, 0, 0);

                            if (pLevel.getBlockState(offsetPos.offset(0, -1, 0)).is(ModTags.Blocks.IS_CHESS_BOARD) &
                                    pLevel.getBlockState(offsetPos).is(Blocks.AIR)) {

                                pLevel.setBlock(offsetPos, ModBlocks.MOVEABLE_SQUARE.get().defaultBlockState(), 3);
                                BlockEntity blockEntity = pLevel.getBlockEntity(offsetPos);
                                if (blockEntity instanceof MoveableSquareBlockEntity moveableSquareBlockEntity) {
                                    moveableSquareBlockEntity.setPiecePosition(pPos);
                                }
                            }
                        } else if (pState.getValue(FACING) == Direction.WEST) {
                            BlockPos offsetPos = pPos.offset(-2, 0, 0);

                            if (pLevel.getBlockState(offsetPos.offset(0, -1, 0)).is(ModTags.Blocks.IS_CHESS_BOARD) &
                                    pLevel.getBlockState(offsetPos).is(Blocks.AIR)) {

                                pLevel.setBlock(offsetPos, ModBlocks.MOVEABLE_SQUARE.get().defaultBlockState(), 3);
                                BlockEntity blockEntity = pLevel.getBlockEntity(offsetPos);
                                if (blockEntity instanceof MoveableSquareBlockEntity moveableSquareBlockEntity) {
                                    moveableSquareBlockEntity.setPiecePosition(pPos);
                                }
                            }
                        }
                    }
                }
            }
        }

        return InteractionResult.SUCCESS;
    }

}
