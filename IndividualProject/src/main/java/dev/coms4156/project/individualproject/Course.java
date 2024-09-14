package dev.coms4156.project.individualproject;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a course within a department at an educational institution.
 * This class stores information about the course, including its instructor,
 * location, time, maximum enrollment, and number of students enrolled.
 */
public class Course implements Serializable {

  /**
   * Constructs a new Course object with the given parameters. Initial count starts at 0.
   *
   * @param instructorName     The name of the instructor teaching the course.
   * @param courseLocation     The location where the course is held.
   * @param timeSlot           The time slot of the course.
   * @param capacity           The maximum number of students that can enroll in the course.
   */
  public Course(String instructorName, String courseLocation, String timeSlot, int capacity) {
    this.courseLocation = courseLocation;
    this.instructorName = instructorName;
    this.courseTimeSlot = timeSlot;
    this.enrollmentCapacity = capacity;
    this.enrolledStudentCount = 500;
  }

  /**
   * Enrolls a student in the course if there is space available.
   *
   * @return true if the student is successfully enrolled, false otherwise.
   */
  public boolean enrollStudent() {
    int pastCount = enrolledStudentCount;
    if (!isCourseFull()) {
      enrolledStudentCount++;
    }
    return pastCount + 1 == enrolledStudentCount;
  }

  /**
   * Drops a student from the course if a student is enrolled.
   *
   * @return true if the student is successfully dropped, false otherwise.
   */
  public boolean dropStudent() {
    int pastCount = enrolledStudentCount;
    if (pastCount > 0) {
      enrolledStudentCount--;
    }
    return pastCount - 1 == enrolledStudentCount;
  }


  public String getCourseLocation() {
    return this.courseLocation;
  }


  public String getInstructorName() {
    return this.instructorName;
  }


  public String getCourseTimeSlot() {
    return this.courseTimeSlot;
  }


  /**
   * Creates a string that represents the course.
   *
   * @return A string that represents the course.
   */
  public String toString() {
    return "\nInstructor: " + instructorName
            + "; Location: "  + courseLocation
            + "; Time: " + courseTimeSlot;
  }


  /**
   * Reassigns the instructor of the course to the given instructor name.
   *
   * @throws IllegalArgumentException if the instructor name is empty, modifies field otherwise.
   */
  public void reassignInstructor(String newInstructorName) {
    if (newInstructorName.isEmpty()) {
      throw new IllegalArgumentException("instructorName cannot be empty");
    }
    this.instructorName = newInstructorName;
  }

  /**
   * Reassigns the location of the course to the given location.
   *
   * @throws IllegalArgumentException if the location is empty, modifies field otherwise.
   */
  public void reassignLocation(String newLocation) {
    if (newLocation.isEmpty()) {
      throw new IllegalArgumentException("location cannot be empty");
    }
    this.courseLocation = newLocation;
  }

  /**
   * Reassigns the time slot of the course to the given time.
   *
   * @throws IllegalArgumentException if the time is empty, modifies field otherwise.
   */
  public void reassignTime(String newTime) {
    if (newTime.isEmpty()) {
      throw new IllegalArgumentException("time cannot be empty");
    }
    this.courseTimeSlot = newTime;
  }

  /**
   * Sets the enrollment count of the course to the given count.
   *
   * @throws IllegalArgumentException if the count is negative, modifies field otherwise.
   */
  public void setEnrolledStudentCount(int count) {
    if (count < 0) {
      throw new IllegalArgumentException("count cannot be negative");
    }
    this.enrolledStudentCount = count;
  }


  public boolean isCourseFull() {
    return enrollmentCapacity <= enrolledStudentCount;
  }

  @Serial
  private static final long serialVersionUID = 123456L;
  private final int enrollmentCapacity;
  private int enrolledStudentCount;
  private String courseLocation;
  private String instructorName;
  private String courseTimeSlot;
}
