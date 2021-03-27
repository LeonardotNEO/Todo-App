package utils;

import ntnu.idatt1002.utils.ColorUtil;
import org.junit.jupiter.api.Test;

import java.awt.Color;

public class ColorUtilTest {

    @Test
    public void testHexToRgb(){
        System.out.println(ColorUtil.hexToRgb("#001021"));
    }

    @Test
    public void getHSBfromHEX(){
        Color rgb = ColorUtil.hexToRgb("#001021");
        System.out.println(rgb);

        float[] hsb = ColorUtil.RgbToHsb(rgb);
        System.out.println(hsb[0]);
        System.out.println(hsb[1]);
        System.out.println(hsb[2]);
    }

    @Test
    public void getBrightnessFromHex(){
        System.out.println(ColorUtil.getHexBrightness("#001021"));
    }

    @Test
    public void getSaturationFromHex(){
        System.out.println(ColorUtil.getHexSaturation("#2b5b8f"));
        System.out.println(ColorUtil.getHexSaturationInverted("#2b5b8f"));
    }

    @Test
    public void checkTextColorWithHexBackground(){
        String hexcolor = "#f5a9a9";
        System.out.println(ColorUtil.getHexBrightness(hexcolor));
        System.out.println(ColorUtil.getHexSaturation(hexcolor));
        System.out.println("value: " + ColorUtil.getVisiblityRating(hexcolor));
    }
}
