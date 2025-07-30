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
    private String castleDirection = "null";
    private BlockPos castlePosition = BlockPos.ZERO;

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

    public String getCastleDirection() {
        return castleDirection;
    }

    public void setCastleDirection(String castleDirection) {
        this.castleDirection = castleDirection;
    }

    public BlockPos getCastlePosition() {
        return castlePosition;
    }

    public void setCastlePosition(BlockPos castlePosition) {
        this.castlePosition = castlePosition;
        setChanged();
    }

    public Boolean getDoubleMovedWithPawnLast() {
        return doubleMovedWithPawnLast;
    }

    public void setDoubleMovedWithPawnLast(Boolean doubleMovedWithPawnLast) {
        this.doubleMovedWithPawnLast = doubleMovedWithPawnLast;
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        pTag.putInt("pieceX", piecePosition.getX());
        pTag.putInt("pieceY", piecePosition.getY());
        pTag.putInt("pieceZ", piecePosition.getZ());
        pTag.putInt("castlePosX", castlePosition.getX());
        pTag.putInt("castlePosY", castlePosition.getY());
        pTag.putInt("castlePosZ", castlePosition.getZ());
        pTag.putBoolean("doubleMovedWithPawn", doubleMovedWithPawnLast);
        pTag.putString("castleDirection", castleDirection);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        this.piecePosition = new BlockPos(pTag.getInt("pieceX"), pTag.getInt("pieceY"), pTag.getInt("pieceZ"));
        this.castlePosition = new BlockPos(pTag.getInt("castlePosX"), pTag.getInt("castlePosY"), pTag.getInt("castlePosZ"));
        this.doubleMovedWithPawnLast = pTag.getBoolean("doubleMovedWithPawn");
        this.castleDirection = pTag.getString("castleDirection");
    }
}

