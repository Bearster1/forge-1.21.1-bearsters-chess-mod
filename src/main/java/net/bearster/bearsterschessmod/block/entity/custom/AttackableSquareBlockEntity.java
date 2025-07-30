package net.bearster.bearsterschessmod.block.entity.custom;

import net.bearster.bearsterschessmod.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AttackableSquareBlockEntity extends BlockEntity {
    private BlockPos piecePosition = BlockPos.ZERO;
    private BlockPos enPassantPosition = BlockPos.ZERO;
    private boolean enPassant = false;
    private boolean isDoubleMovePawn = false;

    public AttackableSquareBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ATTACKABLE_SQUARE_BE.get(), pos, state);
    }

    public BlockPos getPiecePosition() {
        return piecePosition;
    }

    public void setPiecePosition(BlockPos pos) {
        this.piecePosition = pos;
        setChanged();
    }

    public BlockPos getEnPassantPosition() {
        return enPassantPosition;
    }

    public void setEnPassantPosition(BlockPos pos) {
        this.enPassantPosition = pos;
        setChanged();
    }

    public boolean isEnPassant() {
        return enPassant;
    }

    public void setEnPassant(boolean enPassant) {
        this.enPassant = enPassant;
        setChanged();
    }

    public boolean isDoubleMovePawn() {
        return isDoubleMovePawn;
    }

    public void setDoubleMovePawn(boolean doubleMovePawn) {
        isDoubleMovePawn = doubleMovePawn;
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        pTag.putInt("pieceX", piecePosition.getX());
        pTag.putInt("pieceY", piecePosition.getY());
        pTag.putInt("pieceZ", piecePosition.getZ());
        pTag.putInt("enPassantX", enPassantPosition.getX());
        pTag.putInt("enPassantY", enPassantPosition.getY());
        pTag.putInt("enPassantZ", enPassantPosition.getZ());
        pTag.putBoolean("enPassant", this.enPassant);
        pTag.putBoolean("isDoubleMovePawn", this.isDoubleMovePawn);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        this.piecePosition = new BlockPos(pTag.getInt("pieceX"), pTag.getInt("pieceY"), pTag.getInt("pieceZ"));
        this.enPassantPosition = new BlockPos(pTag.getInt("enPassantX"), pTag.getInt("enPassantY"), pTag.getInt("enPassantZ"));
        this.enPassant = pTag.getBoolean("enPassant");
        this.isDoubleMovePawn = pTag.getBoolean("isDoubleMovePawn");
    }
}

