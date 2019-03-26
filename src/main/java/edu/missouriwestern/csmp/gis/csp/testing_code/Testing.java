package edu.missouriwestern.csmp.gis.csp.testing_code;


import edu.missouriwestern.csmp.gis.csp.course_prerequisites_wei.Course_prerequisite_wei;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.variables.IntVar;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;

@RestController
@RequestMapping(path = "/testing")
public class Testing {
    @GetMapping
    public String check() {
        return "it works on testing";
    }
    @GetMapping(path = "/test")
    public String EightQueen(){
        String solutionToString="";
//        int n = 8;
//        Model model = new Model(n + "-csc courses problem");
//        IntVar[] cscList = new IntVar[5];
//        cscList[0] = model.intVar("csc184",1,n,false);
//        cscList[1] = model.intVar("csc245",1,n,false);
//        cscList[2] = model.intVar("csc254",1,n,false);
//        cscList[3] = model.intVar("csc406",1,n,false);
//        cscList[4] = model.intVar("csc450",1,n,false);
//
//        //IntVar[] diag1 = IntStream.range(0, n).mapToObj(i -> cscList[i].sub(i).intVar()).toArray(IntVar[]::new);
//        model.post(
//                model.arithm(cscList[0],"<",8),
//                model.arithm(cscList[1],"<",8),
//                model.arithm(cscList[2],"<",8),
//                model.arithm(cscList[3],"<",8),
//                model.arithm(cscList[4],"<",8)
//
//
//        );
//        Solver solver = model.getSolver();
//        solver.showStatistics();
//        solver.setSearch(Search.domOverWDegSearch(cscList));
//        Solution solution = solver.findSolution();
//        solutionToString=solution.toString();

        int n = 8;
        Model model = new Model(n + "-queens problem");
        IntVar[] vars = model.intVarArray("Q", n, 1, n, false);
        IntVar[] diag1 = IntStream.range(0, n).mapToObj(i -> vars[i].sub(i).intVar()).toArray(IntVar[]::new);
        IntVar[] diag2 = IntStream.range(0, n).mapToObj(i -> vars[i].add(i).intVar()).toArray(IntVar[]::new);
        model.post(
                model.allDifferent(vars),
                model.allDifferent(diag1),
                model.allDifferent(diag2)
        );
        Solver solver = model.getSolver();
        solver.showStatistics();
        solver.setSearch(Search.domOverWDegSearch(vars));
        Solution solution = solver.findSolution();
        solutionToString=solution.toString();
        return solutionToString;
    }
}
