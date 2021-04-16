package ntnu.idatt1002.dao;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class ImageDAO {
    private static final String SAVEPATH = "src/main/resources/saves";

    /**
     * Add an image to the users image folder
     */
    public static void add(String username, File image){
        BufferedImage bufferedImage;
        try{
            bufferedImage = ImageIO.read(image);
            ImageIO.write(bufferedImage, "png", new File(removeExtension(filepath(username, image)) + ".png"));
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     * Get all image files for a user
     * @return {@code null} if folder is empty or non-existant
     */
    public static File[] list(String username){
        File dir = new File(imageDir(username));
        return dir.listFiles();
    }

    /**
     * Get an image file given its owner and filename
     * @param filename filename with extension. "image.png"
     */
    public static File get(String username, String filename){
        return new File(filepath(username, filename));
    }

    /**
     * Delete all images for a user
     * @return {@code false} if some files could not be deleted
     */
    public static boolean deleteByUser(String username){
        boolean result = true;
        for(File file : list(username)){
            if(!file.delete()){ result = false; }
        }
        return result;
    }

    /**
     * Delete an image for a user
     * @return {@code false} if file could not be deleted
     */
    public static boolean delete(File file){
        return file.delete();
    }

    /**
     * Delete an image for a user
     * @param filename filename with extension. "image.png"
     * @return {@code false} if file could not be deleted
     */
    public static boolean delete(String username, String filename){
        return new File(filepath(username, filename)).delete();
    }

    //Get paths
    private static String imageDir(String username){
        return (SAVEPATH + "/" + username + "/Images/");
    }

    private static String filepath(String username, File file){
        return (imageDir(username) + file.getName());
    }

    private static String filepath(String username, String filename){
        return (imageDir(username) + filename);
    }

    private static String removeExtension(String filename){
        int pos = filename.lastIndexOf('.');
        if(pos == -1){ return filename; }
        return filename.substring(0, pos);
    }
}
