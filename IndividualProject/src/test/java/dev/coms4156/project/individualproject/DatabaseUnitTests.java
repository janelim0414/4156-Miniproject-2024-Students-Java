package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the MyFileDatabase class.
 */
@SpringBootTest
@ContextConfiguration
public class DatabaseUnitTests {

  @Test
  public void setMappingTest() {
    HashMap<String, Course> courses = new HashMap<>();
    Course testCourse1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    Course testCourse2 = new Course("Jane Lim", "451 CSB", "17:40-18:55", 40);
    courses.put("1234", testCourse1);
    courses.put("5678", testCourse2);
    Department department = new Department("COMS", courses, "Jae Lee", 3);
    HashMap<String, Department> mapping = new HashMap<>();
    mapping.put("COMS", department);
    testDatabase.setMapping(mapping);
    assertEquals(mapping, testDatabase.getDepartmentMapping());
  }

  @Test
  public void deSerializeObjectFromValidFileTest() {
    HashMap<String, Course> courses = new HashMap<>();
    Course testCourse1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    Course testCourse2 = new Course("Jane Lim", "451 CSB", "17:40-18:55", 40);
    courses.put("1234", testCourse1);
    courses.put("5678", testCourse2);
    Department department = new Department("COMS", courses, "Jae Lee", 3);
    HashMap<String, Department> mapping = new HashMap<>();
    mapping.put("COMS", department);
    String expected = "For the COMS department: \n"
            + "COMS 1234: \nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55\n"
            + "COMS 5678: \nInstructor: Jane Lim; Location: 451 CSB; Time: 17:40-18:55\n";
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./testdata.txt"))) {
      out.writeObject(mapping);
    } catch (IOException e) {
      e.printStackTrace();
    }
    testDatabase = new MyFileDatabase(0, "./testdata.txt");
    assertEquals(testDatabase.toString(), expected);
  }

  @Test
  public void deSerializeObjectFromInvalidFileTest() {
    Course testCourse1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./testdata.txt"))) {
      out.writeObject(testCourse1);
    } catch (IOException e) {
      e.printStackTrace();
    }
    testDatabase = new MyFileDatabase(1, "./testdata.txt");
    assertThrows(IllegalArgumentException.class, () -> testDatabase.deSerializeObjectFromFile());
  }

  @Test
  public void saveContentsToFileTest() {
    HashMap<String, Course> courses = new HashMap<>();
    Course testCourse1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    Course testCourse2 = new Course("Jane Lim", "451 CSB", "17:40-18:55", 40);
    courses.put("1234", testCourse1);
    courses.put("5678", testCourse2);
    Department department = new Department("COMS", courses, "Jae Lee", 3);
    HashMap<String, Department> mapping = new HashMap<>();
    mapping.put("COMS", department);
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./testdata.txt"))) {
      out.writeObject("");
    } catch (IOException e) {
      e.printStackTrace();
    }
    testDatabase = new MyFileDatabase(1, "./testdata.txt");
    testDatabase.setMapping(mapping);
    testDatabase.saveContentsToFile();
    HashMap<String, Department> expected = new HashMap<>();
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("./testdata.txt"))) {
      Object obj = in.readObject();
      if (obj instanceof HashMap) {
        expected = (HashMap<String, Department>) obj;
      }
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    assertEquals(testDatabase.getDepartmentMapping().toString(), expected.toString());
  }

  /** Database instance used for testing. */
  public static MyFileDatabase testDatabase;
}


