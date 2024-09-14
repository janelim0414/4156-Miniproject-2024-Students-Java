package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the Course class.
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  @BeforeAll
  public static void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }

  @Test
  public void getCourseLocationTest() {
    String expectedResult = "417 IAB";
    assertEquals(expectedResult, testCourse.getCourseLocation());
  }

  @Test
  public void getInstructorNameTest() {
    String expectedResult = "Griffin Newbold";
    assertEquals(expectedResult, testCourse.getInstructorName());
  }

  @Test
  public void getCourseTimeSlotTest() {
    String expectedResult = "11:40-12:55";
    assertEquals(expectedResult, testCourse.getCourseTimeSlot());
  }

  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  @Test
  public void reassignInstructorRegularTest() {
    String expectedResult = "\nInstructor: Jane Lim; Location: 417 IAB; Time: 11:40-12:55";
    testCourse.reassignInstructor("Jane Lim");
    assertEquals(expectedResult, testCourse.toString());
    // revert to original value for future testing
    testCourse.reassignInstructor("Griffin Newbold");
  }

  @Test
  public void reassignInstructorEmptyStringTest() {
    assertThrows(IllegalArgumentException.class, () -> testCourse.reassignInstructor(""));
  }

  @Test
  public void reassignLocationRegularTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: CSC 451; Time: 11:40-12:55";
    testCourse.reassignLocation("CSC 451");
    assertEquals(expectedResult, testCourse.toString());
    testCourse.reassignLocation("417 IAB");
  }

  @Test
  public void reassignLocationEmptyStringTest() {
    assertThrows(IllegalArgumentException.class, () -> testCourse.reassignLocation(""));
  }

  @Test
  public void reassignTimeRegularTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 17:40-18:55";
    testCourse.reassignTime("17:40-18:55");
    assertEquals(expectedResult, testCourse.toString());
    testCourse.reassignTime("11:40-12:55");
  }

  @Test
  public void reassignTimeEmptyStringTest() {
    assertThrows(IllegalArgumentException.class, () -> testCourse.reassignTime(""));
  }

  @Test
  public void setEnrolledStudentCountRegularTest() {
    assertTrue(testCourse.isCourseFull());
    boolean expectedResult = false;
    testCourse.setEnrolledStudentCount(200);
    assertEquals(expectedResult, testCourse.isCourseFull());
    testCourse.setEnrolledStudentCount(500);
  }

  @Test
  public void setEnrolledStudentCountInvalidTest() {
    assertThrows(IllegalArgumentException.class, () -> testCourse.setEnrolledStudentCount(-1));
  }

  @Test
  public void enrollStudentSuccessTest() {
    testCourse.setEnrolledStudentCount(200);
    assertTrue(testCourse.enrollStudent());
    testCourse.setEnrolledStudentCount(500);
  }

  @Test
  public void enrollStudentFailTest() {
    assertFalse(testCourse.enrollStudent());
  }

  @Test
  public void dropStudentSuccessTest() {
    assertTrue(testCourse.dropStudent());
    testCourse.setEnrolledStudentCount(500);
  }

  @Test
  public void dropStudentFailTest() {
    testCourse.setEnrolledStudentCount(0);
    assertFalse(testCourse.dropStudent());
    testCourse.setEnrolledStudentCount(500);
  }

  /** The test course instance used for testing. */
  public static Course testCourse;
}