package edu.missouriwestern.csmp.gis.csp.solver;


import edu.missouriwestern.csmp.gis.csp.courses.Course;
import edu.missouriwestern.csmp.gis.csp.courses.CourseRepository;
import edu.missouriwestern.csmp.gis.csp.semester_type.SemesterTypeRepository;
import edu.missouriwestern.csmp.gis.csp.semester_type.Semester_Type;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Formatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/solving")
public class Solver_wei {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    SemesterTypeRepository semesterTypeRepository;

    @GetMapping
    public String check() {
        return "it works on solving";
    }

    @RequestMapping(value = "/solver/{courseName}/{semester}",method = RequestMethod.GET)
    public String getcourses(@PathVariable  String courseName,@PathVariable String semester) {
      //  return courses(courseName,semester);
return "";
    }

    @GetMapping(path = "/solver")
    public List<Course> courses(String courseNam, String semester) {

        List<Course> programCourses = courseRepository.getProgramCourses(2);
        Course emptyCourse = new Course();
        emptyCourse.setName("____");
        emptyCourse.setCredits(0);
        programCourses.add(0, emptyCourse);
        ////////////
        // MODEL: //
        ////////////
        List<String> courseNames = programCourses.stream().map(x -> x.getName()).collect(Collectors.toList());

        // load parameters
        int maxCoursesPerTerm;
        int numberOfCourses = courseNames.size();   // warehouses (28 + 1 blank)
        int maxTerms = 8;       // stores
        int[] achievableCreditPoints = programCourses.stream().mapToInt(x -> x.getCredits()).toArray();

        Model model = new Model("GIS Course Scheduling");

        maxCoursesPerTerm = Math.floorDiv(numberOfCourses , maxTerms);
        System.out.println(numberOfCourses + " " + maxTerms);
        System.out.println(maxCoursesPerTerm);
        // VARIABLES
        // a course is either scheduled or not

        IntVar[] terms = model.intVarArray("terms", maxTerms * maxCoursesPerTerm, 0, numberOfCourses - 1, false);

        // credit points per term
        IntVar[][] points = new IntVar[maxTerms][];
        for (int i = 0; i < maxTerms; i++) {
            points[i] = model.intVarArray ("points_"+i, maxCoursesPerTerm, 0, 7, true);
        }

        // accumulated points
        IntVar[] accPoints = new IntVar[maxTerms];
        for (int i = 0; i < maxTerms; i++) {
            accPoints[i] = model.intVar("accPoints"+i, 0, 100, false);
        }



        //////////////////
        // CONSTRAINTS: //
        //////////////////

        model.allDifferentExcept0(terms).post();
        for (int j = 0; j < maxTerms; j++) {
            // a course is scheduled, if it is 'bound' to a store
            for (int i = 0; i < maxCoursesPerTerm; i++) {
                model.element( points[j][i], achievableCreditPoints, terms[(j * maxCoursesPerTerm) + i], 0).post(); // Compute credit points for each term
            }
            model.sum(points[j], "=", accPoints[j]).post();
            model.sum(points[j], ">=", 3 * maxCoursesPerTerm).post();
            model.sum(points[j], "<=", 5 * maxCoursesPerTerm).post();
        }

        //////////////
        // SOLVING: //
        //////////////

        Solver solver = model.getSolver();
        solver.showShortStatistics();
        Solution solution = solver.findSolution();

        String[] output = new String[maxTerms];
        if (solution != null) {
            for (int i = 0; i < maxTerms; i++) {
                int start = i * maxCoursesPerTerm;
                for (int j = start; j < (start + maxCoursesPerTerm); j++) {
                    String courseName = courseNames.get(terms[j].getValue());
                    if (output[i] != null) {
                        output[i] += courseName + "   ";
                    } else {
                        output[i] = courseName + "   ";
                    }
                }
                System.out.println((i + 1)+ ". Sem:   " + output[i]);
            }
        } else {
            System.out.println("NO SOLUTION FOUND!");
        }



//        List<String> courseNames = programCourses.stream().map(x -> x.getName()).collect(Collectors.toList());
//        int maxCoursePerTerm = 5;
//        int numberOfCourses = courseNames.size();
//        int maxTerms = 8;
//
//
//        int[] achievableCreditPointsArray = programCourses.stream().mapToInt(x -> x.getCredits()).toArray();
//
//        Model model = new Model("GIS Course Scheduling");
//
//        IntVar[] terms = model.intVarArray("Terms", maxTerms * maxCoursePerTerm, 0 , numberOfCourses , false);
//
//        IntVar[][] points = new IntVar[maxTerms][];
//        for (int i = 0; i < maxTerms; i++) {
//            points[i] = model.intVarArray("points_" + 1, maxCoursePerTerm, 0, 5, true);
//        }
//
//        IntVar[] accumulatedPoints = new IntVar[maxTerms];
//        for (int i = 0; i < maxTerms; i++) {
//            accumulatedPoints[i] = model.intVar("accumulatedPoints" +i, 0, 100, false);
//        }
//
//
//        //CONSTRAINTS
//        model.allDifferentExcept0(terms).post();
//
//        for (int i = 0; i < maxTerms; i++) {
//            for (int j = 0; j < maxCoursePerTerm; j++) {
//                model.element(points[i][j], achievableCreditPointsArray, terms[(i * maxCoursePerTerm) + 1], 0).post();
//
//            }
//            model.sum(points[i], "=", accumulatedPoints[i]).post();
//            model.sum(points[i], ">=", 12).post();
//            model.sum(points[i], "<=", 18).post();
//        }
//
//        //Solving
//
//        Solver solver = model.getSolver();
//        solver.showShortStatistics();
//        Solution solution = solver.findSolution();
//
//        String[] output = new String[maxTerms];
//        String returnString = "";
//        if (solution != null) {
//            for (int i = 0; i < maxTerms; i++) {
//                int start = i * maxCoursePerTerm;
//                for (int j = start; j < maxCoursePerTerm ; j++) {
//                    String courseName = courseNames.get(terms[j].getValue());
//                    if (output[i] != null) {
//                        output[i] += courseName + "   ";
//                    } else {
//                        output[i] = courseName + "   ";
//                    }
//                }
//
//                System.out.println(((i + 1) + " " + output[i]));
//            }
//        }else {
//            System.out.println("No Solution found");
//        }
        return programCourses;
    }
}
