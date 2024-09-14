package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.HashMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Unit tests for the IndividualProjectApplication class.
 */
@WebMvcTest(controllers = RouteController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class RouteControllerUnitTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private MyFileDatabase myFileDatabase;

  @Test
  public void indexTest() throws Exception {
    ResultActions response = mockMvc.perform(get("/"));
    String expected = "Welcome, in order to make an API call direct your browser or "
            + "Postman to an endpoint "
            + "\n\n This can be done using the following format: \n\n http:127.0.0"
            + ".1:8080/endpoint?arg=value";
    assertEquals(expected, response.andReturn().getResponse().getContentAsString());
  }

  @Test
  public void retrieveDepartmentTest() throws Exception {
    HashMap<String, Course> courses = new HashMap<>();
    courses.put("1234", new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250));
    courses.put("5678", new Course("Jane Lim", "451 CSB", "17:40-18:55", 40));
    Department department = new Department("COMS", courses, "Jae Lee", 3);
    HashMap<String, Department> mapping = new HashMap<>();
    mapping.put("COMS", department);
    Mockito.when(myFileDatabase.getDepartmentMapping()).thenReturn(mapping);
    IndividualProjectApplication.overrideDatabase(myFileDatabase);
    ResultActions response = mockMvc.perform(get("/retrieveDept")
            .param("deptCode", "COMS")
            .accept(MediaType.APPLICATION_JSON));
    response.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(department.toString()));
  }

  @Test
  public void retrieveInvalidDepartmentTest() throws Exception {
    HashMap<String, Course> courses = new HashMap<>();
    courses.put("1234", new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250));
    courses.put("5678", new Course("Jane Lim", "451 CSB", "17:40-18:55", 40));
    Department department = new Department("COMS", courses, "Jae Lee", 3);
    HashMap<String, Department> mapping = new HashMap<>();
    mapping.put("COMS", department);
    Mockito.when(myFileDatabase.getDepartmentMapping()).thenReturn(mapping);
    IndividualProjectApplication.overrideDatabase(myFileDatabase);
    ResultActions response = mockMvc.perform(get("/retrieveDept")
            .param("deptCode", "ECON")
            .accept(MediaType.APPLICATION_JSON));
    response.andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string("Department Not Found"));
  }

  @Test
  public void retrieveDepartmentExceptionTest() throws Exception {
    Mockito.when(myFileDatabase.getDepartmentMapping()).thenThrow(new RuntimeException());
    IndividualProjectApplication.overrideDatabase(myFileDatabase);
    ResultActions response = mockMvc.perform(get("/retrieveDept")
            .param("deptCode", "ECON")
            .accept(MediaType.APPLICATION_JSON));
    response.andExpect(MockMvcResultMatchers.status().isInternalServerError())
            .andExpect(MockMvcResultMatchers.content().string("An Error has occurred"));
  }

  @Test
  public void retrieveCourseTest() throws Exception {
    HashMap<String, Course> courses = new HashMap<>();
    Course course1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    Course course2 = new Course("Jane Lim", "451 CSB", "17:40-18:55", 40);
    courses.put("1234", course1);
    courses.put("5678", course2);
    Department department = new Department("COMS", courses, "Jae Lee", 3);
    HashMap<String, Department> mapping = new HashMap<>();
    mapping.put("COMS", department);
    Mockito.when(myFileDatabase.getDepartmentMapping()).thenReturn(mapping);
    IndividualProjectApplication.overrideDatabase(myFileDatabase);
    ResultActions response = mockMvc.perform(get("/retrieveCourse")
            .param("deptCode", "COMS")
            .param("courseCode", "1234")
            .accept(MediaType.APPLICATION_JSON));
    response.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(course1.toString()));
  }

  @Test
  public void retrieveInvalidCourseTest() throws Exception {
    HashMap<String, Course> courses = new HashMap<>();
    Course course1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    Course course2 = new Course("Jane Lim", "451 CSB", "17:40-18:55", 40);
    courses.put("1234", course1);
    courses.put("5678", course2);
    Department department = new Department("COMS", courses, "Jae Lee", 3);
    HashMap<String, Department> mapping = new HashMap<>();
    mapping.put("COMS", department);
    Mockito.when(myFileDatabase.getDepartmentMapping()).thenReturn(mapping);
    IndividualProjectApplication.overrideDatabase(myFileDatabase);
    ResultActions response = mockMvc.perform(get("/retrieveCourse")
            .param("deptCode", "COMS")
            .param("courseCode", "3157")
            .accept(MediaType.APPLICATION_JSON));
    response.andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string("Course Not Found"));
  }

  @Test
  public void retrieveCourseInvalidDepartmentTest() throws Exception {
    HashMap<String, Course> courses = new HashMap<>();
    Course course1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    Course course2 = new Course("Jane Lim", "451 CSB", "17:40-18:55", 40);
    courses.put("1234", course1);
    courses.put("5678", course2);
    Department department = new Department("COMS", courses, "Jae Lee", 3);
    HashMap<String, Department> mapping = new HashMap<>();
    mapping.put("COMS", department);
    Mockito.when(myFileDatabase.getDepartmentMapping()).thenReturn(mapping);
    IndividualProjectApplication.overrideDatabase(myFileDatabase);
    ResultActions response = mockMvc.perform(get("/retrieveCourse")
            .param("deptCode", "ECON")
            .param("courseCode", "1234")
            .accept(MediaType.APPLICATION_JSON));
    response.andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string("Department Not Found"));
  }

  @Test
  public void isCourseFullTrueTest() throws Exception {
    HashMap<String, Course> courses = new HashMap<>();
    Course course1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 500);
    Course course2 = new Course("Jane Lim", "451 CSB", "17:40-18:55", 40);
    courses.put("1234", course1);
    courses.put("5678", course2);
    Department department = new Department("COMS", courses, "Jae Lee", 3);
    HashMap<String, Department> mapping = new HashMap<>();
    mapping.put("COMS", department);
    Mockito.when(myFileDatabase.getDepartmentMapping()).thenReturn(mapping);
    IndividualProjectApplication.overrideDatabase(myFileDatabase);
    ResultActions response = mockMvc.perform(get("/isCourseFull")
            .param("deptCode", "COMS")
            .param("courseCode", "1234")
            .accept(MediaType.APPLICATION_JSON));
    response.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("true"));
  }

  @Test
  public void isCourseFullFalseTest() throws Exception {
    HashMap<String, Course> courses = new HashMap<>();
    Course course1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 500);
    Course course2 = new Course("Jane Lim", "451 CSB", "17:40-18:55", 600);
    courses.put("1234", course1);
    courses.put("5678", course2);
    Department department = new Department("COMS", courses, "Jae Lee", 3);
    HashMap<String, Department> mapping = new HashMap<>();
    mapping.put("COMS", department);
    Mockito.when(myFileDatabase.getDepartmentMapping()).thenReturn(mapping);
    IndividualProjectApplication.overrideDatabase(myFileDatabase);
    ResultActions response = mockMvc.perform(get("/isCourseFull")
            .param("deptCode", "COMS")
            .param("courseCode", "5678")
            .accept(MediaType.APPLICATION_JSON));
    response.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("false"));
  }

  @Test
  public void isCourseFullInvalidCourseTest() throws Exception {
    HashMap<String, Course> courses = new HashMap<>();
    Course course1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 500);
    Course course2 = new Course("Jane Lim", "451 CSB", "17:40-18:55", 600);
    courses.put("1234", course1);
    courses.put("5678", course2);
    Department department = new Department("COMS", courses, "Jae Lee", 3);
    HashMap<String, Department> mapping = new HashMap<>();
    mapping.put("COMS", department);
    Mockito.when(myFileDatabase.getDepartmentMapping()).thenReturn(mapping);
    IndividualProjectApplication.overrideDatabase(myFileDatabase);
    ResultActions response = mockMvc.perform(get("/isCourseFull")
            .param("deptCode", "COMS")
            .param("courseCode", "1215")
            .accept(MediaType.APPLICATION_JSON));
    response.andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string("Course Not Found"));
  }

  @Test
  public void isCourseFullInvalidDepartmentTest() throws Exception {
    HashMap<String, Course> courses = new HashMap<>();
    Course course1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 500);
    Course course2 = new Course("Jane Lim", "451 CSB", "17:40-18:55", 600);
    courses.put("1234", course1);
    courses.put("5678", course2);
    Department department = new Department("COMS", courses, "Jae Lee", 3);
    HashMap<String, Department> mapping = new HashMap<>();
    mapping.put("COMS", department);
    Mockito.when(myFileDatabase.getDepartmentMapping()).thenReturn(mapping);
    IndividualProjectApplication.overrideDatabase(myFileDatabase);
    ResultActions response = mockMvc.perform(get("/isCourseFull")
            .param("deptCode", "ECON")
            .param("courseCode", "1234")
            .accept(MediaType.APPLICATION_JSON));
    response.andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string("Department Not Found"));
  }

  @Test
  public void getMajorCtFromDeptTest() throws Exception {
    HashMap<String, Course> courses = new HashMap<>();
    Course course1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 500);
    Course course2 = new Course("Jane Lim", "451 CSB", "17:40-18:55", 600);
    courses.put("1234", course1);
    courses.put("5678", course2);
    Department department = new Department("COMS", courses, "Jae Lee", 3);
    HashMap<String, Department> mapping = new HashMap<>();
    mapping.put("COMS", department);
    Mockito.when(myFileDatabase.getDepartmentMapping()).thenReturn(mapping);
    IndividualProjectApplication.overrideDatabase(myFileDatabase);
    ResultActions response = mockMvc.perform(get("/getMajorCountFromDept")
            .param("deptCode", "COMS")
            .accept(MediaType.APPLICATION_JSON));
    response.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(
                    "There are: 3 majors in the department"));
  }

  @Test
  public void getMajorCtFromInvalidDeptTest() throws Exception {
    HashMap<String, Course> courses = new HashMap<>();
    Course course1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 500);
    Course course2 = new Course("Jane Lim", "451 CSB", "17:40-18:55", 600);
    courses.put("1234", course1);
    courses.put("5678", course2);
    Department department = new Department("COMS", courses, "Jae Lee", 3);
    HashMap<String, Department> mapping = new HashMap<>();
    mapping.put("COMS", department);
    Mockito.when(myFileDatabase.getDepartmentMapping()).thenReturn(mapping);
    IndividualProjectApplication.overrideDatabase(myFileDatabase);
    ResultActions response = mockMvc.perform(get("/getMajorCountFromDept")
            .param("deptCode", "ECON")
            .accept(MediaType.APPLICATION_JSON));
    response.andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string("Department Not Found"));
  }

  @Test
  public void identifyDeptChairTest() throws Exception {
    HashMap<String, Course> courses = new HashMap<>();
    Course course1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 500);
    Course course2 = new Course("Jane Lim", "451 CSB", "17:40-18:55", 600);
    courses.put("1234", course1);
    courses.put("5678", course2);
    Department department = new Department("COMS", courses, "Jae Lee", 3);
    HashMap<String, Department> mapping = new HashMap<>();
    mapping.put("COMS", department);
    Mockito.when(myFileDatabase.getDepartmentMapping()).thenReturn(mapping);
    IndividualProjectApplication.overrideDatabase(myFileDatabase);
    ResultActions response = mockMvc.perform(get("/idDeptChair")
            .param("deptCode", "COMS")
            .accept(MediaType.APPLICATION_JSON));
    response.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("Jae Lee is the department chair."));
  }

  @Test
  public void identifyInvalidDeptChairTest() throws Exception {
    HashMap<String, Course> courses = new HashMap<>();
    Course course1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 500);
    Course course2 = new Course("Jane Lim", "451 CSB", "17:40-18:55", 600);
    courses.put("1234", course1);
    courses.put("5678", course2);
    Department department = new Department("COMS", courses, "Jae Lee", 3);
    HashMap<String, Department> mapping = new HashMap<>();
    mapping.put("COMS", department);
    Mockito.when(myFileDatabase.getDepartmentMapping()).thenReturn(mapping);
    IndividualProjectApplication.overrideDatabase(myFileDatabase);
    ResultActions response = mockMvc.perform(get("/idDeptChair")
            .param("deptCode", "ECON")
            .accept(MediaType.APPLICATION_JSON));
    response.andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string("Department Not Found"));
  }

  @Test
  public void findCourseLocationTest() throws Exception {
    HashMap<String, Course> courses = new HashMap<>();
    Course course1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 500);
    Course course2 = new Course("Jane Lim", "451 CSB", "17:40-18:55", 600);
    courses.put("1234", course1);
    courses.put("5678", course2);
    Department department = new Department("COMS", courses, "Jae Lee", 3);
    HashMap<String, Department> mapping = new HashMap<>();
    mapping.put("COMS", department);
    Mockito.when(myFileDatabase.getDepartmentMapping()).thenReturn(mapping);
    IndividualProjectApplication.overrideDatabase(myFileDatabase);
    ResultActions response = mockMvc.perform(get("/findCourseLocation")
            .param("deptCode", "COMS")
            .param("courseCode", "1234")
            .accept(MediaType.APPLICATION_JSON));
    response.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(
                    "417 IAB is where the course is located."));
  }

  @Test
  public void findInvalidCourseLocationTest() throws Exception {
    HashMap<String, Course> courses = new HashMap<>();
    Course course1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 500);
    Course course2 = new Course("Jane Lim", "451 CSB", "17:40-18:55", 600);
    courses.put("1234", course1);
    courses.put("5678", course2);
    Department department = new Department("COMS", courses, "Jae Lee", 3);
    HashMap<String, Department> mapping = new HashMap<>();
    mapping.put("COMS", department);
    Mockito.when(myFileDatabase.getDepartmentMapping()).thenReturn(mapping);
    IndividualProjectApplication.overrideDatabase(myFileDatabase);
    ResultActions response = mockMvc.perform(get("/findCourseLocation")
            .param("deptCode", "COMS")
            .param("courseCode", "1215")
            .accept(MediaType.APPLICATION_JSON));
    response.andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string("Course Not Found"));
  }

  @Test
  public void findInvalidDeptCourseLocationTest() throws Exception {
    HashMap<String, Course> courses = new HashMap<>();
    Course course1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 500);
    Course course2 = new Course("Jane Lim", "451 CSB", "17:40-18:55", 600);
    courses.put("1234", course1);
    courses.put("5678", course2);
    Department department = new Department("COMS", courses, "Jae Lee", 3);
    HashMap<String, Department> mapping = new HashMap<>();
    mapping.put("COMS", department);
    Mockito.when(myFileDatabase.getDepartmentMapping()).thenReturn(mapping);
    IndividualProjectApplication.overrideDatabase(myFileDatabase);
    ResultActions response = mockMvc.perform(get("/findCourseLocation")
            .param("deptCode", "ECON")
            .param("courseCode", "1234")
            .accept(MediaType.APPLICATION_JSON));
    response.andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string("Department Not Found"));
  }

  @Test
  public void findCourseInstructorTest() throws Exception {
    HashMap<String, Course> courses = new HashMap<>();
    Course course1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 500);
    Course course2 = new Course("Jane Lim", "451 CSB", "17:40-18:55", 600);
    courses.put("1234", course1);
    courses.put("5678", course2);
    Department department = new Department("COMS", courses, "Jae Lee", 3);
    HashMap<String, Department> mapping = new HashMap<>();
    mapping.put("COMS", department);
    Mockito.when(myFileDatabase.getDepartmentMapping()).thenReturn(mapping);
    IndividualProjectApplication.overrideDatabase(myFileDatabase);
    ResultActions response = mockMvc.perform(get("/findCourseInstructor")
            .param("deptCode", "COMS")
            .param("courseCode", "1234")
            .accept(MediaType.APPLICATION_JSON));
    response.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(
                    "Griffin Newbold is the instructor for the course."));
  }

  @Test
  public void findInvalidCourseInstructorTest() throws Exception {
    HashMap<String, Course> courses = new HashMap<>();
    Course course1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 500);
    Course course2 = new Course("Jane Lim", "451 CSB", "17:40-18:55", 600);
    courses.put("1234", course1);
    courses.put("5678", course2);
    Department department = new Department("COMS", courses, "Jae Lee", 3);
    HashMap<String, Department> mapping = new HashMap<>();
    mapping.put("COMS", department);
    Mockito.when(myFileDatabase.getDepartmentMapping()).thenReturn(mapping);
    IndividualProjectApplication.overrideDatabase(myFileDatabase);
    ResultActions response = mockMvc.perform(get("/findCourseInstructor")
            .param("deptCode", "COMS")
            .param("courseCode", "1215")
            .accept(MediaType.APPLICATION_JSON));
    response.andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string("Course Not Found"));
  }

  @Test
  public void findCourseInstructorInvalidDeptTest() throws Exception {
    HashMap<String, Course> courses = new HashMap<>();
    Course course1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 500);
    Course course2 = new Course("Jane Lim", "451 CSB", "17:40-18:55", 600);
    courses.put("1234", course1);
    courses.put("5678", course2);
    Department department = new Department("COMS", courses, "Jae Lee", 3);
    HashMap<String, Department> mapping = new HashMap<>();
    mapping.put("COMS", department);
    Mockito.when(myFileDatabase.getDepartmentMapping()).thenReturn(mapping);
    IndividualProjectApplication.overrideDatabase(myFileDatabase);
    ResultActions response = mockMvc.perform(get("/findCourseInstructor")
            .param("deptCode", "MATH")
            .param("courseCode", "1234")
            .accept(MediaType.APPLICATION_JSON));
    response.andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string("Department Not Found"));
  }

  @Test
  public void findCourseTimeTest() throws Exception {
    HashMap<String, Course> courses = new HashMap<>();
    Course course1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 500);
    Course course2 = new Course("Jane Lim", "451 CSB", "17:40-18:55", 600);
    courses.put("1234", course1);
    courses.put("5678", course2);
    Department department = new Department("COMS", courses, "Jae Lee", 3);
    HashMap<String, Department> mapping = new HashMap<>();
    mapping.put("COMS", department);
    Mockito.when(myFileDatabase.getDepartmentMapping()).thenReturn(mapping);
    IndividualProjectApplication.overrideDatabase(myFileDatabase);
    ResultActions response = mockMvc.perform(get("/findCourseTime")
            .param("deptCode", "COMS")
            .param("courseCode", "1234")
            .accept(MediaType.APPLICATION_JSON));
    response.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("The course meets at: 11:40-12:55"));
  }

  @Test
  public void findInvalidCourseTimeTest() throws Exception {
    HashMap<String, Course> courses = new HashMap<>();
    Course course1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 500);
    Course course2 = new Course("Jane Lim", "451 CSB", "17:40-18:55", 600);
    courses.put("1234", course1);
    courses.put("5678", course2);
    Department department = new Department("COMS", courses, "Jae Lee", 3);
    HashMap<String, Department> mapping = new HashMap<>();
    mapping.put("COMS", department);
    Mockito.when(myFileDatabase.getDepartmentMapping()).thenReturn(mapping);
    IndividualProjectApplication.overrideDatabase(myFileDatabase);
    ResultActions response = mockMvc.perform(get("/findCourseTime")
            .param("deptCode", "COMS")
            .param("courseCode", "1215")
            .accept(MediaType.APPLICATION_JSON));
    response.andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string("Course Not Found"));
  }

  @Test
  public void findCourseTimeInvalidDeptTest() throws Exception {
    HashMap<String, Course> courses = new HashMap<>();
    Course course1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 500);
    Course course2 = new Course("Jane Lim", "451 CSB", "17:40-18:55", 600);
    courses.put("1234", course1);
    courses.put("5678", course2);
    Department department = new Department("COMS", courses, "Jae Lee", 3);
    HashMap<String, Department> mapping = new HashMap<>();
    mapping.put("COMS", department);
    Mockito.when(myFileDatabase.getDepartmentMapping()).thenReturn(mapping);
    IndividualProjectApplication.overrideDatabase(myFileDatabase);
    ResultActions response = mockMvc.perform(get("/findCourseTime")
            .param("deptCode", "MATH")
            .param("courseCode", "1234")
            .accept(MediaType.APPLICATION_JSON));
    response.andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string("Department Not Found"));
  }

}
