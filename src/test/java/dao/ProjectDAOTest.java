package dao;

import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.ProjectDAO;
import ntnu.idatt1002.dao.UserDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectDAOTest {
    @BeforeAll
    public static void setup(){
        UserDAO.serialize(new User("olanormann"));
        ProjectDAO.add("olanormann", "Build house");
        ProjectDAO.add("olanormann", "Start business");
    }

    @Test
    public void _list(){
        String[] projects = ProjectDAO.list("olanormann");

        assertTrue(projects.length == 2 && Arrays.asList(projects).contains("Build house"));
    }

    @Nested
    public class wrong_arguments{
        @Test
        public void _list(){
            assertNull(ProjectDAO.list("joseph"));
        }

        @Test
        public void _add(){
            assertFalse(ProjectDAO.add("joseph", "Renovate kitchen"));
        }

        @Test
        public void _deleteByUser(){
            assertFalse(ProjectDAO.deleteByUser("joseph"));
        }

        @Test
        public void _delete(){
            assertFalse(ProjectDAO.delete("joseph", "Renovate kitchen"));

            assertFalse(ProjectDAO.delete("olanormann", "Renovate kitchen"));
        }
    }

    @AfterAll
    public static void cleanup(){
        UserDAO.delete("olanormann");
    }
}
