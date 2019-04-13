package edu.missouriwestern.csmp.gis.csp.courses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/course")
public class CourseController {
    @GetMapping
    public String check() {
        return "it works on course";
    }

    @Autowired
    CourseRepository courseRepository;

    @GetMapping(path = "/size")
    public int getSize() {
        return courseRepository.size();
    }

    @GetMapping(path = "/{id}")
    public Course findcourse(@PathVariable int id) {
        return courseRepository.findcourse(id);
    }

    @GetMapping(path = "/all")
    public List<Course> getAllCourses() {
        return courseRepository.getAllCourses();
    }
}
