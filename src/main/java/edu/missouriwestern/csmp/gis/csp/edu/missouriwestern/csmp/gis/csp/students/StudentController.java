package edu.missouriwestern.csmp.gis.csp.edu.missouriwestern.csmp.gis.csp.students;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;
    //map to all student
    @RequestMapping("/students")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }
    //POST request student id for specific student
    @RequestMapping(value = "/students/{id}", method = RequestMethod.POST)
    public JSONArray getStudentByID(@PathVariable String id) {
        return studentService.getStudent(id);
    }

}
