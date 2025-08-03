package net.bearster.bearsterschessmod.block.custom;

import net.bearster.bearsterschessmod.BearstersChessMod;
import net.bearster.bearsterschessmod.block.ModBlocks;
import net.bearster.bearsterschessmod.block.entity.custom.ChessPieceBlockEntity;
import net.bearster.bearsterschessmod.block.entity.custom.MoveableSquareBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PromotionBlock extends ChessPieceBlock  {
    public static final VoxelShape SHAPE = Block.box(4, 4, 4, 12, 12, 12);

    public PromotionBlock(Properties properties) {
        super(properties);
    }


    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (!pLevel.isClientSide()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof ChessPieceBlockEntity chessPieceBlockEntity) {
                switch (pPlayer.getDirection()) {
                    case SOUTH -> chessPieceBlockEntity.setPieceToPromoteTo(ModBlocks.KNIGHT.get().toString());
                    case EAST -> chessPieceBlockEntity.setPieceToPromoteTo(ModBlocks.BISHOP.get().toString());
                    case NORTH -> chessPieceBlockEntity.setPieceToPromoteTo(ModBlocks.ROOK.get().toString());
                    case WEST -> chessPieceBlockEntity.setPieceToPromoteTo(ModBlocks.QUEEN.get().toString());
                }
            }
        }
        return ItemInteractionResult.SUCCESS;
    }
}
