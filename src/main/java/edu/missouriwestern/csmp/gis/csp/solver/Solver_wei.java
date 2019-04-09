package edu.missouriwestern.csmp.gis.csp.solver;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.variables.IntVar;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/solving")
public class Solver_wei {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping
    public String check() {
        return "it works on solving";
    }

    @RequestMapping(value = "/solver/{courseName}/{semester}", method = RequestMethod.GET)
    public JSONArray getcourses(@PathVariable String courseName, @PathVariable String semester) {
        return courses(courseName, semester);

    }
    @GetMapping(path = "/solver")
    public JSONArray courses(String courseName, String semester) {
        String solutionToString;
        Model model = new Model("-csc courses problem");
        IntVar[] cscList = getList(model);
        JSONArray credits = getCredit();

        model.post(
                model.arithm(cscList[0], "=", 2),
                // csc208 requires concurrent enrollment csc254,MAT166 or MAT167
                model.arithm(cscList[getIndex(cscList, "CSC208")], ">=", cscList[getIndex(cscList, "CSC254")]),
                model.arithm(cscList[getIndex(cscList, "CSC208")], ">=", cscList[getIndex(cscList, "MAT166")]),
                model.arithm(cscList[getIndex(cscList, "CSC208")], ">=", cscList[getIndex(cscList, "MAT167")]),
                //csc246 requires csc254
                model.arithm(cscList[getIndex(cscList, "CSC246")], ">", cscList[getIndex(cscList, "CSC254")]),
                //csc254 requires csc184
                model.arithm(cscList[getIndex(cscList, "CSC254")], ">", cscList[getIndex(cscList, "CSC184")]),
                //csc294 requires csc184
                model.arithm(cscList[getIndex(cscList, "CSC294")], ">", cscList[getIndex(cscList, "CSC184")]),
                //csc328 requires csc285
                model.arithm(cscList[getIndex(cscList, "CSC328")], ">", cscList[getIndex(cscList, "CSC285")]),
                //csc406 requires csc285 or concurrent enrollment in CSC 305
                model.arithm(cscList[getIndex(cscList, "CSC406")], ">", cscList[getIndex(cscList, "CSC285")]),
                model.arithm(cscList[getIndex(cscList, "CSC406")], ">=", cscList[getIndex(cscList, "CSC305")]),
                //csc410 requires csc294
                model.arithm(cscList[getIndex(cscList, "CSC410")], ">", cscList[getIndex(cscList, "CSC294")]),
                //act301 requires csc184 or csc201
                model.arithm(cscList[getIndex(cscList, "ACT301")], ">", cscList[getIndex(cscList, "CSC184")]),
                //csc285 requires csc254
                model.arithm(cscList[getIndex(cscList, "CSC285")], ">", cscList[getIndex(cscList, "CSC254")])
        );
        //with special conditions
        Solver mysolver = model.getSolver();
        mysolver.showStatistics();
        mysolver.setSearch(Search.domOverWDegSearch(cscList));
        Solution solution = mysolver.findSolution();
        solutionToString = solution.toString();
        JSONArray result=result(solutionToString,credits);
        for(int i =0;i<result.size();i++){
            System.out.println(result.get(i).toString());
        }
        return result;
    }

    public JSONArray getCredit() {
        int size = jdbcTemplate.queryForObject("select count(*)\n" +
                "from programs a\n" +
                "join program_courses b on a.ProgramId = b.ProgramId\n" +
                "join courses c on b.CourseId = c.CourseId\n" +
                "join semester_type d on c.SemesterTypeId = d.SemesterTypeId\n" +
                "join precourses p on c.Name=p.Name", Integer.class);
        JSONArray creditList = new JSONArray();
        List<Map<String, Object>> credits = jdbcTemplate.queryForList("select c.Credits\n" +
                "from programs a\n" +
                "join program_courses b on a.ProgramId = b.ProgramId\n" +
                "join courses c on b.CourseId = c.CourseId\n" +
                "join semester_type d on c.SemesterTypeId = d.SemesterTypeId\n" +
                "join precourses p on c.Name=p.Name");
        List<Map<String, Object>> names = jdbcTemplate.queryForList("select c.Name\n" +
                "from programs a\n" +
                "join program_courses b on a.ProgramId = b.ProgramId\n" +
                "join courses c on b.CourseId = c.CourseId\n" +
                "join semester_type d on c.SemesterTypeId = d.SemesterTypeId\n" +
                "join precourses p on c.Name=p.Name");

        //assign name(key) and credit(value)
        for (int i = 0; i < size; i++) {
            JSONObject course = new JSONObject();
            String name = String.valueOf(names.get(i).values()).substring(1, (String.valueOf(names.get(i).values()).length()) - 1);
            int credit = Integer.parseInt(String.valueOf(credits.get(i)).substring(credits.get(i).toString().length()-2,credits.get(i).toString().length()-1 ));
            System.out.println("Name:"+name+", Credit: "+credit);
            course.put(name, credit);
            creditList.add(course);
        }
        return creditList;

    }

