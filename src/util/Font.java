package util;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import util.exceptions.UncheckedDemigodsException;

import java.util.ArrayList;

public class Font {

    public static enum FontType {
        NORMAL,
        RENDERED
    }

    private static enum FontColor {
        NO_COLOR,
        RED,
        WHITE,
        BLACK
    }


    private static ArrayList<UnicodeFont> monospacedFonts = new ArrayList<UnicodeFont>();
    private static AngelCodeFont[][][] dkNorthumbria = new AngelCodeFont[6][4][2];
    private static AngelCodeFont[][][] triniganFg = new AngelCodeFont[6][4][2];

    /**
     * This method will draw a normal type DK Northumbria string with the specified size, color, and location.
     * @param text the string to be drawn
     * @param x the x coordinate of the drawing location
     * @param y the y coordinate of the drawing location
     * @param size the size of the font to be drawn (only 6 available)
     * @param color the color of the font to be drawn (only 3 available)
     */
    public static void drawDkNorthumbriaString(String text, float x, float y, int size, Color color) {
        drawDkNorthumbriaString(text, x, y, size, color, FontType.NORMAL);
    }

    /**
     * This method will draw a DK Northumbria string with the specified size, color, location, and type.
     * @param text the string to be drawn
     * @param x the x coordinate of the drawing location
     * @param y the y coordinate of the drawing location
     * @param size the size of the font to be drawn (only 6 available)
     * @param color the color of the font to be drawn (only 3 available)
     * @param type the type of the font to be drawn (rendered or normal)
     */
    public static void drawDkNorthumbriaString(String text, float x, float y, int size, Color color, FontType type) {
        if (type == null) type = FontType.NORMAL;
        int sizeIndex = getDKNIndexFromFontSize(size);
        FontColor fontColor;
        if (type == FontType.RENDERED) {
            if (sizeIndex < 3)
                throw new UncheckedDemigodsException("There is no rendered font in that size. Please try font sizes 40, 45, 50");
            else fontColor = FontColor.NO_COLOR;
        } else {
            fontColor = getFontColorFromColor(color);
        }
        if (dkNorthumbria[sizeIndex][fontColor.ordinal()][type.ordinal()] == null)
            initializeDKNFont(sizeIndex, fontColor, type);
        dkNorthumbria[sizeIndex][fontColor.ordinal()][type.ordinal()].drawString(x, y, text);
    }

    /**
     * This method will draw a normal type Trinigan FG string with the specified size, color, and location.
     * @param text the string to be drawn
     * @param x the x coordinate of the drawing location
     * @param y the y coordinate of the drawing location
     * @param size the size of the font to be drawn (only 6 available)
     * @param color the color of the font to be drawn (only 3 available)
     */
    public static void drawTriniganFgString(String text, float x, float y, int size, Color color) {
        drawTriniganFgString(text, x, y, size, color, FontType.NORMAL);
    }

    /**
     * This method will draw a Trinigan FG string with the specified size, color, location, and type.
     * @param text the string to be drawn
     * @param x the x coordinate of the drawing location
     * @param y the y coordinate of the drawing location
     * @param size the size of the font to be drawn (only 6 available)
     * @param color the color of the font to be drawn (only 3 available)
     * @param type the type of the font to be drawn (rendered or normal)
     */
    public static void drawTriniganFgString(String text, float x, float y, int size, Color color, FontType type) {
        if (type == null) type = FontType.NORMAL;
        int sizeIndex = getTFGIndexFromFontSize(size);
        FontColor fontColor;
        if (type == FontType.RENDERED) {
            if (sizeIndex < 4)
                throw new UncheckedDemigodsException("There is no rendered font in that size. Please try font sizes 24, 30");
            else fontColor = FontColor.NO_COLOR;
        } else {
            fontColor = getFontColorFromColor(color);
        }
        if (triniganFg[sizeIndex][fontColor.ordinal()][type.ordinal()] == null)
            initializeTFGFont(sizeIndex, fontColor, type);
        triniganFg[sizeIndex][fontColor.ordinal()][type.ordinal()].drawString(x, y, text);
    }

