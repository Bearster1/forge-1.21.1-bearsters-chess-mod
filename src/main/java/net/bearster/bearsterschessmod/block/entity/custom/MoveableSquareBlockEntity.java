package net.bearster.bearsterschessmod.block.entity.custom;

import net.bearster.bearsterschessmod.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MoveableSquareBlockEntity extends BlockEntity {
    private BlockPos piecePosition = BlockPos.ZERO;
    private Boolean doubleMovedWithPawnLast = false;

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

    public Boolean getDoubleMovedWithPawnLast() {
        return doubleMovedWithPawnLast;
    }

    public void setDoubleMovedWithPawnLast(Boolean doubleMovedWithPawnLast) {
        this.doubleMovedWithPawnLast = doubleMovedWithPawnLast;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        pTag.putInt("pieceX", piecePosition.getX());
        pTag.putInt("pieceY", piecePosition.getY());
        pTag.putInt("pieceZ", piecePosition.getZ());
        pTag.putBoolean("doubleMovedWithPawn", doubleMovedWithPawnLast);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        this.piecePosition = new BlockPos(pTag.getInt("pieceX"), pTag.getInt("pieceY"), pTag.getInt("pieceZ"));
        this.doubleMovedWithPawnLast = pTag.getBoolean("doubleMovedWithPawn");
    }
}

