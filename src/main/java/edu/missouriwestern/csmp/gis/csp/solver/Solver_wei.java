package edu.missouriwestern.csmp.gis.csp.solver;


import edu.missouriwestern.csmp.gis.csp.courses.Course;
import edu.missouriwestern.csmp.gis.csp.courses.CourseRepository;
import edu.missouriwestern.csmp.gis.csp.semester_type.SemesterTypeRepository;
import edu.missouriwestern.csmp.gis.csp.semester_type.Semester_Type;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.SetVar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Formatter;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/solving")
public class Solver_wei {
    Model model;
    IntVar[] courseVars;
    List<Course> programCourses;

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
    public List<Course> courses(String courseNam, String semestr) {
        programCourses = courseRepository.getProgramCourses(2);
//        exampleSolver();
//        if(true) {
//            return programCourses;
//        }

        long startTime = System.nanoTime();

        // Maximum terms over all
        int maxTerms = 8;

        // Amount of courses to be considered

        ////////////
        // MODEL: //
        ////////////
        // CS-Model
        int numberOfCourses = programCourses.size();
        int maxCoursesPerTerm = Math.floorDiv(numberOfCourses , maxTerms);

        model = new Model("GIS Course Scheduling");

        // Variables: courses with a term number between 0 and max terms
        courseVars = new IntVar[numberOfCourses];
        for (int i = 0; i < numberOfCourses; i++) {
            courseVars[i] = model.intVar(Integer.toString(programCourses.get(i).getCourseId()), 0, maxTerms - 1, false);
        }

        // Variables: credit points
        int[] courseCredits = programCourses.stream().mapToInt(x -> x.getCredits()).toArray();


        int[] courseNumbers = new int[numberOfCourses];

        for(int x = 0; x < numberOfCourses; x++ ){
            courseNumbers[x] = x;
        }
                //programCourses.stream().mapToInt(x -> x.getCourseId()).toArray();
        SetVar[] scheduledCourses = model.setVarArray("scheduledCourses", maxTerms, new int[]{}, courseNumbers);
        IntVar[] credits = model.intVarArray(maxTerms, 3* maxCoursesPerTerm, 5* maxCoursesPerTerm); // consider to make a 0-100 bound and set two constraints >= 15 and <= 21 (more efficient)

        //////////////////
        // CONSTRAINTS: //
        //////////////////

        setPrerequisiteConstraints();

       // setSemesterConstraints();
        // Constraints: calculate credits
        for (int term = 0; term < maxTerms; term++) {
            // Check which courses are scheduled to which term
            for (int course = 0; course < numberOfCourses; course++) {
                model.ifThenElse(
                        model.arithm(courseVars[course], "=", term),
                        model.member(course, scheduledCourses[term]),
                        model.notMember(course, scheduledCourses[term])
                );
            }

            // Calculate achieved credit points for each term
            model.sumElements(scheduledCourses[term], courseCredits, credits[term]).post();
        }

        //////////////
        // SOLVING: //
        //////////////
        final Solver solver = model.getSolver();
        solver.showShortStatistics();
        solver.showStatistics();
        solver.showContradiction();
        Solution solution = solver.findSolution();
        printSolution(solution, maxTerms, courseVars);

        long estimatedTime = System.nanoTime() - startTime;
        long seconds = estimatedTime / 1000000000;
        System.out.println("Estimated time: " + seconds / 60 + " min");
        return  programCourses.stream().filter(x -> Arrays.stream(courseVars).map(y -> y.getName()).collect(Collectors.toList()).contains(Integer.toString(x.getCourseId()))).collect(Collectors.toList());

    }

    private void setSemesterConstraints() {

        for (Course course : programCourses ) {
            for (var courseVar : courseVars) {
                if(course.getCourseId() == Integer.parseInt(courseVar.getName())){
                   int courseSemesterTypeId = course.getSemesterTypeId();

                   switch (courseSemesterTypeId) {
                       case 1:
                           model.arithm(courseVar.mod(4).intVar(), "=", 0).post();
                           break;
                   }

                }
            }
        }

    }

    private void setPrerequisiteConstraints() {
        for (Course course : programCourses ) {
            for (var courseVar : courseVars) {
                if(course.getCourseId() == Integer.parseInt(courseVar.getName())){
                    for (var preRequisite : course.getPrerequisiteSetList()) {
                        var preRequisiteVar = Arrays.stream(courseVars) .filter(x -> x.getName().equals(preRequisite.getCourseId())).findFirst().orElse(null);
                        if(preRequisiteVar == null) continue;
                        model.arithm(courseVar, ">", preRequisiteVar).post();
                        int courseSemesterTypeId = course.getSemesterTypeId();
                    }
                    continue;
                }
            }
        }
    }

    private void printSolution(Solution solution, int maxTerms, IntVar[] courseVars) {
        String[] output = new String[maxTerms];
        if (solution != null) {
            for (IntVar courseVar : courseVars) {
                int row = solution.getIntVal(courseVar);
                Course course = programCourses.stream().filter(x -> x.getCourseId() == Integer.parseInt(courseVar.getName())).findAny().orElse(null);
                String courseName = course.getName();
                if (output[row] != null) {
                    output[row] += courseName + "   ";
                } else {
                    output[row] = courseName + "   ";
                }
            }
            for (int i = 0; i < maxTerms; i++) {
                if (output[i] != null) {
                    System.out.println((i + 1) + ". Sem:   " + output[i]);
                } else {
                    System.out.println((i + 1) + ". Sem:   ");
                }
            }
        } else {
            System.out.println("NO SOLUTION FOUND!");
        }
    }
}
