package net.bearster.bearsterschessmod.block.custom;

import net.bearster.bearsterschessmod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class QueenBlock extends RotationalBlock {
    public static final VoxelShape SHAPE = Block.box(3, 0, 3, 13, 16, 13);
    public static final BooleanProperty COLOUR = BooleanProperty.create("colour");

    public QueenBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(COLOUR, true));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(COLOUR);
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }
/*
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

 */

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {

        if (!pLevel.isClientSide()) {
            boolean colour = pState.getValue(COLOUR);
            if (pStack.is(ModBlocks.WHITE_SQUARE.get().asItem()) && !colour) {
                pLevel.setBlock(pPos, pState.setValue(COLOUR, !colour), 3);
            } else if (pStack.is(ModBlocks.BLACK_SQUARE.get().asItem()) && colour) {
                pLevel.setBlock(pPos, pState.setValue(COLOUR, !colour), 3);
            }
        }

        return ItemInteractionResult.SUCCESS;

    }
}
