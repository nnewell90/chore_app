package edu.missouriwestern.csmp.gis.csp.edu.missouriwestern.csmp.gis.csp.students;

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

    //POST without using POST
//    @RequestMapping("/students/{id}")
//    public Student getStudent(@PathVariable String id){
//        return studentService.getStudent(id);
//    }
    //POST request student id for specific student
    @RequestMapping(value = "/students/{id}", method = RequestMethod.POST)
    public Student getStudentByID(@PathVariable String id) {
        return studentService.getStudent(id);
    }

    //PUT(update) student information
    @RequestMapping(value = "students/{id}", method = RequestMethod.PUT)
    public void udateStudent(@RequestBody Student student, @PathVariable String id) {
        studentService.updateStudent(id, student);
    }

    //DELETE a student by specific ID
    @RequestMapping(value = "/students/{id}", method = RequestMethod.DELETE)
    public void deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
    }

}
