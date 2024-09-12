package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the Department class.
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

  @BeforeAll
  public static void setupDepartmentForTesting() {
    testCourse1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    testCourse2 = new Course("Jane Lim", "451 CSB", "17:40-18:55", 40);
  }

  @Test
  public void getNumberOfMajorsTest() {
    testCourses = new HashMap<>();
    testCourses.put("1234", testCourse1);
    testCourses.put("5678", testCourse2);
    testDepartment = new Department("COMS", testCourses, "Jae Lee", 3);
    int expectedResult = 3;
    assertEquals(expectedResult, testDepartment.getNumberOfMajors());
  }

  @Test
  public void getDepartmentChairTest() {
    testCourses = new HashMap<>();
    testCourses.put("1234", testCourse1);
    testCourses.put("5678", testCourse2);
    testDepartment = new Department("COMS", testCourses, "Jae Lee", 3);
    String expectedResult = "Jae Lee";
    assertEquals(expectedResult, testDepartment.getDepartmentChair());
  }

  @Test
  public void getCourseSelectionTest() {
    testCourses = new HashMap<>();
    testCourses.put("1234", testCourse1);
    testCourses.put("5678", testCourse2);
    testDepartment = new Department("COMS", testCourses, "Jae Lee", 3);
    HashMap<String, Course> expectedResult = testCourses;
    assertEquals(expectedResult, testDepartment.getCourseSelection());
  }

  @Test
  public void addPersonToMajorTest() {
    testCourses = new HashMap<>();
    testCourses.put("1234", testCourse1);
    testCourses.put("5678", testCourse2);
    testDepartment = new Department("COMS", testCourses, "Jae Lee", 3);
    testDepartment.addPersonToMajor();
    assertEquals(4, testDepartment.getNumberOfMajors());
  }

  @Test
  public void dropPersonFromMajorTest() {
    testCourses = new HashMap<>();
    testCourses.put("1234", testCourse1);
    testCourses.put("5678", testCourse2);
    testDepartment = new Department("COMS", testCourses, "Jae Lee", 3);
    testDepartment.dropPersonFromMajor();
    assertEquals(2, testDepartment.getNumberOfMajors());
  }

  @Test
  public void addCourseTest() {
    testCourses = new HashMap<>();
    testCourses.put("1234", testCourse1);
    testCourses.put("5678", testCourse2);
    testDepartment = new Department("COMS", testCourses, "Jae Lee", 3);
    Course newCourse = new Course("Gail Kaiser", "417 IAB", "10:10-11:25", 120);
    HashMap<String, Course> expectedResult = testCourses;
    expectedResult.put("2345", newCourse);
    testDepartment.addCourse("2345", newCourse);
    assertEquals(expectedResult, testDepartment.getCourseSelection());
  }

  @Test
  public void createCourseTest() {
    testCourses = new HashMap<>();
    testCourses.put("1234", testCourse1);
    testCourses.put("5678", testCourse2);
    testDepartment = new Department("COMS", testCourses, "Jae Lee", 3);
    String expectedResult =
            "COMS 1234: \nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55\n"
                    + "COMS 2345: \nInstructor: Gail Kaiser; Location: 417 IAB; Time: 10:10-11:25\n"
                    + "COMS 5678: \nInstructor: Jane Lim; Location: 451 CSB; Time: 17:40-18:55\n";
    testDepartment.createCourse("2345", "Gail Kaiser", "417 IAB", "10:10-11:25", 120);
    assertEquals(expectedResult, testDepartment.toString());
  }

  @Test
  public void createCourseEmptyParameterTest() {
    testCourses = new HashMap<>();
    testCourses.put("1234", testCourse1);
    testCourses.put("5678", testCourse2);
    testDepartment = new Department("COMS", testCourses, "Jae Lee", 3);
    assertThrows(IllegalArgumentException.class,
            () -> testDepartment.createCourse("", "Gail Kaiser", "417 IAB", "10:10-11:25", 120));
  }

  @Test
  public void toStringTest() {
    testCourses = new HashMap<>();
    testCourses.put("1234", testCourse1);
    testCourses.put("5678", testCourse2);
    testDepartment = new Department("COMS", testCourses, "Jae Lee", 3);
    String expectedResult =
            "COMS 1234: \nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55\n"
            + "COMS 5678: \nInstructor: Jane Lim; Location: 451 CSB; Time: 17:40-18:55\n";
    assertEquals(expectedResult, testDepartment.toString());
  }


  /** Department and Course instances used for testing. */
  public static Department testDepartment;
  public static HashMap<String, Course> testCourses;
  public static Course testCourse1;
  public static Course testCourse2;
}
