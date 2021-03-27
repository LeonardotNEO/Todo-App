package ntnu.idatt1002.utils;

import java.awt.Color;

public class ColorUtil {

    public static String getCorrectColorFormat(String hex){
        String hexCorrectFormat = "#";
        for(int i = 0; i < hex.length(); i++){
            if(i > 1 && i < 8){
                hexCorrectFormat += hex.charAt(i);
            }
        }
        return hexCorrectFormat;
    }

    public static Color hexToRgb(String hex){
        return Color.decode(hex);
    }

    public static float[] RgbToHsb(Color rgb){
        int r = rgb.getRed();
        int g = rgb.getGreen();
        int b = rgb.getBlue();
        float[] hsb = new float[3];
        Color.RGBtoHSB(r, g, b, hsb);
        return hsb;
    }

    public static int getHexBrightness(String hex){
        return (int) (RgbToHsb(hexToRgb(hex))[2] * 100);
    }

    public static int getHexSaturation(String hex){
        return (int) (RgbToHsb(hexToRgb(hex))[1] * 100);
    }

    public static int getHexSaturationInverted(String hex){
        return (int) (100 - (RgbToHsb(hexToRgb(hex))[1] * 100));
    }

    public static int getVisiblityRating(String hex){
        return (int) 10000 - (getHexBrightness(hex) * getHexSaturationInverted(hex));
    }

    public static boolean isVisibilityRatingOverThreshold(String hex){
        if(getVisiblityRating(hex) > 6000){
            return true;
        } else {
            return false;
        }
    }

}
