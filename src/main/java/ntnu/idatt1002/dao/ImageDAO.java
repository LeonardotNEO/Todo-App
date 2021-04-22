package ntnu.idatt1002.dao;

import ntnu.idatt1002.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The class {@code ImageDAO} provides static functions for handling image files for a {@link User}.
 * All images will be stored under the 'Images' folder, and gets converted to '.png'.
 * The methods gives and takes {@link File} objects referencing existing image files.
 */
public final class ImageDAO {
    private static final String SAVEPATH = "src/main/resources/saves";

    /**
     * Saves the given image file to the {@link User} and converts it to '.png'.
     * @param username the {@code username} variable in a {@link User}.
     * @param image a {@link File} referencing an existing image file.
     * @throws IOException if an error occurs. Either the user or the given file doesn't exist.
     */
    public static void add(String username, File image) throws IOException{
        if(Storage.userExists(username) && image.exists()) {
            BufferedImage bufferedImage = null;
            bufferedImage = ImageIO.read(image);
            String extension = getExtension(image.getName());
            ImageIO.write(bufferedImage, extension, new File(removeExtension(filepath(username, image)) + "." + extension));
        }
    }

    /**
     * Returns a {@link File}[] of all images stored in a {@link User}.
     * @param username the {@code username} variable in a {@link User}.
     * @return an array of files, or {@code null} if user doesn't exist.
     */
    public static File[] list(String username){
        File dir = new File(imageDir(username));
        return dir.listFiles();
    }

    /**
     * Returns a {@link File} of the given filename under a {@link User} folder.
     * @param username the {@code username} variable in a {@link User}.
     * @param filename the image files name. For example 'image0.png'.
     * @return a {@link File} referencing the found image file, or {@code null} if either the user
     * or the files doesn't exist.
     */
    public static File get(String username, String filename){
        File file = new File(filepath(username, filename));
        if(file.exists()){ return file; }
        else{ return null; }
    }

    /**
     * Delete all image files under a {@link User} folder.
     * @param username the {@code username} variable in a {@link User}.
     * @return {@code True} if succesfull. {@code False} if some or all elements failed to delete,
     * normally because the user doesn't exist.
     */
    public static boolean deleteByUser(String username){
        boolean result = true;
        File[] files = list(username);

        if(files == null){ return false; }
        for(File file : list(username)){
            if(!file.delete()){ result = false; }
        }
        return result;
    }

    /**
     * Delete an image file under the 'Images' folder for a {@link User}.
     * @param file a {@link File} object referencing the file to be deleted.
     * @return {@code True} if succesfull. {@code False} if the file couldn't be deleted,
     * normally because the file doesnt' exist.
     */
    public static boolean delete(File file){
        return file.delete();
    }

    /**
     * Delete an image file under the 'Images' folder for a {@link User}.
     * @param username the {@code username} variable in a {@link User}.
     * @param filename the image files name. For example 'image0.png'.
     * @return {@code True} if succesfull. {@code False} if the file couldn't be deleted,
     * either the user or the file doesn't exist.
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

    private static String getExtension(String filename){
        int pos = filename.lastIndexOf('.');
        return filename.substring(pos+1).toLowerCase();
    }
}
