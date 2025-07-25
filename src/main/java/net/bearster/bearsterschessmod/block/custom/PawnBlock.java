package net.bearster.bearsterschessmod.block.custom;

import net.bearster.bearsterschessmod.BearstersChessMod;
import net.bearster.bearsterschessmod.block.ModBlocks;
import net.bearster.bearsterschessmod.block.entity.custom.AttackableSquareBlockEntity;
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

public class PawnBlock extends ChessPieceBlock implements EntityBlock {
    public static final VoxelShape SHAPE = Block.box(3, 0, 3, 13, 14, 13);
    public static final BooleanProperty COLOUR = BooleanProperty.create("colour");

    public PawnBlock(Properties properties) {
        super(properties);
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
        super.useItemOn(pStack, pState, pLevel, pPos, pPlayer, pHand, pHitResult);
        if (!pLevel.isClientSide()) {
            if (pStack.is(Blocks.AIR.asItem())) {
                boolean firstSquareIsChessBoard = true;
                BlockPos offsetPos = BlockPos.ZERO;

                offsetPos = pPos.relative(pState.getValue(FACING));
                if (pLevel.getBlockState(offsetPos.offset(0,-1,0)).is(ModTags.Blocks.IS_CHESS_BOARD) &
                        pLevel.getBlockState(offsetPos).is(Blocks.AIR)) {

                        pLevel.setBlockAndUpdate(offsetPos, ModBlocks.MOVEABLE_SQUARE.get().defaultBlockState());
                        BlockEntity blockEntity = pLevel.getBlockEntity(offsetPos);
                        if (blockEntity instanceof MoveableSquareBlockEntity moveableSquareBlockEntity) {
                            moveableSquareBlockEntity.setPiecePosition(pPos);
                            BlockEntity typePawn = pLevel.getBlockEntity(pPos);
                            if (typePawn instanceof PawnBlockEntity pawnBlockEntity) {
                                pawnBlockEntity.addToList(offsetPos);
                            }
                        }
                    } else {
                        firstSquareIsChessBoard = false;
                    }

                BlockEntity pawnEntity = pLevel.getBlockEntity(pPos);
                if (pawnEntity instanceof PawnBlockEntity pawnBlockEntity) {

                    if (!pawnBlockEntity.getHasDoubleMoved()) {
                        if (firstSquareIsChessBoard) {

                            offsetPos = pPos.relative(pState.getValue(FACING),2);

                            if (pLevel.getBlockState(offsetPos.offset(0, -1, 0)).is(ModTags.Blocks.IS_CHESS_BOARD) &
                                    pLevel.getBlockState(offsetPos).is(Blocks.AIR)) {

                                pLevel.setBlockAndUpdate(offsetPos, ModBlocks.MOVEABLE_SQUARE.get().defaultBlockState());
                                BlockEntity blockEntity = pLevel.getBlockEntity(offsetPos);
                                if (blockEntity instanceof MoveableSquareBlockEntity moveableSquareBlockEntity) {
                                    moveableSquareBlockEntity.setPiecePosition(pPos);
                                    pawnBlockEntity.addToList(offsetPos);
                                }
                            }
                        }
                    }
                }


                if (pState.getValue(FACING) == Direction.NORTH) {

                    offsetPos = pPos.offset(-1,1,-1);

                    if (pLevel.getBlockState(offsetPos.offset(0,-1,0)).is(ModTags.Blocks.IS_CHESS_PIECE) &
                            pLevel.getBlockState(offsetPos).is(Blocks.AIR)) {

                        if (pLevel.getBlockState(offsetPos.offset(0,-1,0)).getValue(COLOUR) == !pLevel.getBlockState(pPos).getValue(COLOUR)) {

                            pLevel.setBlockAndUpdate(offsetPos, ModBlocks.ATTACKABLE_SQUARE.get().defaultBlockState());

                            BlockEntity blockEntity = pLevel.getBlockEntity(offsetPos);
                            if (blockEntity instanceof AttackableSquareBlockEntity attackableSquareBlockEntity) {
                                attackableSquareBlockEntity.setPiecePosition(pPos);
                            }

                            BlockEntity typePawn = pLevel.getBlockEntity(pPos);
                            if (typePawn instanceof PawnBlockEntity pawnBlockEntity) {
                                pawnBlockEntity.addToList(offsetPos);
                            }
                        }
                    }

                } else if (pState.getValue(FACING) == Direction.SOUTH) {
                    offsetPos = pPos.offset(1,1,1);

                    BearstersChessMod.LOGGER.info("Hello at:" + offsetPos);
                    BearstersChessMod.LOGGER.info("I am a:"+pLevel.getBlockState(offsetPos.offset(0,-1,0)));
                    if (pLevel.getBlockState(offsetPos.offset(0,-1,0)).is(ModTags.Blocks.IS_CHESS_PIECE)) {
                        BearstersChessMod.LOGGER.info("I am facing:" + pLevel.getBlockState(offsetPos.offset(0, -1, 0)).getValue(FACING));
                    }

                    if (pLevel.getBlockState(offsetPos.offset(0,-1,0)).is(ModTags.Blocks.IS_CHESS_PIECE) &
                            pLevel.getBlockState(offsetPos).is(Blocks.AIR)) {

                        if (pLevel.getBlockState(offsetPos.offset(0,-1,0)).getValue(COLOUR) == !pLevel.getBlockState(pPos).getValue(COLOUR)) {

                            pLevel.setBlockAndUpdate(offsetPos, ModBlocks.ATTACKABLE_SQUARE.get().defaultBlockState());

                            BlockEntity blockEntity = pLevel.getBlockEntity(offsetPos);
                            if (blockEntity instanceof AttackableSquareBlockEntity attackableSquareBlockEntity) {
                                attackableSquareBlockEntity.setPiecePosition(pPos);
                            }

                            BlockEntity typePawn = pLevel.getBlockEntity(pPos);
                            if (typePawn instanceof PawnBlockEntity pawnBlockEntity) {
                                pawnBlockEntity.addToList(offsetPos);
                            }
                        }
                    }

                } else if (pState.getValue(FACING) == Direction.EAST) {

                    offsetPos = pPos.offset(1,1,-1);

                    if (pLevel.getBlockState(offsetPos.offset(0,-1,0)).is(ModTags.Blocks.IS_CHESS_PIECE) &
                            pLevel.getBlockState(offsetPos).is(Blocks.AIR)) {

                        if (pLevel.getBlockState(offsetPos.offset(0,-1,0)).getValue(COLOUR) == !pLevel.getBlockState(pPos).getValue(COLOUR)) {

                            pLevel.setBlockAndUpdate(offsetPos, ModBlocks.ATTACKABLE_SQUARE.get().defaultBlockState());

                            BlockEntity blockEntity = pLevel.getBlockEntity(offsetPos);
                            if (blockEntity instanceof AttackableSquareBlockEntity attackableSquareBlockEntity) {
                                attackableSquareBlockEntity.setPiecePosition(pPos);
                            }

                            BlockEntity typePawn = pLevel.getBlockEntity(pPos);
                            if (typePawn instanceof PawnBlockEntity pawnBlockEntity) {
                                pawnBlockEntity.addToList(offsetPos);
                            }
                        }
                    }

                } else if (pState.getValue(FACING) == Direction.WEST) {

                    offsetPos = pPos.offset(-1,1,1);

                    if (pLevel.getBlockState(offsetPos.offset(0,-1,0)).is(ModTags.Blocks.IS_CHESS_PIECE) &
                            pLevel.getBlockState(offsetPos).is(Blocks.AIR)) {

                        if (pLevel.getBlockState(offsetPos.offset(0, -1, 0)).getValue(COLOUR) == !pLevel.getBlockState(pPos).getValue(COLOUR)) {

                            pLevel.setBlockAndUpdate(offsetPos, ModBlocks.ATTACKABLE_SQUARE.get().defaultBlockState());

                            BlockEntity blockEntity = pLevel.getBlockEntity(offsetPos);
                            if (blockEntity instanceof AttackableSquareBlockEntity attackableSquareBlockEntity) {
                                attackableSquareBlockEntity.setPiecePosition(pPos);
                            }

                            BlockEntity typePawn = pLevel.getBlockEntity(pPos);
                            if (typePawn instanceof PawnBlockEntity pawnBlockEntity) {
                                pawnBlockEntity.addToList(offsetPos);
                            }
                        }
                    }
                }

                BearstersChessMod.LOGGER.info("PLACED FIRST ATTACK");

                if (pState.getValue(FACING) == Direction.NORTH) {

                    offsetPos = pPos.offset(1,1,-1);

                    if (pLevel.getBlockState(offsetPos.offset(0,-1,0)).is(ModTags.Blocks.IS_CHESS_PIECE) &
                            pLevel.getBlockState(offsetPos).is(Blocks.AIR)) {

                        if (pLevel.getBlockState(offsetPos.offset(0,-1,0)).getValue(COLOUR) == !pLevel.getBlockState(pPos).getValue(COLOUR)) {

                            pLevel.setBlockAndUpdate(offsetPos, ModBlocks.ATTACKABLE_SQUARE.get().defaultBlockState());

                            BlockEntity blockEntity = pLevel.getBlockEntity(offsetPos);
                            if (blockEntity instanceof AttackableSquareBlockEntity attackableSquareBlockEntity) {
                                attackableSquareBlockEntity.setPiecePosition(pPos);
                            }

                            BlockEntity typePawn = pLevel.getBlockEntity(pPos);
                            if (typePawn instanceof PawnBlockEntity pawnBlockEntity) {
                                pawnBlockEntity.addToList(offsetPos);
                            }
                        }
                    }

                } else if (pState.getValue(FACING) == Direction.SOUTH) {

                    offsetPos = pPos.offset(-1,1,1);

                    if (pLevel.getBlockState(offsetPos.offset(0,-1,0)).is(ModTags.Blocks.IS_CHESS_PIECE) &
                            pLevel.getBlockState(offsetPos).is(Blocks.AIR)) {

                        if (pLevel.getBlockState(offsetPos.offset(0,-1,0)).getValue(COLOUR) == !pLevel.getBlockState(pPos).getValue(COLOUR)) {

                            pLevel.setBlockAndUpdate(offsetPos, ModBlocks.ATTACKABLE_SQUARE.get().defaultBlockState());

                            BlockEntity blockEntity = pLevel.getBlockEntity(offsetPos);
                            if (blockEntity instanceof AttackableSquareBlockEntity attackableSquareBlockEntity) {
                                attackableSquareBlockEntity.setPiecePosition(pPos);
                            }

                            BlockEntity typePawn = pLevel.getBlockEntity(pPos);
                            if (typePawn instanceof PawnBlockEntity pawnBlockEntity) {
                                pawnBlockEntity.addToList(offsetPos);
                            }
                        }
                    }

                } else if (pState.getValue(FACING) == Direction.EAST) {

                    offsetPos = pPos.offset(1,1,1);

                    if (pLevel.getBlockState(offsetPos.offset(0,-1,0)).is(ModTags.Blocks.IS_CHESS_PIECE) &
                            pLevel.getBlockState(offsetPos).is(Blocks.AIR)) {

                        if (pLevel.getBlockState(offsetPos.offset(0,-1,0)).getValue(COLOUR) == !pLevel.getBlockState(pPos).getValue(COLOUR)) {

                            pLevel.setBlockAndUpdate(offsetPos, ModBlocks.ATTACKABLE_SQUARE.get().defaultBlockState());

                            BlockEntity blockEntity = pLevel.getBlockEntity(offsetPos);
                            if (blockEntity instanceof AttackableSquareBlockEntity attackableSquareBlockEntity) {
                                attackableSquareBlockEntity.setPiecePosition(pPos);
                            }

                            BlockEntity typePawn = pLevel.getBlockEntity(pPos);
                            if (typePawn instanceof PawnBlockEntity pawnBlockEntity) {
                                pawnBlockEntity.addToList(offsetPos);
                            }
                        }
                    }

                } else if (pState.getValue(FACING) == Direction.WEST) {

                    offsetPos = pPos.offset(-1,1,-1);

                    if (pLevel.getBlockState(offsetPos.offset(0,-1,0)).is(ModTags.Blocks.IS_CHESS_PIECE) &
                            pLevel.getBlockState(offsetPos).is(Blocks.AIR)) {

                        if (pLevel.getBlockState(offsetPos.offset(0,-1,0)).getValue(COLOUR) == !pLevel.getBlockState(pPos).getValue(COLOUR)) {

                            pLevel.setBlockAndUpdate(offsetPos, ModBlocks.ATTACKABLE_SQUARE.get().defaultBlockState());

                            BlockEntity blockEntity = pLevel.getBlockEntity(offsetPos);
                            if (blockEntity instanceof AttackableSquareBlockEntity attackableSquareBlockEntity) {
                                attackableSquareBlockEntity.setPiecePosition(pPos);
                            }

                            BlockEntity typePawn = pLevel.getBlockEntity(pPos);
                            if (typePawn instanceof PawnBlockEntity pawnBlockEntity) {
                                pawnBlockEntity.addToList(offsetPos);
                            }
                        }
                    }

                }

            }
        }

        return ItemInteractionResult.SUCCESS;
    }
}
