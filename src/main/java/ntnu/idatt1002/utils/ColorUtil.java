package ntnu.idatt1002.utils;

import java.awt.Color;

public class ColorUtil {

    /**
     * Method for changing HEX format of: "0x00000ff" to "#00000"
     * @param hex
     * @return
     */
    public static String getCorrectColorFormat(String hex){
        String hexCorrectFormat = "#";
        for(int i = 0; i < hex.length(); i++){
            if(i > 1 && i < 8){
                hexCorrectFormat += hex.charAt(i);
            }
        }
        return hexCorrectFormat;
    }

    /**
     * Transform HEX-color to RGB-color format
     * @param hex
     * @return
     */
    public static Color hexToRgb(String hex){
        return Color.decode(hex);
    }

    /**
     * Transform RGB-color format to HSB/HSV-color format
     * @param rgb
     * @return
     */
    public static float[] RgbToHsb(Color rgb){
        int r = rgb.getRed();
        int g = rgb.getGreen();
        int b = rgb.getBlue();
        float[] hsb = new float[3];
        Color.RGBtoHSB(r, g, b, hsb);
        return hsb;
    }

    /**
     * Get Hex brightness in value from 0 to 100
     * @param hex
     * @return
     */
    public static int getHexBrightness(String hex){
        return (int) (RgbToHsb(hexToRgb(hex))[2] * 100);
    }

    /**
     * Get Hex saturation in value from 0 to 100
     * @param hex
     * @return
     */
    public static int getHexSaturation(String hex){
        return (int) (RgbToHsb(hexToRgb(hex))[1] * 100);
    }

    /**
     * Get the inverted Hex saturation from 0 to 100
     * @param hex
     * @return
     */
    public static int getHexSaturationInverted(String hex){
        return (int) (100 - (RgbToHsb(hexToRgb(hex))[1] * 100));
    }

    /**
     * Method for generating a Visibility rating with the product of the HEXBrightness and HEXSaturation.
     * This can later be used to choose a treshold for generating text-color based on background-color (to achieve correct contrast).
     * @param hex
     * @return
     */
    public static int getVisiblityRating(String hex){
        return (int) 10000 - (getHexBrightness(hex) * getHexSaturationInverted(hex));
    }

    /**
     * Method for knowing if a HEX-color is over threshold or under. Based on this we can later set text color to UI elements.
     * @param hex
     * @return
     */
    public static boolean isVisibilityRatingOverThreshold(String hex){
        if(getVisiblityRating(hex) > 6000){
            return true;
        } else {
            return false;
        }
    }

}
