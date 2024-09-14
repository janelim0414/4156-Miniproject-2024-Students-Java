package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Tests for the Application class.
 */
@SpringBootTest
@ContextConfiguration
public class ApplicationUnitTests {

  @BeforeAll
  public static void setupApplicationForTesting() {
    expected = """
            For the ELEN department:\s
            ELEN 3082:\s
            Instructor: Kenneth Shepard; Location: 1205 MUDD; Time: 4:10-6:40
            ELEN 1201:\s
            Instructor: David G Vallancourt; Location: 301 PUP; Time: 4:10-5:25
            ELEN 3401:\s
            Instructor: Keren Bergman; Location: 829 MUDD; Time: 2:40-3:55
            ELEN 4510:\s
            Instructor: Mohamed Kamaludeen; Location: 903 SSW; Time: 7:00-9:30
            ELEN 3331:\s
            Instructor: David G Vallancourt; Location: 203 MATH; Time: 11:40-12:55
            ELEN 4830:\s
            Instructor: Christine P Hendon; Location: 633 MUDD; Time: 10:10-12:40
            ELEN 3701:\s
            Instructor: Irving Kalet; Location: 333 URIS; Time: 2:40-3:55
            ELEN 4702:\s
            Instructor: Alexei Ashikhmin; Location: 332 URIS; Time: 7:00-9:30
            For the CHEM department:\s
            CHEM 1403:\s
            Instructor: Ruben M Savizky; Location: 309 HAV; Time: 6:10-7:25
            CHEM 3080:\s
            Instructor: Milan Delor; Location: 209 HAV; Time: 10:10-11:25
            CHEM 1500:\s
            Instructor: Joseph C Ulichny; Location: 302 HAV; Time: 6:10-9:50
            CHEM 2444:\s
            Instructor: Christopher Eckdahl; Location: 309 HAV; Time: 11:40-12:55
            CHEM 4102:\s
            Instructor: Dalibor Sames; Location: 320 HAV; Time: 10:10-11:25
            CHEM 2045:\s
            Instructor: Luis M Campos; Location: 209 HAV; Time: 1:10-2:25
            CHEM 2494:\s
            Instructor: Talha Siddiqui; Location: 202 HAV; Time: 1:10-5:00
            CHEM 4071:\s
            Instructor: Jonathan S Owen; Location: 320 HAV; Time: 8:40-9:55
            For the PHYS department:\s
            PHYS 4040:\s
            Instructor: James C Hill; Location: 214 PUP; Time: 4:10-5:25
            PHYS 1602:\s
            Instructor: Kerstin M Perez; Location: 428 PUP; Time: 10:10-11:25
            PHYS 3008:\s
            Instructor: William A Zajc; Location: 329 PUP; Time: 10:10-11:25
            PHYS 1201:\s
            Instructor: Eric Raymer; Location: 428 PUP; Time: 2:40-3:55
            PHYS 4003:\s
            Instructor: Frederik Denef; Location: 214 PUP; Time: 4:10-5:25
            PHYS 1001:\s
            Instructor: Szabolcs Marka; Location: 301 PUP; Time: 2:40-3:55
            PHYS 4018:\s
            Instructor: James W McIver; Location: 307 PUP; Time: 2:40-3:55
            PHYS 2802:\s
            Instructor: Yury Levin; Location: 329 PUP; Time: 10:10-12:00
            For the PSYC department:\s
            PSYC 4493:\s
            Instructor: Jennifer Blaze; Location: 200 SCH; Time: 2:10-4:00
            PSYC 1610:\s
            Instructor: Christopher Baldassano; Location: 200 SCH; Time: 10:10-11:25
            PSYC 2235:\s
            Instructor: Katherine T Fox-Glassman; Location: 501 SCH; Time: 11:40-12:55
            PSYC 2620:\s
            Instructor: Jeffrey M Cohen; Location: 303 URIS; Time: 1:10-3:40
            PSYC 3445:\s
            Instructor: Mariam Aly; Location: 405 SCH; Time: 2:10-4:00
            PSYC 1001:\s
            Instructor: Patricia G Lindemann; Location: 501 SCH; Time: 1:10-2:25
            PSYC 3212:\s
            Instructor: Mayron Piccolo; Location: 200 SCH; Time: 2:10-4:00
            PSYC 4236:\s
            Instructor: Trenton Jerde; Location: 405 SCH; Time: 6:10-8:00
            For the COMS department:\s
            COMS 3827:\s
            Instructor: Daniel Rubenstein; Location: 207 Math; Time: 10:10-11:25
            COMS 1004:\s
            Instructor: Adam Cannon; Location: 417 IAB; Time: 11:40-12:55
            COMS 3203:\s
            Instructor: Ansaf Salleb-Aouissi; Location: 301 URIS; Time: 10:10-11:25
            COMS 4156:\s
            Instructor: Gail Kaiser; Location: 501 NWC; Time: 10:10-11:25
            COMS 3157:\s
            Instructor: Jae Lee; Location: 417 IAB; Time: 4:10-5:25
            COMS 3134:\s
            Instructor: Brian Borowski; Location: 301 URIS; Time: 4:10-5:25
            COMS 3251:\s
            Instructor: Tony Dear; Location: 402 CHANDLER; Time: 1:10-3:40
            COMS 3261:\s
            Instructor: Josh Alman; Location: 417 IAB; Time: 2:40-3:55
            For the ECON department:\s
            ECON 1105:\s
            Instructor: Waseem Noor; Location: 309 HAV; Time: 2:40-3:55
            ECON 2257:\s
            Instructor: Tamrat Gashaw; Location: 428 PUP; Time: 10:10-11:25
            ECON 3412:\s
            Instructor: Thomas Piskula; Location: 702 HAM; Time: 11:40-12:55
            ECON 3213:\s
            Instructor: Miles Leahey; Location: 702 HAM; Time: 4:10-5:25
            ECON 3211:\s
            Instructor: Murat Yilmaz; Location: 310 FAY; Time: 4:10-5:25
            ECON 4840:\s
            Instructor: Mark Dean; Location: 142 URIS; Time: 2:40-3:55
            ECON 4710:\s
            Instructor: Matthieu Gomez; Location: 517 HAM; Time: 8:40-9:55
            ECON 4415:\s
            Instructor: Evan D Sadler; Location: 309 HAV; Time: 10:10-11:25
            For the IEOR department:\s
            IEOR 3404:\s
            Instructor: Christopher J Dolan; Location: 303 MUDD; Time: 10:10-11:25
            IEOR 2500:\s
            Instructor: Uday Menon; Location: 627 MUDD; Time: 11:40-12:55
            IEOR 4540:\s
            Instructor: Krzysztof M Choromanski; Location: 633 MUDD; Time: 7:10-9:40
            IEOR 4102:\s
            Instructor: Antonius B Dieker; Location: 209 HAM; Time: 10:10-11:25
            IEOR 4511:\s
            Instructor: Michael Robbins; Location: 633 MUDD; Time: 9:00-11:30
            IEOR 4106:\s
            Instructor: Kaizheng Wang; Location: 501 NWC; Time: 10:10-11:25
            IEOR 4405:\s
            Instructor: Yuri Faenza; Location: 517 HAV; Time: 11:40-12:55
            IEOR 3658:\s
            Instructor: Daniel Lacker; Location: 310 FAY; Time: 10:10-11:25
            """;
  }

