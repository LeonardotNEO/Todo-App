package dao;

import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.ImageDAO;
import ntnu.idatt1002.dao.UserDAO;
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
    public static void setup(){
        imageA = new File("src/main/resources/images/logo.PNG");
        UserDAO.serialize(new User("olanormann"));
        try {
            ImageDAO.add("olanormann", imageA);
        }catch(IOException ioe){ ioe.printStackTrace(); }
    }

    @Test
    public void _list(){
        File[] files = ImageDAO.list("olanormann");

        assertEquals(files.length, 1);
    }

    @Test
    public void _get(){
        File imageB = ImageDAO.get("olanormann", "logo.png");
        assertTrue(imageB.exists());
    }

    @Nested
    public class wrong_arguments{
        @Test
        public void _add(){
            assertDoesNotThrow(() -> ImageDAO.add("joseph", imageA));
            assertDoesNotThrow(() -> ImageDAO.add("joseph", new File("nonExistent.png")));
        }

        @Test
        public void _list(){
            assertNull(ImageDAO.list("joseph"));
        }

        @Test
        public void _get(){
            assertNull(ImageDAO.get("joseph", "logo.png"));
            assertNull(ImageDAO.get("olanormann", "nonExistent.png"));
        }

        @Test
        public void _deleteByUser(){
            assertFalse(ImageDAO.deleteByUser("joseph"));
        }

        @Test
        public void _delete(){
            assertFalse(ImageDAO.delete(new File("nonExistent.png")));
            assertFalse(ImageDAO.delete("joseph", "logo.png"));
            assertFalse(ImageDAO.delete("olanormann", "nonExistent.png"));
        }
    }

    @AfterAll
    public static void cleanup(){
        UserDAO.delete("olanormann");
    }
}
