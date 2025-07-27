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
import net.minecraft.world.item.component.FireworkExplosion;
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
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof AttackableSquareBlockEntity attackableSquareBlockEntity) {
                BlockPos storedPos = attackableSquareBlockEntity.getPiecePosition();
                BlockState newBlockstate = pLevel.getBlockState(storedPos);

                BlockEntity takeKing = pLevel.getBlockEntity(pPos.offset(0,-1,0));
                if (takeKing instanceof KingBlockEntity kingBlockEntity) {
                    if (newBlockstate.getValue(ChessPieceBlock.COLOUR)) {
                        pPlayer.sendSystemMessage(Component.translatable("message.bearsterschessmod.win_game_white"));
                    } else {
                        pPlayer.sendSystemMessage(Component.translatable("message.bearsterschessmod.win_game_black"));
                    }
                }

                BlockEntity typePawn = pLevel.getBlockEntity(storedPos);
                if (typePawn instanceof PawnBlockEntity pawnBlockEntity) {
                    List<Integer> xList = pawnBlockEntity.getXList();
                    List<Integer> yList = pawnBlockEntity.getYList();
                    List<Integer> zList = pawnBlockEntity.getZList();

                    BearstersChessMod.LOGGER.info("x: "+xList);
                    BearstersChessMod.LOGGER.info("y: "+yList);
                    BearstersChessMod.LOGGER.info("z: "+zList);

                    for (int i = 0; i <
                            (xList.size() + yList.size() + zList.size()) / 3;
                         i++) {
                        pLevel.setBlockAndUpdate(new BlockPos(xList.get(i),yList.get(i),zList.get(i)), Blocks.AIR.defaultBlockState());
                    }

                    pawnBlockEntity.resetList();
                }

                BlockEntity typeBishop = pLevel.getBlockEntity(storedPos);
                if (typeBishop instanceof BishopBlockEntity bishopBlockEntity) {
                    List<Integer> xList = bishopBlockEntity.getXList();
                    List<Integer> yList = bishopBlockEntity.getYList();
                    List<Integer> zList = bishopBlockEntity.getZList();

                    BearstersChessMod.LOGGER.info("x: "+xList);
                    BearstersChessMod.LOGGER.info("y: "+yList);
                    BearstersChessMod.LOGGER.info("z: "+zList);

                    for (int i = 0; i <
                            (xList.size() + yList.size() + zList.size()) / 3;
                         i++) {
                        pLevel.setBlockAndUpdate(new BlockPos(xList.get(i),yList.get(i),zList.get(i)), Blocks.AIR.defaultBlockState());
                    }

                    bishopBlockEntity.resetList();
                }

                BlockEntity typeQueen = pLevel.getBlockEntity(storedPos);
                if (typeQueen instanceof QueenBlockEntity queenBlockEntity) {
                    List<Integer> xList = queenBlockEntity.getXList();
                    List<Integer> yList = queenBlockEntity.getYList();
                    List<Integer> zList = queenBlockEntity.getZList();

                    BearstersChessMod.LOGGER.info("x: "+xList);
                    BearstersChessMod.LOGGER.info("y: "+yList);
                    BearstersChessMod.LOGGER.info("z: "+zList);

                    for (int i = 0; i <
                            (xList.size() + yList.size() + zList.size()) / 3;
                         i++) {
                        pLevel.setBlockAndUpdate(new BlockPos(xList.get(i),yList.get(i),zList.get(i)), Blocks.AIR.defaultBlockState());
                    }

                    queenBlockEntity.resetList();
                }

                BlockEntity typeRook = pLevel.getBlockEntity(storedPos);
                if (typeRook instanceof RookBlockEntity rookBlockEntity) {
                    List<Integer> xList = rookBlockEntity.getXList();
                    List<Integer> yList = rookBlockEntity.getYList();
                    List<Integer> zList = rookBlockEntity.getZList();

                    BearstersChessMod.LOGGER.info("x: "+xList);
                    BearstersChessMod.LOGGER.info("y: "+yList);
                    BearstersChessMod.LOGGER.info("z: "+zList);

                    for (int i = 0; i <
                            (xList.size() + yList.size() + zList.size()) / 3;
                         i++) {
                        pLevel.setBlockAndUpdate(new BlockPos(xList.get(i),yList.get(i),zList.get(i)), Blocks.AIR.defaultBlockState());
                    }

                    rookBlockEntity.resetList();
                }

                BlockEntity typeKnight = pLevel.getBlockEntity(storedPos);
                if (typeKnight instanceof KnightBlockEntity knightBlockEntity) {
                    List<Integer> xList = knightBlockEntity.getXList();
                    List<Integer> yList = knightBlockEntity.getYList();
                    List<Integer> zList = knightBlockEntity.getZList();

                    BearstersChessMod.LOGGER.info("x: "+xList);
                    BearstersChessMod.LOGGER.info("y: "+yList);
                    BearstersChessMod.LOGGER.info("z: "+zList);

                    for (int i = 0; i <
                            (xList.size() + yList.size() + zList.size()) / 3;
                         i++) {
                        pLevel.setBlockAndUpdate(new BlockPos(xList.get(i),yList.get(i),zList.get(i)), Blocks.AIR.defaultBlockState());
                    }

                    knightBlockEntity.resetList();
                }

                BlockEntity typeKing = pLevel.getBlockEntity(storedPos);
                if (typeKing instanceof KingBlockEntity kingBlockEntity) {
                    List<Integer> xList = kingBlockEntity.getXList();
                    List<Integer> yList = kingBlockEntity.getYList();
                    List<Integer> zList = kingBlockEntity.getZList();

                    BearstersChessMod.LOGGER.info("x: "+xList);
                    BearstersChessMod.LOGGER.info("y: "+yList);
                    BearstersChessMod.LOGGER.info("z: "+zList);

                    for (int i = 0; i <
                            (xList.size() + yList.size() + zList.size()) / 3;
                         i++) {
                        pLevel.setBlockAndUpdate(new BlockPos(xList.get(i),yList.get(i),zList.get(i)), Blocks.AIR.defaultBlockState());
                    }

                    kingBlockEntity.resetList();
                }

                pLevel.setBlock(pPos.offset(0,-1,0), newBlockstate, 3);

                BlockEntity blockEntity2 = pLevel.getBlockEntity(pPos.offset(0,-1,0));
                if (blockEntity2 instanceof PawnBlockEntity pawnBlockEntity) {
                    pawnBlockEntity.setHasDoubleMoved(true);

                    BlockState pawnBlockState = pLevel.getBlockState(pPos.offset(0,-1,0));
                    if (pawnBlockState.is(ModBlocks.PAWN.get())) {
                        Direction facing = pawnBlockState.getValue(PawnBlock.FACING);

                        if (!pLevel.getBlockState(pPos.offset(0,-2,0).relative(facing)).is(ModTags.Blocks.IS_CHESS_BOARD)) {
                            pLevel.setBlock(pPos.offset(0,-1,0),
                                    ModBlocks.QUEEN.get().defaultBlockState()
                                            .setValue(QueenBlock.COLOUR, newBlockstate.getValue(PawnBlock.COLOUR))
                                            .setValue(QueenBlock.FACING,newBlockstate.getValue(PawnBlock.FACING))
                                    ,3);
                        }
                    }
                }


                pLevel.setBlockAndUpdate(storedPos, Blocks.AIR.defaultBlockState());
                pLevel.setBlockAndUpdate(pPos, Blocks.AIR.defaultBlockState());

            }

        }
        return InteractionResult.SUCCESS;
    }

}
