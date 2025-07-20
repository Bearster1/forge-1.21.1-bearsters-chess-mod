package net.bearster.bearsterschessmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BishopBlock extends RotationalBlock {
    public static final VoxelShape SHAPE = Block.box(3, 0, 3, 13, 15, 13);
    private String COLOUR;

    public BishopBlock(Properties properties) {
        super(properties);
    }

    public BishopBlock(Properties properties, String colour) {
        super(properties);
        this.COLOUR = colour;
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

}
