package ntnu.idatt1002.dao;

import ntnu.idatt1002.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ImageDAOTest {
    public static File imageA;

    @BeforeAll
    public static void testData(){
        imageA = new File("src/main/resources/images/logo.PNG");
        UserDAO.serialize(new User("olanormann"));
        try {
            ImageDAO.add("olanormann", imageA);
        }catch(IOException ioe){ ioe.printStackTrace(); }
    }

    @Test
    public void filepathHasBeenSavedUnderItsUserTest(){
        File[] files = ImageDAO.list("olanormann");

        assertEquals(files.length, 1);
    }

    @Test
    public void getMethodeTest(){
        File imageB = ImageDAO.get("olanormann", "logo.png");
        assertTrue(imageB.exists());
    }

    @Nested
    public class wrong_arguments{
        @Test
        public void addingFilepathToNoneExistingUserTest(){
            assertDoesNotThrow(() -> ImageDAO.add("joseph", imageA));
            assertDoesNotThrow(() -> ImageDAO.add("joseph", new File("nonExistent.png")));
        }

        @Test
        public void getListOfFilePathOfNoneExistingUserTest(){
            assertNull(ImageDAO.list("joseph"));
        }

        @Test
        public void getFilepathFromNoneExistingUserTest(){
            assertNull(ImageDAO.get("joseph", "logo.png"));
            assertNull(ImageDAO.get("olanormann", "nonExistent.png"));
        }

        @Test
        public void deleteFileThatDoesNotExistTest(){
            assertFalse(ImageDAO.deleteByUser("joseph"));
        }

        @Test
        public void deleteNoneExistentFilesTest(){
            assertFalse(ImageDAO.delete(new File("nonExistent.png")));
            assertFalse(ImageDAO.delete("joseph", "logo.png"));
            assertFalse(ImageDAO.delete("olanormann", "nonExistent.png"));
        }
    }

    @AfterAll
    public static void deleteTestData(){
        UserDAO.delete("olanormann");
    }
}
