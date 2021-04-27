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
    public static void test_data(){
        imageA = new File("src/main/resources/images/logo.PNG");
        UserDAO.serialize(new User("olanormann"));
        try {
            ImageDAO.add("olanormann", imageA);
        }catch(IOException ioe){ ioe.printStackTrace(); }
    }

    @Test
    public void the_filepath_has_been_saved_under_its_user(){
        File[] files = ImageDAO.list("olanormann");

        assertEquals(files.length, 1);
    }

    @Test
    public void get_methode_test(){
        File imageB = ImageDAO.get("olanormann", "logo.png");
        assertTrue(imageB.exists());
    }

    @Nested
    public class wrong_arguments{
        @Test
        public void adding_filepath_to_none_existing_user(){
            assertDoesNotThrow(() -> ImageDAO.add("joseph", imageA));
            assertDoesNotThrow(() -> ImageDAO.add("joseph", new File("nonExistent.png")));
        }

        @Test
        public void get_list_of_file_path_of_none_existing_user(){
            assertNull(ImageDAO.list("joseph"));
        }

        @Test
        public void get_filepath_from_none_existing_user(){
            assertNull(ImageDAO.get("joseph", "logo.png"));
            assertNull(ImageDAO.get("olanormann", "nonExistent.png"));
        }

        @Test
        public void delete_file_that_does_not_exist(){
            assertFalse(ImageDAO.deleteByUser("joseph"));
        }

        @Test
        public void delete_none_existent_files(){
            assertFalse(ImageDAO.delete(new File("nonExistent.png")));
            assertFalse(ImageDAO.delete("joseph", "logo.png"));
            assertFalse(ImageDAO.delete("olanormann", "nonExistent.png"));
        }
    }

    @AfterAll
    public static void delete_test_data(){
        UserDAO.delete("olanormann");
    }
}
