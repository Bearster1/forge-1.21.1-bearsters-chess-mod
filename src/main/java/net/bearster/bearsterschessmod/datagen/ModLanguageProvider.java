package net.bearster.bearsterschessmod.datagen;

import com.mojang.logging.LogUtils;
import net.bearster.bearsterschessmod.BearstersChessMod;
import net.bearster.bearsterschessmod.block.ModBlocks;
import net.bearster.bearsterschessmod.item.ModCreativeModeTabs;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.data.LanguageProvider;
import org.slf4j.Logger;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output) {
        super(output, BearstersChessMod.MOD_ID, "en_us");
    }

    public static final Logger LOGGER = LogUtils.getLogger();

    @Override
    protected void addTranslations() {
        add(ModBlocks.WHITE_SQUARE.get(), "White Square");
        add(ModBlocks.BLACK_SQUARE.get(), "Black Square");
        add(ModBlocks.KNIGHT.get(), "Knight");
        add(ModBlocks.PAWN.get(), "Pawn");
        add(ModBlocks.ROOK.get(), "Rook");
        add(ModBlocks.BISHOP.get(), "Bishop");
        add(ModBlocks.QUEEN.get(), "Queen");
        add(ModBlocks.KING.get(), "King");
        add(ModBlocks.MOVEABLE_SQUARE.get(), "Moveable Square");
        add(ModBlocks.ATTACKABLE_SQUARE.get(), "Attackable Square");
        add(ModBlocks.OPTION.get(), "Option Block");

        add("message.bearsterschessmod.win_game_white","White has won the game!");
        add("message.bearsterschessmod.win_game_black","Black has won the game!");

        add("message.bearsterschessmod.forced_turn_taking_on","Forced Turn Taking Has Been Turned On");
        add("message.bearsterschessmod.forced_turn_taking_off","Forced Turn Taking Has Been Turned Off");
        add("message.bearsterschessmod.game_started","The Chess Game Has Started, White Goes First");

        add("tooltip.bearsterschessmod.right_click_to_start_game","Right Click This Block To Begin The Chess Game");
        add("tooltip.bearsterschessmod.shift_right_click_to_toggle_color_boundary","Shift Right Click This Block To Toggle Taking Turns");

        add("menu.bearsterschessmod.promotion_menu","Promotion");

        addCreativeTabType(ModCreativeModeTabs.BEARSTERS_CHESS_MOD_TAB.get(), "Bearster's Chess Mod");
    }

    public void addCreativeTabType(CreativeModeTab creativeModeTab, String name) {
        add(creativeModeTab.getDisplayName().getString(), name);
    }

    public void addMusicDiscType(SoundEvent songIdentifier, String songName, String description) {
        String song = songIdentifier.getLocation().toString();
        String newSong = song.replace(':','.');
        add("item."+newSong+"_music_disc", songName+" Music Disc");
        add("jukebox_song."+newSong, description);

    }

    public void addSoundType(SoundEvent soundIdentifier, String soundName) {
        String sound = soundIdentifier.getLocation().toString();
        String newSound = sound.replace(':','.');
        add("sounds."+newSound, soundName);

    }

    public void addPaintingType(ResourceKey paintingVariants, String paintingName, String paintingAuthor) {
        String painting = paintingVariants.location().toString();
        String newPainting = painting.replace(':','.');
        add("painting."+newPainting+".title", paintingName);
        add("painting."+newPainting+".author", paintingAuthor);

    }

    public void addNameType(String keyName, String translatedName) {
        add("name.jojojetzmod."+keyName, translatedName);
    }

    public void addAdvancementType(String keyName,
                                   String translatedTitle,
                                   String translatedDescription) {
        add("advancements.jojojetzmod."+keyName+".title", translatedTitle);
        add("advancements.jojojetzmod."+keyName+".description", translatedDescription);
    }
}
