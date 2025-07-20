package net.bearster.bearsterschessmod.item;

import net.bearster.bearsterschessmod.BearstersChessMod;
import net.bearster.bearsterschessmod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BearstersChessMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> BEARSTERS_CHESS_MOD_TAB = CREATIVE_MODE_TABS.register("bearsters_chess_mod_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.WHITE_SQUARE.get()))
                    .title(Component.translatable("creativetab.bearsters_chess_mod_tab"))
                    .displayItems((itemDisplayParameters, output) -> {

                        output.accept(ModBlocks.WHITE_SQUARE.get());
                        output.accept(ModBlocks.BLACK_SQUARE.get());
                        output.accept(ModBlocks.WHITE_KNIGHT.get());
                        output.accept(ModBlocks.BLACK_KNIGHT.get());
                        /*
                        output.accept(ModBlocks.WHITE_PAWN.get());
                        output.accept(ModBlocks.BLACK_PAWN.get());

                         */
                        output.accept(ModBlocks.PAWN.get());
                        output.accept(ModBlocks.WHITE_ROOK.get());
                        output.accept(ModBlocks.BLACK_ROOK.get());
                        output.accept(ModBlocks.WHITE_BISHOP.get());
                        output.accept(ModBlocks.BLACK_BISHOP.get());
                        output.accept(ModBlocks.WHITE_QUEEN.get());
                        output.accept(ModBlocks.BLACK_QUEEN.get());
                        output.accept(ModBlocks.WHITE_KING.get());
                        output.accept(ModBlocks.BLACK_KING.get());
                        output.accept(ModBlocks.MOVEABLE_SQUARE.get());


                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
