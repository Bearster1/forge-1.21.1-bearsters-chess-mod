package net.bearster.bearsterschessmod.block.custom;

import net.bearster.bearsterschessmod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;

public class ChessPieceBlock extends RotationalBlock {
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
