package edu.missouriwestern.csmp.gis.csp.solver;


import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.variables.IntVar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/solving")
public class Solver_wei {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @GetMapping
    public String check() {
        return "it works on solving";
    }
    @GetMapping(path = "/solver")
    public String courses(){
        String solutionToString="";
        int n = 8;
        int size= jdbcTemplate.queryForObject("select count(*) from gis.precourses", Integer.class);;
        Model model = new Model(n + "-csc courses problem");
        IntVar[] cscList = new IntVar[size];
        for(int i = 0; i<size;i++){
            String name = jdbcTemplate.queryForObject("select Name from gis.precourses limit " + i + ",1", String.class);
            cscList[i]=model.intVar(name,1,n,false);
            int setNum = jdbcTemplate.queryForObject("select SetNumber from gis.precourses limit " + i + ",1", Integer.class);
            model.post(
                    model.arithm(cscList[i], ">=", setNum),
                    model.arithm(cscList[i],"<=",5)
            );
        }
        //IntVar[] diag1 = IntStream.range(0, n).mapToObj(i -> cscList[i].sub(i).intVar()).toArray(IntVar[]::new);
//        for(int i =0;i<size;i++) {
//            int setNum = jdbcTemplate.queryForObject("select SetNumber from gis.precourses limit " + i + ",1", Integer.class);
//            model.post(
//                    model.arithm(cscList[i], ">=", setNum)
//                    );
//        }
        Solver mysolver = model.getSolver();
        mysolver.showStatistics();
        mysolver.setSearch(Search.domOverWDegSearch(cscList));
        Solution solution = mysolver.findSolution();
        solutionToString=solution.toString();
        return solutionToString;
    }
}
