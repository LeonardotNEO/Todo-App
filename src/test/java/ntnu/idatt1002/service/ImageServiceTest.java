package ntnu.idatt1002.service;

import ntnu.idatt1002.dao.UserDAO;
import ntnu.idatt1002.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ImageServiceTest {

    @BeforeAll
    public static void setup() {
        User user = new User("Test");
        UserDAO.serialize(user);
        UserStateService.setCurrentUserUsername("Test");
    }

    @AfterAll
    public static void clean() {
        UserService.deleteUser();
    }

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