    //Assign the range to the class projects
    public IntVar[] getList(Model model) {
        int n = 8;
        int size = jdbcTemplate.queryForObject("select count(*)\n" +
                "from programs a\n" +
                "join program_courses b on a.ProgramId = b.ProgramId\n" +
                "join courses c on b.CourseId = c.CourseId\n" +
                "join semester_type d on c.SemesterTypeId = d.SemesterTypeId\n" +
                "join precourses p on c.Name=p.Name", Integer.class);
        IntVar[] cscList = new IntVar[size];
        List<Map<String, Object>> earliestSemester = jdbcTemplate.queryForList("select p.SetNumber as EarliestSemester\n" +
                "from programs a\n" +
                "join program_courses b on a.ProgramId = b.ProgramId\n" +
                "join courses c on b.CourseId = c.CourseId\n" +
                "join semester_type d on c.SemesterTypeId = d.SemesterTypeId\n" +
                "join precourses p on c.Name=p.Name");
        List<Map<String, Object>> name = jdbcTemplate.queryForList("select c.Name\n" +
                "from programs a\n" +
                "join program_courses b on a.ProgramId = b.ProgramId\n" +
                "join courses c on b.CourseId = c.CourseId\n" +
                "join semester_type d on c.SemesterTypeId = d.SemesterTypeId\n" +
                "join precourses p on c.Name=p.Name");
        //assign the numbers to courses
        for (int i = 0; i < size; i++) {
            cscList[i] = model.intVar(String.valueOf(name.get(i).values()).substring(1, (String.valueOf(name.get(i).values()).length()) - 1), Integer.parseInt(String.valueOf(earliestSemester.get(i)).substring(18, 19)), n, false);
        }
        return cscList;
    }

    public int getSize() {
        int size = jdbcTemplate.queryForObject("select count(*)\n" +
                "from programs a\n" +
                "join program_courses b on a.ProgramId = b.ProgramId\n" +
                "join courses c on b.CourseId = c.CourseId\n" +
                "join semester_type d on c.SemesterTypeId = d.SemesterTypeId\n" +
                "join precourses p on c.Name=p.Name", Integer.class);

        return size;
    }

    public int getIndex(IntVar[] searchList, String course) {
        int index = 0;
        for (int i = 0; i < searchList.length; i++) {
            if (course.equals(searchList[i].getName())) {
                index = i;
            }
        }
        return index;
    }
    public JSONArray result(String solution, JSONArray credit) {
        JSONArray result = new JSONArray();
        JSONObject firstSp = new JSONObject(), secondSp = new JSONObject(), thirdSp = new JSONObject(), forthSp = new JSONObject(),
                firstFa = new JSONObject(), secondFa = new JSONObject(), thirdFa = new JSONObject(), forthFa = new JSONObject();

        JSONObject firstSpSet = new JSONObject(),
                secondSpSet = new JSONObject(),
                thirdSpSet = new JSONObject(),
                forthSpSet = new JSONObject(),
                firstFaSet = new JSONObject(),
                secondFaSet = new JSONObject(),
                thirdFaSet = new JSONObject(),
                forthFaSet = new JSONObject();
        String[] solutions = solution.split(":");
        String[] solutionSet = solutions[1].split(",");
//        for(int i=0;i<solutionSet.length;i++){
//            System.out.println(solutionSet[i]);
//        }
        for (int i = 0; i < credit.size(); i++) {
            if (solutionSet[i].endsWith("1")) {
                firstSp.put(getName(credit, i), getCredit(credit, i));
                System.out.println(firstSp);
            } else if (solutionSet[i].endsWith("2")) {
                firstFa.put(getName(credit, i), getCredit(credit, i));
            } else if (solutionSet[i].endsWith("3")) {
                secondSp.put(getName(credit, i), getCredit(credit, i));
            } else if (solutionSet[i].endsWith("4")) {
                secondFa.put(getName(credit, i), getCredit(credit, i));
            } else if (solutionSet[i].endsWith("5")) {
                thirdSp.put(getName(credit, i), getCredit(credit, i));
            } else if (solutionSet[i].endsWith("6")) {
                thirdFa.put(getName(credit, i), getCredit(credit, i));
            } else if (solutionSet[i].endsWith("7")) {
                forthSp.put(getName(credit, i), getCredit(credit, i));
            } else {
                forthFa.put(getName(credit, i), getCredit(credit, i));
            }
        }

        firstSpSet.put("First Spring", firstSp);
        firstFaSet.put("First Fall", firstFa);
        secondSpSet.put("Second Spring", secondSp);
        secondFaSet.put("Second Fall", secondFa);
        thirdSpSet.put("Third Spring", thirdSp);
        thirdFaSet.put("Third Fall", thirdFa);
        forthSpSet.put("Forth Spring", forthSp);
        forthFaSet.put("Forth Fall", forthFa);
        result.add(firstSpSet);
        result.add(firstFaSet);
        result.add(secondSpSet);
        result.add(secondFaSet);
        result.add(thirdSpSet);
        result.add(thirdFaSet);
        result.add(forthSpSet);
        result.add(forthFaSet);

        return result;
    }

    public String getName(JSONArray array, int i) {
        String name = array.get(i).toString().substring(array.get(i).toString().indexOf("\"") + 1);
        name = name.substring(0, name.indexOf("\""));
        return name;
    }

    public String getCredit(JSONArray array, int i) {
        String credit = array.get(i).toString().substring((array.get(i).toString().length() - 2), (array.get(1).toString().length()) - 1);
        return credit;
    }
}
