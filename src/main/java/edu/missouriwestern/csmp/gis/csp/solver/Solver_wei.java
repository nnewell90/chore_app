package edu.missouriwestern.csmp.gis.csp.solver;


import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.variables.IntVar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/solving")
public class Solver_wei {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping
    public String check() {
        return "it works on solving";
    }

    @RequestMapping(value = "/solver/{courseName}/{semester}",method = RequestMethod.GET)
    public String getcourses(@PathVariable  String courseName,@PathVariable String semester) {
        return courses(courseName,semester);

    }


    @GetMapping(path = "/solver")
    public String courses(String courseName,String semester) {
        String solutionToString = "";
        int num=0;
        Model model = new Model("-csc courses problem");
        IntVar[] cscList=setList(model);
        //with special conditions
        if (semester != null&&courseName!=null) {
            int semesterNum = Integer.parseInt(semester);
            int index=getIndex(cscList,courseName,model);
            System.out.println("index is "+index);
            IntVar[] newList=setNewList(cscList,index);
            for(int i=0;i<newList.length;i++){
                System.out.println(newList[i]);
            }
            for(int i =0;i< newList.length;i++){
                model.post(
                        model.arithm(cscList[index], "<", newList[i])


                );
            }
            model.post(
                    model.arithm(cscList[index], "=", semesterNum)
            );
            //constraints for courses (example csc254 has to greater than csc184)

        }
        Solver mysolver = model.getSolver();
        mysolver.showStatistics();
        mysolver.setSearch(Search.domOverWDegSearch(cscList));
        Solution solution = mysolver.findSolution();
        solutionToString = solution.toString();
        return solutionToString;
    }

    private IntVar[] setNewList(IntVar[] cscList,int index) {
        int num=0;
        for (int i =0;i<cscList.length;i++){
        if(cscList[i].getValue()>cscList[index].getValue()){
            num++;
        }
        }
        System.out.println("number is "+num);
        IntVar[] newList = new IntVar[num];
        for (int i=0;i<cscList.length;i++){
        if(cscList[i].getValue()>cscList[index].getValue()){
           newList[num-8]=cscList[i];
           num++;
        }
        }
        return newList;
    }

    public IntVar[] setList(Model model){
        int n = 8;
        int size = jdbcTemplate.queryForObject("select count(*) from gis.orderbysetnumber", Integer.class);
        IntVar[] cscList = new IntVar[size];
        //assign the numbers to courses
        for (int i = 0; i < size; i++) {
            int semesterNumber = jdbcTemplate.queryForObject("select SemesterNum from gis.orderbysetnumber limit "+i+","+"1", Integer.class);
            String name = jdbcTemplate.queryForObject("select Name from gis.orderbysetnumber where CourseId="+(i+1), String.class);
            cscList[i] = model.intVar(name,semesterNumber , n, false);
        }
        return cscList;
    }
public int getIndex(IntVar[] cscList,String courseName,Model model){
    int size = jdbcTemplate.queryForObject("select count(*) from gis.orderbysetnumber", Integer.class);
        int index=0;
    for (int i = 0; i<size; i++) {
        if(cscList[i].getName().toUpperCase().equals(courseName.toUpperCase())){
            index=i;
            break;
        }
    }
    return index;
}
}
