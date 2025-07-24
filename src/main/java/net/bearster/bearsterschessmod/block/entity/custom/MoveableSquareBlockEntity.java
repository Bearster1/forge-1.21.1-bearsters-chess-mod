package net.bearster.bearsterschessmod.block.entity.custom;

import net.bearster.bearsterschessmod.BearstersChessMod;
import net.bearster.bearsterschessmod.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class MoveableSquareBlockEntity extends BlockEntity {
    private BlockPos piecePosition = BlockPos.ZERO;

    public MoveableSquareBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MOVEABLE_SQUARE_BE.get(), pos, state);
    }

    public BlockPos getPiecePosition() {
        return piecePosition;
    }

    public void setPiecePosition(BlockPos pos) {
        this.piecePosition = pos;
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        pTag.putInt("pieceX", piecePosition.getX());
        pTag.putInt("pieceY", piecePosition.getY());
        pTag.putInt("pieceZ", piecePosition.getZ());
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        this.piecePosition = new BlockPos(pTag.getInt("pieceX"), pTag.getInt("pieceY"), pTag.getInt("pieceZ"));
    }
}