    /**
     * This method will draw a monospaced string with the specified size, color, and location.
     * @param text the string to be drawn
     * @param x the x coordinate of the drawing location
     * @param y the u coordinate of the drawing location
     * @param size the size of the font to be drawn (any size)
     * @param color the color of the font to be drawn (any color)
     */
    @SuppressWarnings("unchecked")
    public static void drawMonospacedFontString(String text, float x, float y, int size, Color color) {
        UnicodeFont font = null;
        for (UnicodeFont f : monospacedFonts) {
            if (f.getFont().getSize() == size) {
                font = f;
                break;
            }
        }

        if (font == null) {
            try {
                font = new UnicodeFont(new java.awt.Font("Monospaced", 0, 1), size, false, false);
                font.addGlyphs(new String(Constants.acceptedCharacters));
                font.getEffects().add(new ColorEffect());
                font.loadGlyphs();
                monospacedFonts.add(font);
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }

        font.drawString(x, y, text, color);
    }

    private static int getDKNIndexFromFontSize(int size) {
        if (size == 25) return 0;
        if (size == 30) return 1;
        if (size == 35) return 2;
        if (size == 40) return 3;
        if (size == 45) return 4;
        if (size == 50) return 5;
        throw new UncheckedDemigodsException(size + " is an invalid DK Northumbria font size. Please choose from the following: 25, 30, 35, 40, 45, 50");
    }

    private static int getTFGIndexFromFontSize(int size) {
        if (size == 10) return 0;
        if (size == 12) return 1;
        if (size == 16) return 2;
        if (size == 20) return 3;
        if (size == 24) return 4;
        if (size == 30) return 5;
        throw new UncheckedDemigodsException(size + " is an invalid Trinigan FG font size. Please choose from the following: 10, 12, 16, 20, 24, 30");
    }

    private static FontColor getFontColorFromColor(Color color) {
        if (color.equals(Color.red)) return FontColor.RED;
        if (color.equals(Color.black)) return FontColor.BLACK;
        if (color.equals(Color.white)) return FontColor.WHITE;
        throw new UncheckedDemigodsException("The color " + color + "is not supported. Please try red, white, or black");
    }

    private static void initializeDKNFont(int size, FontColor color, FontType type) {
        try {
            switch (size) {
                case 0:
                    switch (color) {
                        case RED:
                            dkNorthumbria[size][color.ordinal()][0] = new AngelCodeFont("fnt/DK_Northumbria_25_red.fnt", "fnt/DK_Northumbria_25_red.png");
                            break;
                        case WHITE:
                            dkNorthumbria[size][color.ordinal()][0] = new AngelCodeFont("fnt/DK_Northumbria_25_white.fnt", "fnt/DK_Northumbria_25_white.png");
                            break;
                        case BLACK:
                            dkNorthumbria[size][color.ordinal()][0] = new AngelCodeFont("fnt/DK_Northumbria_25_black.fnt", "fnt/DK_Northumbria_25_black.png");
                    }
                    break;
                case 1:
                    switch (color) {
                        case RED:
                            dkNorthumbria[size][color.ordinal()][0] = new AngelCodeFont("fnt/DK_Northumbria_30_red.fnt", "fnt/DK_Northumbria_30_red.png");
                            break;
                        case WHITE:
                            dkNorthumbria[size][color.ordinal()][0] = new AngelCodeFont("fnt/DK_Northumbria_30_white.fnt", "fnt/DK_Northumbria_30_white.png");
                            break;
                        case BLACK:
                            dkNorthumbria[size][color.ordinal()][0] = new AngelCodeFont("fnt/DK_Northumbria_30_black.fnt", "fnt/DK_Northumbria_30_black.png");
                            break;
                    }
                    break;
                case 2:
                    switch (color) {
                        case RED:
                            dkNorthumbria[size][color.ordinal()][0] = new AngelCodeFont("fnt/DK_Northumbria_35_red.fnt", "fnt/DK_Northumbria_35_red.png");
                            break;
                        case WHITE:
                            dkNorthumbria[size][color.ordinal()][0] = new AngelCodeFont("fnt/DK_Northumbria_35_white.fnt", "fnt/DK_Northumbria_35_white.png");
                            break;
                        case BLACK:
                            dkNorthumbria[size][color.ordinal()][0] = new AngelCodeFont("fnt/DK_Northumbria_35_black.fnt", "fnt/DK_Northumbria_35_black.png");
                            break;
                    }
                    break;
                case 3:
                    switch (color) {
                        case RED:
                            dkNorthumbria[size][color.ordinal()][0] = new AngelCodeFont("fnt/DK_Northumbria_40_red.fnt", "fnt/DK_Northumbria_40_red.png");
                            break;
                        case WHITE:
                            dkNorthumbria[size][color.ordinal()][0] = new AngelCodeFont("fnt/DK_Northumbria_40_white.fnt", "fnt/DK_Northumbria_40_white.png");
                            break;
                        case BLACK:
                            dkNorthumbria[size][color.ordinal()][0] = new AngelCodeFont("fnt/DK_Northumbria_40_black.fnt", "fnt/DK_Northumbria_40_black.png");
                            break;
                        case NO_COLOR:
                            switch (type) {
                                case RENDERED:
                                    dkNorthumbria[size][color.ordinal()][type.ordinal()] = new AngelCodeFont("fnt/DK_Northumbria_40_rendered.fnt", "fnt/DK_Northumbria_40_rendered.png");
                                    break;
                            }
                    }
                    break;
                case 4:
                    switch (color) {
                        case RED:
                            dkNorthumbria[size][color.ordinal()][0] = new AngelCodeFont("fnt/DK_Northumbria_45_red.fnt", "fnt/DK_Northumbria_45_red.png");
                            break;
                        case WHITE:
                            dkNorthumbria[size][color.ordinal()][0] = new AngelCodeFont("fnt/DK_Northumbria_45_white.fnt", "fnt/DK_Northumbria_45_white.png");
                            break;
                        case BLACK:
                            dkNorthumbria[size][color.ordinal()][0] = new AngelCodeFont("fnt/DK_Northumbria_45_black.fnt", "fnt/DK_Northumbria_45_black.png");
                            break;
                        case NO_COLOR:
                            switch (type) {
                                case RENDERED:
                                    dkNorthumbria[size][color.ordinal()][type.ordinal()] = new AngelCodeFont("fnt/DK_Northumbria_45_rendered.fnt", "fnt/DK_Northumbria_45_rendered.png");
                                    break;
                            }
                    }
                    break;
                case 5:
                    switch (color) {
                        case RED:
                            dkNorthumbria[size][color.ordinal()][0] = new AngelCodeFont("fnt/DK_Northumbria_50_red.fnt", "fnt/DK_Northumbria_50_red.png");
                            break;
                        case WHITE:
                            dkNorthumbria[size][color.ordinal()][0] = new AngelCodeFont("fnt/DK_Northumbria_50_white.fnt", "fnt/DK_Northumbria_50_white.png");
                            break;
                        case BLACK:
                            dkNorthumbria[size][color.ordinal()][0] = new AngelCodeFont("fnt/DK_Northumbria_50_black.fnt", "fnt/DK_Northumbria_50_black.png");
                            break;
                        case NO_COLOR:
                            switch (type) {
                                case RENDERED:
                                    dkNorthumbria[size][color.ordinal()][type.ordinal()] = new AngelCodeFont("fnt/DK_Northumbria_50_rendered.fnt", "fnt/DK_Northumbria_50_rendered.png");
                                    break;
                            }
                    }
                    break;
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    private static void initializeTFGFont(int size, FontColor color, FontType type) {
        try {
            switch (size) {
                case 0:
                    switch (color) {
                        case RED:
                            triniganFg[size][color.ordinal()][0] = new AngelCodeFont("fnt/Trinigan_FG_10_red.fnt", "fnt/Trinigan_FG_10_red.png");
                            break;
                        case WHITE:
                            triniganFg[size][color.ordinal()][0] = new AngelCodeFont("fnt/Trinigan_FG_10_white.fnt", "fnt/Trinigan_FG_10_white.png");
                            break;
                        case BLACK:
                            triniganFg[size][color.ordinal()][0] = new AngelCodeFont("fnt/Trinigan_FG_10_black.fnt", "fnt/Trinigan_FG_10_black.png");
                    }
                    break;
                case 1:
                    switch (color) {
                        case RED:
                            triniganFg[size][color.ordinal()][0] = new AngelCodeFont("fnt/Trinigan_FG_12_red.fnt", "fnt/Trinigan_FG_12_red.png");
                            break;
                        case WHITE:
                            triniganFg[size][color.ordinal()][0] = new AngelCodeFont("fnt/Trinigan_FG_12_white.fnt", "fnt/Trinigan_FG_12_white.png");
                            break;
                        case BLACK:
                            triniganFg[size][color.ordinal()][0] = new AngelCodeFont("fnt/Trinigan_FG_12_black.fnt", "fnt/Trinigan_FG_12_black.png");
                            break;
                    }
                    break;
                case 2:
                    switch (color) {
                        case RED:
                            triniganFg[size][color.ordinal()][0] = new AngelCodeFont("fnt/Trinigan_FG_16_red.fnt", "fnt/Trinigan_FG_16_red.png");
                            break;
                        case WHITE:
                            triniganFg[size][color.ordinal()][0] = new AngelCodeFont("fnt/Trinigan_FG_16_white.fnt", "fnt/Trinigan_FG_16_white.png");
                            break;
                        case BLACK:
                            triniganFg[size][color.ordinal()][0] = new AngelCodeFont("fnt/Trinigan_FG_16_black.fnt", "fnt/Trinigan_FG_16_black.png");
                            break;
                    }
                    break;
                case 3:
                    switch (color) {
                        case RED:
                            triniganFg[size][color.ordinal()][0] = new AngelCodeFont("fnt/Trinigan_FG_20_red.fnt", "fnt/Trinigan_FG_20_red.png");
                            break;
                        case WHITE:
                            triniganFg[size][color.ordinal()][0] = new AngelCodeFont("fnt/Trinigan_FG_20_white.fnt", "fnt/Trinigan_FG_20_white.png");
                            break;
                        case BLACK:
                            triniganFg[size][color.ordinal()][0] = new AngelCodeFont("fnt/Trinigan_FG_20_black.fnt", "fnt/Trinigan_FG_20_black.png");
                            break;
                    }
                    break;
                case 4:
                    switch (color) {
                        case RED:
                            triniganFg[size][color.ordinal()][0] = new AngelCodeFont("fnt/Trinigan_FG_24_red.fnt", "fnt/Trinigan_FG_24_red.png");
                            break;
                        case WHITE:
                            triniganFg[size][color.ordinal()][0] = new AngelCodeFont("fnt/Trinigan_FG_24_white.fnt", "fnt/Trinigan_FG_24_white.png");
                            break;
                        case BLACK:
                            triniganFg[size][color.ordinal()][0] = new AngelCodeFont("fnt/Trinigan_FG_24_black.fnt", "fnt/Trinigan_FG_24_black.png");
                            break;
                        case NO_COLOR:
                            switch (type) {
                                case RENDERED:
                                    triniganFg[size][color.ordinal()][type.ordinal()] = new AngelCodeFont("fnt/Trinigan_FG_24_rendered.fnt", "fnt/Trinigan_FG_24_rendered.png");
                                    break;
                            }
                    }
                    break;
                case 5:
                    switch (color) {
                        case RED:
                            triniganFg[size][color.ordinal()][0] = new AngelCodeFont("fnt/Trinigan_FG_30_red.fnt", "fnt/Trinigan_FG_30_red.png");
                            break;
                        case WHITE:
                            triniganFg[size][color.ordinal()][0] = new AngelCodeFont("fnt/Trinigan_FG_30_white.fnt", "fnt/Trinigan_FG_30_white.png");
                            break;
                        case BLACK:
                            triniganFg[size][color.ordinal()][0] = new AngelCodeFont("fnt/Trinigan_FG_30_black.fnt", "fnt/Trinigan_FG_30_black.png");
                            break;
                        case NO_COLOR:
                            switch (type) {
                                case RENDERED:
                                    triniganFg[size][color.ordinal()][type.ordinal()] = new AngelCodeFont("fnt/Trinigan_FG_30_rendered.fnt", "fnt/Trinigan_FG_30_rendered.png");
                                    break;
                            }
                    }
                    break;
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
