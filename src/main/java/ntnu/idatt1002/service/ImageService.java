package ntnu.idatt1002.service;

import ntnu.idatt1002.dao.ImageDAO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class ImageService {

    public static ArrayList<File> getAllImagesCurrentUser(){
        File[] files = ImageDAO.list(UserStateService.getCurrentUser().getUsername());
        ArrayList<File> images = new ArrayList<>();

        Collections.addAll(images, files);

        return images;
    }

    public static void addImageToCurrentUser(File file) throws IOException {
        ImageDAO.add(UserStateService.getCurrentUser().getUsername(), file);
    }

    public static void deleteImageFromCurrentUser(File file){
        ImageDAO.delete(UserStateService.getCurrentUser().getUsername(), file.toURI().toString());
    }
}
