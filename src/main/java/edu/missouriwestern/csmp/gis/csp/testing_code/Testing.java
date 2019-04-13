//package edu.missouriwestern.csmp.gis.csp.testing_code;
//
//import org.chocosolver.solver.Model;
//import org.chocosolver.solver.Solution;
//import org.chocosolver.solver.Solver;
//import org.chocosolver.solver.search.strategy.Search;
//import org.chocosolver.solver.variables.IntVar;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.stream.IntStream;
//
//@RestController
//@RequestMapping(path = "/testing")
//public class Testing {
//    @Autowired
//    JdbcTemplate jdbcTemplate;
//
//    @GetMapping
//    public String check() {
//        return "it works on testing";
//    }
//
//
//    @RequestMapping(value = "/{courseName}",method = RequestMethod.GET)
//    public int say(@PathVariable String courseName){
//        int n = 8;
//        int size = jdbcTemplate.queryForObject("select count(*) from gis.orderbysetnumber", Integer.class);
//        ;
//        Model model = new Model(n + "-csc courses problem");
//        IntVar[] cscList = new IntVar[size];
//        //assign the numbers to courses
//        for (int i = 0; i < size; i++) {
//            String name = jdbcTemplate.queryForObject("select Name from gis.orderbysetnumber where CourseId="+(i+1), String.class);
//            cscList[i] = model.intVar(name, 1, n, false);
//
//        }
//
//        int index=0;
//        for (int i = 0; i<size; i++) {
//            if(cscList[i].getName().toUpperCase().equals(courseName.toUpperCase())){
//                index=i;
//                break;
//            }
//        }
//        return index;
//    }
//}
