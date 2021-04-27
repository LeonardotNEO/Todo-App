package ntnu.idatt1002.service;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ImageServiceTest {

    @Test
    public void addAndGetImageWithCurrentUserTest() {
        File file = new File("src/main/resources/images/logo.PNG");

        boolean ran = false;
        try {
            ImageService.addImageToCurrentUser(file);
            ran = true;
        } catch (IOException e) {}
        assertTrue(ran);

        assertEquals("src\\main\\resources\\saves\\Test\\Images\\logo.png", ImageService.getAllImagesCurrentUser().get(0).getPath());
    }
}
