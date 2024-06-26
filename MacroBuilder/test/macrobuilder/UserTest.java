/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package macrobuilder;

import java.sql.SQLException;
import objects.User;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author KingJ
 */
public class UserTest {

    private User user;

    public UserTest() {
    }

    @Before
    public void setUp() throws SQLException {
        user = new User("Melvin", User.Gender.FEMALE, 73, (float) 64, (float) 400, User.ActivityLevel.ACTIVE, User.CurrentMode.BULK);
         user.initializeCalendar();
    }

    @Test
    public void testConstructor() {
        assertEquals("Melvin", user.getUsername());
        assertEquals(User.Gender.FEMALE, user.getGender());
        assertEquals(73, user.getAge());
        assertEquals(64.0f, user.getHeight(), 0.001f);
        assertEquals(400.0f, user.getWeight(), 0.001f);
        assertEquals(User.ActivityLevel.ACTIVE, user.getActivityLevel());
        assertEquals(User.CurrentMode.BULK, user.getCurrentMode());
    }

    @Test
    public void testGettersAndSetters() {
        //Test setters
        user.setGender(User.Gender.FEMALE);
        user.setAge(90);
        user.setHeight(40);
        user.setWeight(200);
        user.setActivityLevel(User.ActivityLevel.NOT_ACTIVE);
        user.setCurrentMode(User.CurrentMode.CUT);

        //Test getters
        assertEquals(User.Gender.FEMALE, user.getGender());
        assertEquals(90, user.getAge());
        assertEquals(40.0f, user.getHeight(), 0.001f);
        assertEquals(200.0f, user.getWeight(), 0.001f);
        assertEquals(User.ActivityLevel.NOT_ACTIVE, user.getActivityLevel());
        assertEquals(User.CurrentMode.CUT, user.getCurrentMode());
    }

    @Test
    public void testDayCreation() {
        assertNotNull(user.getDay());
    }
}
