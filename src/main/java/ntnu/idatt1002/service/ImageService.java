package ntnu.idatt1002.service;

import ntnu.idatt1002.dao.ImageDAO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class used for handling getting, adding and deleting images
 */
public class ImageService {

    /**
     * Method communicating with ImageDAO to fetch the current users images
     * @return
     */
    public static ArrayList<File> getAllImagesCurrentUser(){
        File[] files = ImageDAO.list(UserStateService.getCurrentUser().getUsername());
        ArrayList<File> images = new ArrayList<>();

        Collections.addAll(images, files);

        return images;
    }

    /**
     * Method communicating with ImageDAO to add an image to the current user
     * @param file
     * @throws IOException
     */
    public static void addImageToCurrentUser(File file) throws IOException {
        ImageDAO.add(UserStateService.getCurrentUser().getUsername(), file);
    }

    /**
     * Method communicating with ImageDAO to delete an image from current user
     * @param file
     */
    public static void deleteImageFromCurrentUser(File file){
        ImageDAO.delete(UserStateService.getCurrentUser().getUsername(), file.toURI().toString());
    }
}
