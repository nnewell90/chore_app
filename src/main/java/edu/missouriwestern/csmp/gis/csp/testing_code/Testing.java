//package edu.missouriwestern.csmp.gis.csp.testing_code;
//
//import org.chocosolver.solver.Model;
//import org.chocosolver.solver.Solution;
//import org.chocosolver.solver.Solver;
//import org.chocosolver.solver.search.strategy.Search;
//import org.chocosolver.solver.variables.IntVar;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
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
//    @GetMapping(path = "/test")
//    public JSONArray WriteJSONExample() {
//
//        String[] solution = "Solution: CSC184=2, CSC208=3, CSC246=4, CSC254=3, CSC294=3, CSC328=5, CSC406=5, CSC410=4, MAT167=3, MAT111E=1, MAT111=1, MAT165=3, MAT166=3, ACT301=3, CSC285=4, CSC305=3,".split(":");
//        String[] solutionSet = solution[1].split(",");
//        JSONArray credit = getCredit();
//        JSONArray result=new JSONArray();
//
//       // System.out.println(array.get(1).toString().substring((array.get(1).toString().length() - 2), (array.get(1).toString().length()) - 1));
////        JSONObject firstSpObject=new JSONObject();
////        firstSpObject.put("First","Spring");
//
//        JSONObject firstSp = new JSONObject();
//        JSONObject secondSp = new JSONObject();
//        JSONObject thirdSp = new JSONObject();
//        JSONObject forthSp = new JSONObject();
//        JSONObject firstFa = new JSONObject();
//        JSONObject secondFa = new JSONObject();
//        JSONObject thirdFa = new JSONObject();
//        JSONObject forthFa = new JSONObject();
//
//        JSONObject firstSpSet = new JSONObject();
//        JSONObject secondSpSet = new JSONObject();
//        JSONObject thirdSpSet = new JSONObject();
//        JSONObject forthSpSet = new JSONObject();
//        JSONObject firstFaSet = new JSONObject();
//        JSONObject secondFaSet = new JSONObject();
//        JSONObject thirdFaSet = new JSONObject();
//        JSONObject forthFaSet = new JSONObject();
//
//        for (int i = 0; i < credit.size(); i++) {
//            if (solutionSet[i].endsWith("1")) {
//                firstSp.put(getName(credit,i),getCredit(credit,i));
//            }
//            else if (solutionSet[i].endsWith("2")) {
//                firstFa.put(getName(credit,i),getCredit(credit,i));
//            }else if (solutionSet[i].endsWith("3")) {
//                secondSp.put(getName(credit,i),getCredit(credit,i));
//            }else if (solutionSet[i].endsWith("4")) {
//                secondFa.put(getName(credit,i),getCredit(credit,i));
//            }else if (solutionSet[i].endsWith("5")) {
//                thirdSp.put(getName(credit,i),getCredit(credit,i));
//            }else if (solutionSet[i].endsWith("6")) {
//                thirdFa.put(getName(credit,i),getCredit(credit,i));
//            }else if (solutionSet[i].endsWith("7")) {
//                forthSp.put(getName(credit,i),getCredit(credit,i));
//            }else {
//                forthFa.put(getName(credit,i),getCredit(credit,i));
//            }
//        }
//
//        firstSpSet.put("First Spring",firstSp);
//        firstFaSet.put("First Fall",firstFa);
//        secondSpSet.put("Second Spring",secondSp);
//        secondFaSet.put("Second Fall",secondFa);
//        thirdSpSet.put("Third Spring",thirdSp);
//        thirdFaSet.put("Third Fall",thirdFa);
//        forthSpSet.put("Forth Spring",forthSp);
//        forthFaSet.put("Forth Fall",forthFa);
//        result.add(firstSpSet);
//        result.add(firstFaSet);
//        result.add(secondSpSet);
//        result.add(secondFaSet);
//        result.add(thirdSpSet);
//        result.add(thirdFaSet);
//        result.add(forthSpSet);
//        result.add(forthFaSet);
//
//
//
//        return result;
//
//    }
//    public String getName(JSONArray array,int i){
//        JSONObject course = new JSONObject();
//        String name = array.get(i).toString().substring(array.get(i).toString().indexOf("\"") + 1);
//        //String credit = array.get(i).toString().substring((array.get(i).toString().length() - 2), (array.get(1).toString().length()) - 1);
//        name = name.substring(0, name.indexOf("\""));
//        return name;
//    }
//    public String getCredit(JSONArray array,int i){
//        JSONObject course = new JSONObject();
//        //String name = array.get(i).toString().substring(array.get(i).toString().indexOf("\"") + 1);
//        String credit = array.get(i).toString().substring((array.get(i).toString().length() - 2), (array.get(1).toString().length()) - 1);
//        //name = name.substring(0, name.indexOf("\""));
//       // course.put(name, credit);
//        return credit;
//    }
//
//    public JSONArray getCredit() {
//        int size = jdbcTemplate.queryForObject("select count(*)\n" +
//                "from programs a\n" +
//                "join program_courses b on a.ProgramId = b.ProgramId\n" +
//                "join courses c on b.CourseId = c.CourseId\n" +
//                "join semester_type d on c.SemesterTypeId = d.SemesterTypeId\n" +
//                "join precourses p on c.Name=p.Name", Integer.class);
//        JSONArray creditList = new JSONArray();
//        List<Map<String, Object>> credits = jdbcTemplate.queryForList("select c.Credits\n" +
//                "from programs a\n" +
//                "join program_courses b on a.ProgramId = b.ProgramId\n" +
//                "join courses c on b.CourseId = c.CourseId\n" +
//                "join semester_type d on c.SemesterTypeId = d.SemesterTypeId\n" +
//                "join precourses p on c.Name=p.Name");
//        List<Map<String, Object>> names = jdbcTemplate.queryForList("select c.Name\n" +
//                "from programs a\n" +
//                "join program_courses b on a.ProgramId = b.ProgramId\n" +
//                "join courses c on b.CourseId = c.CourseId\n" +
//                "join semester_type d on c.SemesterTypeId = d.SemesterTypeId\n" +
//                "join precourses p on c.Name=p.Name");
//
//        //assign name(key) and credit(value)
//        for (int i = 0; i < size; i++) {
//            JSONObject course = new JSONObject();
//            String name = String.valueOf(names.get(i).values()).substring(1, (String.valueOf(names.get(i).values()).length()) - 1);
//            int credit = Integer.parseInt(String.valueOf(credits.get(i)).substring(9, 10));
//            //System.out.println("Name:"+name+", Credit: "+credit);
//            course.put(name, credit);
//            creditList.add(course);
//        }
//        return creditList;
//
//    }
//}
//
////    @RequestMapping(value = "/{courseName}",method = RequestMethod.GET)
////    public int say(@PathVariable String courseName){
////        int n = 8;
////        int size = jdbcTemplate.queryForObject("select count(*) from gis.orderbysetnumber", Integer.class);
////        ;
////        Model model = new Model(n + "-csc courses problem");
////        IntVar[] cscList = new IntVar[size];
////        //assign the numbers to courses
////        for (int i = 0; i < size; i++) {
////            String name = jdbcTemplate.queryForObject("select Name from gis.orderbysetnumber where CourseId="+(i+1), String.class);
////            cscList[i] = model.intVar(name, 1, n, false);
////
////        }
////
////        int index=0;
////        for (int i = 0; i<size; i++) {
////            if(cscList[i].getName().toUpperCase().equals(courseName.toUpperCase())){
////                index=i;
////                break;
////            }
////        }
////        return index;
////    }
////}
