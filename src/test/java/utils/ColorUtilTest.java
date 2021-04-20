package utils;

import ntnu.idatt1002.utils.ColorUtil;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

public class ColorUtilTest {

    @Test
    public void hex_to_rgb(){
        Color color = ColorUtil.hexToRgb("#001021");
        int[] expectedColors = {0, 16, 33};
        int[] actualColors = {color.getRed(), color.getGreen(), color.getBlue()};
        assertArrayEquals(expectedColors,actualColors);
    }

    @Test
    public void get_hsb_from_hex(){
        Color rgb = ColorUtil.hexToRgb("#000000");
        float[] hsbExpected = {0, 0, 0};
        float[] hsbActual = ColorUtil.RgbToHsb(rgb);
        assertArrayEquals(hsbExpected, hsbActual);
    }

    @Test
    public void get_brightness_from_hex(){
        int brightnessWhite = ColorUtil.getHexBrightness("#FFFFFF");
        int brightnessBlack = ColorUtil.getHexBrightness("#000000");
        assertEquals(0, brightnessBlack);
        assertEquals(100, brightnessWhite);
    }

    @Test
    public void get_saturation_from_hex(){
        int saturationBlack = ColorUtil.getHexSaturation("#000000");
        int saturationBlackInverted = ColorUtil.getHexSaturationInverted("#000000");
        assertEquals(0, saturationBlack);
        assertEquals(100, saturationBlackInverted);
    }

    @Test
    public void check_text_color_withHex_background(){
        String hexcolor = "#FFFFFF";
        // Max brightness
        assertEquals(100, ColorUtil.getHexBrightness(hexcolor));
        // No saturation
        assertEquals(0, ColorUtil.getHexSaturation(hexcolor));
        // Highest contrast -> black
        assertEquals(0, ColorUtil.getVisiblityRating(hexcolor));
    }
}