  @Test
  public void mainTest() {
    String[] args = new String[] {"setup"};
    IndividualProjectApplication application = new IndividualProjectApplication();
    IndividualProjectApplication.main(args);
    assertEquals(IndividualProjectApplication.myFileDatabase.toString(), expected);
  }

  @Test
  public void runStartUpTest() {
    String[] args = new String[] {};
    IndividualProjectApplication application = new IndividualProjectApplication();
    application.run(args);
    assertEquals(IndividualProjectApplication.myFileDatabase.toString(), expected);
  }

  @Test
  public void runSetUpTest() {
    String[] args = new String[] {"setup"};
    IndividualProjectApplication application = new IndividualProjectApplication();
    application.run(args);
    assertEquals(IndividualProjectApplication.myFileDatabase.toString(), expected);
  }

  @Test
  public void resetDataFileTest() {
    IndividualProjectApplication application = new IndividualProjectApplication();
    MyFileDatabase newDatabase = new MyFileDatabase(1, "./data.txt");
    HashMap<String, Course> courses = new HashMap<>();
    Course testCourse1 = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    Course testCourse2 = new Course("Jane Lim", "451 CSB", "17:40-18:55", 40);
    courses.put("1234", testCourse1);
    courses.put("5678", testCourse2);
    Department department = new Department("COMS", courses, "Jae Lee", 3);
    HashMap<String, Department> mapping = new HashMap<>();
    mapping.put("COMS", department);
    newDatabase.setMapping(mapping);
    IndividualProjectApplication.overrideDatabase(newDatabase);
    application.resetDataFile();
    assertEquals(IndividualProjectApplication.myFileDatabase.toString(), expected);
  }


  public static String expected;
}
