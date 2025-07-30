package net.bearster.bearsterschessmod.block.custom;

import net.bearster.bearsterschessmod.BearstersChessMod;
import net.bearster.bearsterschessmod.block.ModBlocks;
import net.bearster.bearsterschessmod.block.entity.custom.ChessPieceBlockEntity;
import net.bearster.bearsterschessmod.screen.custom.PromotionScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChessPieceBlock extends RotationalBlock implements EntityBlock {
    public static final BooleanProperty COLOUR = BooleanProperty.create("colour");

    public ChessPieceBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(COLOUR, true));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(COLOUR);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ChessPieceBlockEntity(pPos, pState);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {

        if (!pLevel.isClientSide()) {
            BlockEntity blocke = pLevel.getBlockEntity(pPos);
            if (blocke instanceof ChessPieceBlockEntity ChessPieceBE) {
                if (!ChessPieceBE.getYList().isEmpty() && !ChessPieceBE.getXList().isEmpty() && !ChessPieceBE.getZList().isEmpty()) {
                    List<Integer> xList = ChessPieceBE.getXList();
                    List<Integer> yList = ChessPieceBE.getYList();
                    List<Integer> zList = ChessPieceBE.getZList();

                    BearstersChessMod.LOGGER.info("x: "+xList);
                    BearstersChessMod.LOGGER.info("y: "+yList);
                    BearstersChessMod.LOGGER.info("z: "+zList);

                    for (int i = 0; i <
                            (xList.size() + yList.size() + zList.size()) / 3;
                         i++) {
                        pLevel.setBlockAndUpdate(new BlockPos(xList.get(i),yList.get(i),zList.get(i)), Blocks.AIR.defaultBlockState());
                    }

                    ChessPieceBE.resetList();

                    xList = ChessPieceBE.getXList();
                    yList = ChessPieceBE.getYList();
                    zList = ChessPieceBE.getZList();

                    BearstersChessMod.LOGGER.info("x: "+xList);
                    BearstersChessMod.LOGGER.info("y: "+yList);
                    BearstersChessMod.LOGGER.info("z: "+zList);
                }
            }

            boolean colour = pState.getValue(COLOUR);
            if (pStack.is(ModBlocks.WHITE_SQUARE.get().asItem()) && !colour) {
                pLevel.setBlock(pPos, pState.setValue(COLOUR, !colour), 3);
                pStack.shrink(1);
            } else if (pStack.is(ModBlocks.BLACK_SQUARE.get().asItem()) && colour) {
                pLevel.setBlock(pPos, pState.setValue(COLOUR, !colour), 3);
                pStack.shrink(1);
            }
            BearstersChessMod.LOGGER.info("Logger: This is code In the Chess Piece Block");
        }

        return ItemInteractionResult.SUCCESS;

    }
}
